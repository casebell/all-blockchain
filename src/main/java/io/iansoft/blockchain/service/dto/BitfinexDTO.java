package io.iansoft.blockchain.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Bitfinex entity.
 */
public class BitfinexDTO implements Serializable {

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
        return "BitfinexDTO{" +
            "id=" + getId() +
            ", mid='" + getMid() + "'" +
            ", bid='" + getBid() + "'" +
            ", ask='" + getAsk() + "'" +
            ", last_price='" + getLast_price() + "'" +
            ", low='" + getLow() + "'" +
            ", high='" + getHigh() + "'" +
            ", volume='" + getVolume() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
