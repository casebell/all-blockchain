package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Market.
 */
@Entity
@Table(name = "ticker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ticker")
public class Ticker extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private User user;

    private int sequence;

    @ManyToOne
    private MarketCoin marketCoin;

    public MarketCoin getMarketCoin() {
        return marketCoin;
    }

    public void setMarketCoin(MarketCoin marketCoin) {
        this.marketCoin = marketCoin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

        // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticker ticker = (Ticker) o;
        return sequence == ticker.sequence &&
            Objects.equals(id, ticker.id) &&
            Objects.equals(user, ticker.user) &&
            Objects.equals(marketCoin, ticker.marketCoin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, sequence, marketCoin);
    }

    @Override
    public String toString() {
        return "Ticker{" +
            "id=" + id +
            ", user=" + user +
            ", sequence=" + sequence +
            ", marketCoin=" + marketCoin +
            '}';
    }
}
