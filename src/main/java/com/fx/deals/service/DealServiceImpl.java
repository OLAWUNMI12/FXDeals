package com.fx.deals.service;

import com.fx.deals.dto.DealDTO;
import com.fx.deals.exception.ValidationException;
import com.fx.deals.mapper.DealsMapper;
import com.fx.deals.model.FXDeal;
import com.fx.deals.repository.DealRepository;
import com.fx.deals.util.ValidationResult;
import com.fx.deals.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class DealServiceImpl  implements  DealService{
    Logger log = LoggerFactory.getLogger(DealServiceImpl.class);
    private final DealRepository dealRepository;
    private final DealsMapper dealsMapper;
    private final ValidationUtil validationUtil;

    public DealServiceImpl(DealRepository dealRepository, DealsMapper dealsMapper, ValidationUtil validationUtil) {
        this.dealRepository = dealRepository;
        this.dealsMapper = dealsMapper;
        this.validationUtil = validationUtil;
    }

    public DealDTO createDeal(DealDTO dealDTO){
        ValidationResult validationResult = validationUtil.validateFXDeal(dealDTO, null);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        FXDeal newDeal = null;
        dealDTO.setUniqueId(generateUniqueId());
        newDeal = dealsMapper.DtoToDeal(dealDTO);
        dealRepository.save(newDeal);
        log.info("Successfully saved deal " + dealDTO);
        return  dealDTO;
    }


    public boolean createDeals(List<DealDTO> dealDTOs){
        log.info("Initiating batch save for fx deals.");
        Set<DealDTO> dealDTOSet = new HashSet<>();
        List<String> errors = new ArrayList<>();
        for(DealDTO dto : dealDTOs){
            if(dealDTOSet.contains(dto)){
                errors.add("Duplicate Fx deal found " + dto.toString());
            }else {
                dealDTOSet.add(dto);
            }
        }
        if(!CollectionUtils.isEmpty(errors)){
            throw new ValidationException(errors);
        }
        int count = 1;
        for(DealDTO dto : dealDTOs){
            ValidationResult validationResult = validationUtil.validateFXDeal(dto, count);
            if(!validationResult.isValid()){
                errors.addAll(validationResult.getErrors());
            }
            count++;
        }

        if(!CollectionUtils.isEmpty(errors)){
            throw new ValidationException(errors);
        }
        return  true;
    }

    public String generateUniqueId(){
        boolean generated = false;
        String id = String.valueOf(UUID.randomUUID());
        while(!generated){
            if(findFXDealByUniqueId(id) == null){
                generated = true;
            }else {
                id = String.valueOf(UUID.randomUUID());
            }
        }
        return id;
    }


    public FXDeal findFXDealByUniqueId(String uniqueId){
        if(StringUtils.isNotEmpty(uniqueId)){
            return dealRepository.findFxDealByUniqueId(uniqueId);
        }
        return null;
    }

    @Override
    public List<DealDTO> fetchAlldeals() {
        log.info("Fetching all Fx deals");
        List<DealDTO> dtos = new ArrayList<>();
        List<FXDeal> deals = dealRepository.findAll();
        for(FXDeal deal : deals){
            dtos.add(dealsMapper.dealToDTO(deal));
        }
        return dtos;
    }
}
