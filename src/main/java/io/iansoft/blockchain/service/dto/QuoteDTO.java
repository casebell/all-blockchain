package io.iansoft.blockchain.service.dto;


import io.iansoft.blockchain.domain.enumeration.CurrencyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Quote entity.
 */
public class QuoteDTO implements Serializable {

    private Long id;

    private BigDecimal lastPrice;

    private Long marketCoinId;

    private BigDecimal volume;

    private BigDecimal lowPrice;

    private BigDecimal highPrice;

    private BigDecimal avgPrice;

    private BigDecimal buyPrice;

    private BigDecimal sellPrice;

    private BigDecimal openingPrice;

    private BigDecimal closingPrice;

    private Instant quoteTime;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private CurrencyType currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Long getMarketCoinId() {
        return marketCoinId;
    }

    public void setMarketCoinId(Long marketCoinId) {
        this.marketCoinId = marketCoinId;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Instant getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Instant quoteTime) {
        this.quoteTime = quoteTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
        QuoteDTO quoteDTO = (QuoteDTO) o;
        return Objects.equals(id, quoteDTO.id) &&
            Objects.equals(lastPrice, quoteDTO.lastPrice) &&
            Objects.equals(marketCoinId, quoteDTO.marketCoinId) &&
            Objects.equals(volume, quoteDTO.volume) &&
            Objects.equals(lowPrice, quoteDTO.lowPrice) &&
            Objects.equals(highPrice, quoteDTO.highPrice) &&
            Objects.equals(avgPrice, quoteDTO.avgPrice) &&
            Objects.equals(buyPrice, quoteDTO.buyPrice) &&
            Objects.equals(sellPrice, quoteDTO.sellPrice) &&
            Objects.equals(openingPrice, quoteDTO.openingPrice) &&
            Objects.equals(closingPrice, quoteDTO.closingPrice) &&
            Objects.equals(quoteTime, quoteDTO.quoteTime) &&
            Objects.equals(createdBy, quoteDTO.createdBy) &&
            Objects.equals(createdDate, quoteDTO.createdDate) &&
            Objects.equals(lastModifiedBy, quoteDTO.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, quoteDTO.lastModifiedDate) &&
            currency == quoteDTO.currency;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, lastPrice, marketCoinId, volume, lowPrice, highPrice, avgPrice, buyPrice, sellPrice, openingPrice, closingPrice, quoteTime, createdBy, createdDate, lastModifiedBy, lastModifiedDate, currency);
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + id +
            ", lastPrice=" + lastPrice +
            ", marketCoinId=" + marketCoinId +
            ", volume=" + volume +
            ", lowPrice=" + lowPrice +
            ", highPrice=" + highPrice +
            ", avgPrice=" + avgPrice +
            ", buyPrice=" + buyPrice +
            ", sellPrice=" + sellPrice +
            ", openingPrice=" + openingPrice +
            ", closingPrice=" + closingPrice +
            ", quoteTime=" + quoteTime +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", currency=" + currency +
            '}';
    }
}
