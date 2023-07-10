package com.fx.deals.repository;

import com.fx.deals.model.FXDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<FXDeal, Long> {
    FXDeal findFxDealByUniqueId(String uniqueId);
}
