package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * Created by SERVANT Adrien
 * Date 04/21/21
 * Time 15:00
 *
 * Comment : Html's problems "can't resolve [...]" worked fine, its known issues since 2015 with Intellij
 */


@Controller
@EnableWebSecurity
public class BidListController {
    private final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    private BidListRepository bidListRepository;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("Getting BidList");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bid.setCreationDate((new Timestamp(System.currentTimeMillis())));
            bidListRepository.save(bid);
            model.addAttribute("bidList", bidListRepository.findAll());
            logger.info("New BidList saved");
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("BidList updated");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("BidList deleted");
        return "redirect:/bidList/list";
    }
}
