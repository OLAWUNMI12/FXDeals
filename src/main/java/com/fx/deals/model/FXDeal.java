package com.fx.deals.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "tbl_FXDeals")
public class FXDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uniqueId", nullable = false, unique = true)
    private String uniqueId;

    @Column(name = "fromCurrency", nullable = false)
    private Currency fromCurrency;

    @Column(name = "toCurrency", nullable = false)
    private Currency toCurrency;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "amount")
    private BigDecimal amount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
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
        return "FXDeal{" +
                "id=" + id +
                ", uniqueId='" + uniqueId + '\'' +
                ", fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
