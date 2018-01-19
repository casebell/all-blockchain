package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quote")
public class Quote extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "last_price", precision=20, scale=10, nullable = false)
    private BigDecimal lastPrice;

    @Column(name = "volume", precision=30, scale=15)
    private BigDecimal volume;

    @Column(name = "low_price", precision=20, scale=10)
    private BigDecimal lowPrice;

    @Column(name = "high_price", precision=20, scale=10)
    private BigDecimal highPrice;

    @Column(name = "avg_price", precision=20, scale=10)
    private BigDecimal avgPrice;

    @Column(name = "buy_price", precision=20, scale=10)
    private BigDecimal buyPrice;

    @Column(name = "sell_price", precision=20, scale=10)
    private BigDecimal sellPrice;

    @Column(name = "opening_price", precision=20, scale=10)
    private BigDecimal openingPrice;

    @Column(name = "closing_price", precision=20, scale=10)
    private BigDecimal closingPrice;

    @Column(name = "quote_time")
    private Instant quoteTime;

    @ManyToOne
    private MarketCoin marketCoin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public Quote lastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public Quote volume(BigDecimal volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public Quote lowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
        return this;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public Quote highPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
        return this;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public Quote avgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
        return this;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public Quote buyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public Quote sellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public Quote openingPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
        return this;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public Quote closingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
        return this;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Instant getQuoteTime() {
        return quoteTime;
    }

    public Quote quoteTime(Instant quoteTime) {
        this.quoteTime = quoteTime;
        return this;
    }

    public void setQuoteTime(Instant quoteTime) {
        this.quoteTime = quoteTime;
    }

    public MarketCoin getMarketCoin() {
        return marketCoin;
    }

    public Quote marketCoin(MarketCoin marketCoin) {
        this.marketCoin = marketCoin;
        return this;
    }

    public void setMarketCoin(MarketCoin marketCoin) {
        this.marketCoin = marketCoin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        if (quote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", lastPrice=" + getLastPrice() +
            ", volume=" + getVolume() +
            ", lowPrice=" + getLowPrice() +
            ", highPrice=" + getHighPrice() +
            ", avgPrice=" + getAvgPrice() +
            ", buyPrice=" + getBuyPrice() +
            ", sellPrice=" + getSellPrice() +
            ", openingPrice=" + getOpeningPrice() +
            ", closingPrice=" + getClosingPrice() +
            ", quoteTime='" + getQuoteTime() + "'" +
            "}";
    }
}
