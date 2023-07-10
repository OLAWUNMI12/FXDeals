package com.fx.deals.service;

import com.fx.deals.dto.DealDTO;
import com.fx.deals.exception.ValidationException;
import com.fx.deals.mapper.DealsMapper;
import com.fx.deals.model.FXDeal;
import com.fx.deals.repository.DealRepository;
import com.fx.deals.service.DealServiceImpl;
import com.fx.deals.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FxDealServiceTest {

     DealServiceImpl dealService;

    @MockBean
     DealRepository dealRepository;

    @Autowired
     DealsMapper dealsMapper;

    @SpyBean
     ValidationUtil validationUtil;

    FXDeal fxDeal;

    List<FXDeal> deals;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    @BeforeAll
     void init(){
        fxDeal = new FXDeal();
        fxDeal.setFromCurrency(Currency.getInstance("USD"));
        fxDeal.setToCurrency(Currency.getInstance("NGN"));
        fxDeal.setAmount(new BigDecimal(10000));
        fxDeal.setDate(LocalDateTime.parse("2023-07-19 11:30:40", formatter));

        dealService = new DealServiceImpl(dealRepository, dealsMapper, validationUtil);

        deals = new ArrayList<>();

        FXDeal fxDeal1 = new FXDeal();
        fxDeal1.setFromCurrency(Currency.getInstance("USD"));
        fxDeal1.setToCurrency(Currency.getInstance("NGN"));
        fxDeal1.setAmount(new BigDecimal(400000));
        fxDeal1.setDate(LocalDateTime.parse("2023-07-19 11:30:40", formatter));
        deals.add(fxDeal1);

        FXDeal fxDeal2 = new FXDeal();
        fxDeal2.setFromCurrency(Currency.getInstance("ALL"));
        fxDeal2.setToCurrency(Currency.getInstance("BBD"));
        fxDeal2.setAmount(new BigDecimal(100000));
        fxDeal2.setDate(LocalDateTime.parse("2023-07-19 11:30:40", formatter));
        deals.add(fxDeal2);

        FXDeal fxDeal3 = new FXDeal();
        fxDeal3.setFromCurrency(Currency.getInstance("USD"));
        fxDeal3.setToCurrency(Currency.getInstance("NGN"));
        fxDeal3.setAmount(new BigDecimal(400000));
        fxDeal3.setDate(LocalDateTime.parse("2023-07-19 11:30:40", formatter));
        deals.add(fxDeal3);


    }


    @Test
    public void testSuccessfulInsert(){
        Mockito.when(dealRepository.findFxDealByUniqueId(Mockito.any())).thenReturn(null);
        DealDTO dto = dealService.createDeal(dealsMapper.dealToDTO(fxDeal));
        System.out.println(dto);
        assertTrue(StringUtils.isNotEmpty(dto.getUniqueId()));
    }


    @Test
    public void validateUniqueId(){
        String id = String.valueOf(UUID.randomUUID());
        fxDeal.setUniqueId(id);
        Mockito.when(dealRepository.findFxDealByUniqueId(id)).thenReturn(fxDeal);
        String uniqueId = dealService.generateUniqueId();
        assertNotEquals(uniqueId, fxDeal.getUniqueId());

    }


    @Test
    public void validateInvalidFieldOnCreate(){
        fxDeal.setAmount(null);
        Mockito.when(dealRepository.save(Mockito.any())).thenReturn(fxDeal);
        DealDTO dto = dealsMapper.dealToDTO(fxDeal);

        assertThrows(ValidationException.class, () -> {
            dealService.createDeal(dto);
        });
    }


    @Test
    public void validateDuplicateDeals(){
        List<DealDTO>  dealDTOS = new ArrayList<>();
        for(FXDeal fxDeal : deals){
            dealDTOS.add(dealsMapper.dealToDTO(fxDeal));
        }
        try{
            dealService.createDeals(dealDTOS);
        }catch (ValidationException ex){
           List<String> errors =  ex.getErrors();
           for(String error : errors){
               if(error.contains("Duplicate Fx deal found")){
                   assertTrue(error.contains("Duplicate Fx deal found"));
               }
           }
        }
    }
}
