package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Bitfinex.
 */
@Entity
@Table(name = "bitfinex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bitfinex")
public class Bitfinex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "mid")
    private String mid;

    @Column(name = "bid")
    private String bid;

    @Column(name = "ask")
    private String ask;

    @NotNull
    @Column(name = "last_price", nullable = false)
    private String last_price;

    @Column(name = "low")
    private String low;

    @Column(name = "high")
    private String high;

    @Column(name = "volume")
    private String volume;

    @Column(name = "jhi_timestamp")
    private String timestamp;

    @OneToOne(mappedBy = "bitfinex")
    @JsonIgnore
    private Coin coin;

    private String symbol;

    private ZonedDateTime createdat;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public Bitfinex mid(String mid) {
        this.mid = mid;
        return this;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBid() {
        return bid;
    }

    public Bitfinex bid(String bid) {
        this.bid = bid;
        return this;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public Bitfinex ask(String ask) {
        this.ask = ask;
        return this;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getLast_price() {
        return last_price;
    }

    public Bitfinex last_price(String last_price) {
        this.last_price = last_price;
        return this;
    }

    public void setLast_price(String last_price) {
        this.last_price = last_price;
    }

    public String getLow() {
        return low;
    }

    public Bitfinex low(String low) {
        this.low = low;
        return this;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public Bitfinex high(String high) {
        this.high = high;
        return this;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getVolume() {
        return volume;
    }

    public Bitfinex volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public Bitfinex symbol(String symbol){
        this.symbol = symbol;
        return this;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Bitfinex createdat(ZonedDateTime createdat){
        this.createdat = createdat;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Bitfinex timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Coin getCoin() {
        return coin;
    }

    public Bitfinex coin(Coin coin) {
        this.coin = coin;
        return this;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bitfinex bitfinex = (Bitfinex) o;
        if (bitfinex.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bitfinex.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bitfinex{");
        sb.append("id=").append(id);
        sb.append(", mid='").append(mid).append('\'');
        sb.append(", bid='").append(bid).append('\'');
        sb.append(", ask='").append(ask).append('\'');
        sb.append(", last_price='").append(last_price).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", volume='").append(volume).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", coin=").append(coin);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", createdat=").append(createdat);
        sb.append('}');
        return sb.toString();
    }
}
