package com.fx.deals.controller;

import com.fx.deals.dto.DealDTO;
import com.fx.deals.model.ApiResponse;
import com.fx.deals.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DealController {

    Logger log = LoggerFactory.getLogger(DealController.class);
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("/deals")
    public ResponseEntity<?> createDeal(@Valid @RequestBody DealDTO dealDTO){
        log.info("Processing deal " + dealDTO);
        DealDTO newDeal = dealService.createDeal(dealDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<DealDTO>(HttpStatus.CREATED.value(), "Successful", newDeal));

    }


    @PostMapping("/deals/batch")
    public ResponseEntity<?> createDeal(@Valid @RequestBody List<DealDTO> dealDTOS){
        log.info("Processing deals " + dealDTOS);
        dealService.createDeals(dealDTOS);
       return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<DealDTO>(HttpStatus.CREATED.value(), "Successful", null));

    }

    @GetMapping("/deals")
    public ResponseEntity<?> createDeal(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<List<DealDTO>>(HttpStatus.OK.value(), "Successful", dealService.fetchAlldeals()));

    }
}
