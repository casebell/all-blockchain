package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "okcoincn")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OkCoinCn implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    date: server time for returned data
buy: best bid
high: highest price
last: latest price
low: lowest price
sell: best ask
vol: volume (in the last rolling 24 hours)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    private String symbol;
    private String buy;
    private String high;
    private String last;
    private String low;
    private String sell;
    private String vol;
    @Column(name = "createdat")
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public OkCoinCn setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public OkCoinCn setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getBuy() {
        return buy;
    }

    public OkCoinCn setBuy(String buy) {
        this.buy = buy;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public OkCoinCn setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLast() {
        return last;
    }

    public OkCoinCn setLast(String last) {
        this.last = last;
        return this;
    }

    public String getLow() {
        return low;
    }

    public OkCoinCn setLow(String low) {
        this.low = low;
        return this;
    }

    public String getSell() {
        return sell;
    }

    public OkCoinCn setSell(String sell) {
        this.sell = sell;
        return this;
    }

    public String getVol() {
        return vol;
    }

    public OkCoinCn setVol(String vol) {
        this.vol = vol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public OkCoinCn setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }
}
