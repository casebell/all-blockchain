package io.iansoft.blockchain.service.dto;


import io.iansoft.blockchain.domain.Bitfinex;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Bitfinex entity.
 */
public class BitfinexDTO implements Serializable {

    public BitfinexDTO() {

    }

    public BitfinexDTO(Bitfinex bitfinex){
        this(bitfinex.getId(), bitfinex.getMid(), bitfinex.getBid(),
            bitfinex.getAsk(), bitfinex.getLast_price(),bitfinex.getLow(),
            bitfinex.getHigh(),bitfinex.getVolume(),bitfinex.getTimestamp()
        ,bitfinex.getCreatedat(),bitfinex.getSymbol());
    }
    public BitfinexDTO(Long id, String mid, String bid, String ask, String last_price, String low, String high, String volume, String timestamp, ZonedDateTime createdat, String symbol) {
        this.id = id;
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
        this.last_price = last_price;
        this.low = low;
        this.high = high;
        this.volume = volume;
        this.timestamp = timestamp;
        this.createdat = createdat;
        this.symbol = symbol;
    }

    private Long id;

    private String mid;

    private String bid;

    private String ask;

    @NotNull
    private String last_price;

    private String low;

    private String high;

    private String volume;

    private String timestamp;

    private ZonedDateTime createdat;

    private String symbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getLast_price() {
        return last_price;
    }

    public void setLast_price(String last_price) {
        this.last_price = last_price;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitfinexDTO bitfinexDTO = (BitfinexDTO) o;
        if(bitfinexDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bitfinexDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BitfinexDTO{");
        sb.append("id=").append(id);
        sb.append(", mid='").append(mid).append('\'');
        sb.append(", bid='").append(bid).append('\'');
        sb.append(", ask='").append(ask).append('\'');
        sb.append(", last_price='").append(last_price).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", volume='").append(volume).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", createdat=").append(createdat);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
