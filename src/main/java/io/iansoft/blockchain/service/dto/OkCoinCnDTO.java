package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class OkCoinCnDTO implements Serializable {
    private Long id;
    private String symbol;
    private String buy;
    private String high;
    private String last;
    private String low;
    private String sell;
    private String vol;
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public OkCoinCnDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public OkCoinCnDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getBuy() {
        return buy;
    }

    public OkCoinCnDTO setBuy(String buy) {
        this.buy = buy;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public OkCoinCnDTO setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLast() {
        return last;
    }

    public OkCoinCnDTO setLast(String last) {
        this.last = last;
        return this;
    }

    public String getLow() {
        return low;
    }

    public OkCoinCnDTO setLow(String low) {
        this.low = low;
        return this;
    }

    public String getSell() {
        return sell;
    }

    public OkCoinCnDTO setSell(String sell) {
        this.sell = sell;
        return this;
    }

    public String getVol() {
        return vol;
    }

    public OkCoinCnDTO setVol(String vol) {
        this.vol = vol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public OkCoinCnDTO setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OkCoinCnDTO{");
        sb.append("id=").append(id);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", buy='").append(buy).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", last='").append(last).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", sell='").append(sell).append('\'');
        sb.append(", vol='").append(vol).append('\'');
        sb.append(", createdat=").append(createdat);
        sb.append('}');
        return sb.toString();
    }
}
