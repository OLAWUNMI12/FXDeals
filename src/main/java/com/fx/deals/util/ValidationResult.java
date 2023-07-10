package com.fx.deals.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private List<String> errors;

    public ValidationResult() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error){
        errors.add(error);
    }

    public boolean isValid(){
        return CollectionUtils.isEmpty(errors);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
