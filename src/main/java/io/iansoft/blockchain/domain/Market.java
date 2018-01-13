package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Market.
 */
@Entity
@Table(name = "market")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "market")
public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "country")
    private String country;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @Column(name = "currency")
    private String currency;

    @OneToMany(mappedBy = "market")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coin> coins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Market name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Market country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public Market url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrency() {
        return currency;
    }

    public Market currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<Coin> getCoins() {
        return coins;
    }

    public Market coins(Set<Coin> coins) {
        this.coins = coins;
        return this;
    }

    public Market addCoin(Coin coin) {
        this.coins.add(coin);
        coin.setMarket(this);
        return this;
    }

    public Market removeCoin(Coin coin) {
        this.coins.remove(coin);
        coin.setMarket(null);
        return this;
    }

    public void setCoins(Set<Coin> coins) {
        this.coins = coins;
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
        Market market = (Market) o;
        if (market.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), market.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Market{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", url='" + getUrl() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
