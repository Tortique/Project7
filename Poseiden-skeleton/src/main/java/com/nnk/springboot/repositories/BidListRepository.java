package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPARepository for BidList
 */

public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
