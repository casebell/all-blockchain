package io.iansoft.blockchain.service.dto;


import io.iansoft.blockchain.domain.enumeration.CurrencyType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MarketCoin entity.
 */
public class MarketCoinDTO implements Serializable {

    private Long id;

    private CurrencyType currency;

    private Long coinId;

    private Long marketId;

    private String coinName;

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

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketCoinDTO that = (MarketCoinDTO) o;
        return Objects.equals(id, that.id) &&
            currency == that.currency &&
            Objects.equals(coinId, that.coinId) &&
            Objects.equals(marketId, that.marketId) &&
            Objects.equals(coinName, that.coinName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, currency, coinId, marketId, coinName);
    }

    @Override
    public String toString() {
        return "MarketCoinDTO{" +
            "id=" + id +
            ", currency=" + currency +
            ", coinId=" + coinId +
            ", marketId=" + marketId +
            ", coinName='" + coinName + '\'' +
            '}';
    }
}
