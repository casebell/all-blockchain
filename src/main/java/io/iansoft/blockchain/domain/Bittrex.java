package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name="bittrex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bittrex implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @NotNull
    private String symbol;
    @Column(precision=15, scale=8)
    private BigDecimal last;
    @Column(precision=15, scale=8)
    private BigDecimal bid;
    @Column(precision=15, scale=8)
    private BigDecimal ask;
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public Bittrex setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Bittrex setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public BigDecimal getLast() {
        return last;
    }

    public Bittrex setLast(BigDecimal last) {
        this.last = last;
        return this;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public Bittrex setBid(BigDecimal bid) {
        this.bid = bid;
        return this;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public Bittrex setAsk(BigDecimal ask) {
        this.ask = ask;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Bittrex setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }
}

/*
"MarketName":"BITCNY-BTC",
"High":26999.00000000,
"Low":22606.00000000,
"Volume":72.75135195,
"Last":25999.59000000,
"BaseVolume":1731262.87402616,
"TimeStamp":"2017-08-12T11:16:40.363",
"Bid":24719.77000003,
"Ask":25999.51000000,
"OpenBuyOrders":200,
"OpenSellOrders":53,
"PrevDay":23795.99999997,
"Created":"2015-12-11T06:31:40.653"
 */
