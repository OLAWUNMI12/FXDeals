package com.fx.deals.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class DealDTO {

    private String uniqueId;

    private String fromCurrency;

    private String toCurrency;

    private LocalDateTime date;

    private BigDecimal amount;


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        String sb = "{" + "fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
        return sb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealDTO dto = (DealDTO) o;
        return Objects.equals(fromCurrency, dto.fromCurrency) && Objects.equals(toCurrency, dto.toCurrency) && Objects.equals(date, dto.date) && Objects.equals(amount, dto.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency, date, amount);
    }
}
