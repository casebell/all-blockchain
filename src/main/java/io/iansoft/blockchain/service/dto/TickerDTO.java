package io.iansoft.blockchain.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ticker entity.
 */
public class TickerDTO implements Serializable {

    private Long id;

    private Long coinId;

    private Long userId;

    private Long marketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TickerDTO{" +
            "id=" + id +
            ", coinId=" + coinId +
            ", userId=" + userId +
            ", marketId=" + marketId +
            '}';
    }
}
