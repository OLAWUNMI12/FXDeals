package com.fx.deals.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.deals.mapper.DealsMapper;
import com.fx.deals.model.FXDeal;
import com.fx.deals.repository.DealRepository;
import com.fx.deals.service.DealServiceImpl;
import com.fx.deals.util.ValidationUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FxDealControllerTest {

    DealController dealController ;
    @SpyBean
    DealServiceImpl dealService;

    @MockBean
    DealRepository dealRepository;

    @Autowired
    DealsMapper dealsMapper;

    @SpyBean
    ValidationUtil validationUtil;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper  objectMapper;

    FXDeal fxDeal;
    private MockMvc mockMvc;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @BeforeAll
    void init(){
        fxDeal = new FXDeal();
        fxDeal.setFromCurrency(Currency.getInstance("USD"));
        fxDeal.setToCurrency(Currency.getInstance("NGN"));
        fxDeal.setAmount(new BigDecimal(10000));
        fxDeal.setDate(LocalDateTime.parse("2023-07-19 11:30:40", formatter));

        dealService = new DealServiceImpl(dealRepository, dealsMapper, validationUtil);

        dealController = new DealController(dealService);

    }


    @Test
    public void testSuccessfulDealInsert() throws Exception {
        Mockito.when(dealRepository.findFxDealByUniqueId(Mockito.any())).thenReturn(null);
        String body = objectMapper.writeValueAsString(fxDeal);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String result = mockMvc.perform(
                post("/api/v1/deals")
                        .content(body)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String uniqueId = objectMapper.readTree(result).get("data").get("uniqueId").asText();
        assertNotNull(uniqueId);

    }


    @Test
    public void validateInvalidFieldOnCreate() throws Exception {
        fxDeal.setAmount(null);
        Mockito.when(dealRepository.findFxDealByUniqueId(Mockito.any())).thenReturn(null);
        String body = objectMapper.writeValueAsString(fxDeal);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String result = mockMvc.perform(
                        post("/api/v1/deals")
                                .content(body)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
