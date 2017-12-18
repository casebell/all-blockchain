package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Market Coin.
 */
@Entity
@Table(name = "market_coin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketcoin")
public class MarketCoin extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @ManyToOne
    private Market market;

    @ManyToOne
    private Coin coin;

    @OneToMany(mappedBy = "marketCoin")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ticker> tickers = new HashSet<>();


    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Ticker> getTickers() {
        return tickers;
    }

    public void setTickers(Set<Ticker> tickers) {
        this.tickers = tickers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketCoin that = (MarketCoin) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(market, that.market) &&
            Objects.equals(coin, that.coin) &&
            Objects.equals(tickers, that.tickers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, market, coin, tickers);
    }

    @Override
    public String toString() {
        return "MarketCoin{" +
            "id=" + id +
            ", market=" + market +
            ", coin=" + coin +
            ", tickers=" + tickers +
            '}';
    }
}
