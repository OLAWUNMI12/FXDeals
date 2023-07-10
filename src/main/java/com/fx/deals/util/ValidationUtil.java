package com.fx.deals.util;

import com.fx.deals.dto.DealDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Objects;


@Component
public class ValidationUtil {

    Logger log = LoggerFactory.getLogger(ValidationUtil.class);

    public ValidationResult validateFXDeal(DealDTO dealDTO, Integer count){
        ValidationResult validationResult = new ValidationResult();
        log.info("Validating deal " + dealDTO.toString());

        if(Objects.isNull(dealDTO)){
            validationResult.addError("FX deal cannot be empty" + (count != null ? " for deal #" +  count : ""));
        }else {

            if (StringUtils.isEmpty(dealDTO.getFromCurrency())){
                validationResult.addError("from currency can not be empty" + (count != null ? " for deal #" +  count : ""));
            }else {
                try{
                    Currency.getInstance(dealDTO.getFromCurrency());
                }catch (Exception e){
                    validationResult.addError("Invalid from currency" + (count != null ? " for deal #" +  count : ""));
                }
            }

            if(StringUtils.isEmpty(dealDTO.getToCurrency())){
                validationResult.addError("To currency can not be empty" + (count != null ? " for deal #" +  count : ""));
            }else {
                try{
                    Currency.getInstance(dealDTO.getToCurrency());
                }catch (Exception e){
                    validationResult.addError("invalid to currency" + (count != null ? " for deal #" +  count : ""));
                }
            }

            if(validationResult.isValid() && StringUtils.equals(dealDTO.getFromCurrency(), dealDTO.getToCurrency())){
                validationResult.addError("From currency and to currency cannot be equal" + (count != null ? " for deal #" +  count : ""));
            }

            if(dealDTO.getDate() == null){
                validationResult.addError("Deal date can not be empty" + (count != null ? " for deal #" +  count : ""));
            }

            if(dealDTO.getAmount() == null){
                validationResult.addError("Amount cannot be empty" + (count != null ? " for deal #" +  count : ""));
            }
        }
        log.info("Validated deal " + dealDTO.toString());
        return  validationResult;

    }
}
