package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class BittrexDTO implements Serializable {
    private Long id;
    private String symbol;
    private BigDecimal last;
    private BigDecimal bid;
    private BigDecimal ask;
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public BittrexDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public BittrexDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BittrexDTO setLast(BigDecimal last) {
        this.last = last;
        return this;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BittrexDTO setBid(BigDecimal bid) {
        this.bid = bid;
        return this;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BittrexDTO setAsk(BigDecimal ask) {
        this.ask = ask;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public BittrexDTO setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }
}
