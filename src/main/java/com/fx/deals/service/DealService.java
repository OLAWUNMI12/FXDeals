package com.fx.deals.service;

import com.fx.deals.dto.DealDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DealService {

    DealDTO createDeal(DealDTO dealDTO);

    boolean createDeals(List<DealDTO> dealDTO);

    List<DealDTO> fetchAlldeals();
}
