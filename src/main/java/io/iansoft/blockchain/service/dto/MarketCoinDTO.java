package io.iansoft.blockchain.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MarketCoin entity.
 */
public class MarketCoinDTO implements Serializable {

    private Long id;

    private Long coinId;

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

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketCoinDTO that = (MarketCoinDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(coinId, that.coinId) &&
            Objects.equals(marketId, that.marketId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, coinId, marketId);
    }

    @Override
    public String toString() {
        return "MarketCoinDTO{" +
            "id=" + id +
            ", coinId=" + coinId +
            ", marketId=" + marketId +
            '}';
    }
}
