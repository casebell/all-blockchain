package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="korbit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Korbit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    private String symbol;
    private String timestamp;
    private String last;
    private String bid;
    private String ask;
    private String low;
    private String high;
    private String volume;
    private String change;
    private String changePercent;
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public Korbit setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Korbit setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Korbit setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getLast() {
        return last;
    }

    public Korbit setLast(String last) {
        this.last = last;
        return this;
    }

    public String getBid() {
        return bid;
    }

    public Korbit setBid(String bid) {
        this.bid = bid;
        return this;
    }

    public String getAsk() {
        return ask;
    }

    public Korbit setAsk(String ask) {
        this.ask = ask;
        return this;
    }

    public String getLow() {
        return low;
    }

    public Korbit setLow(String low) {
        this.low = low;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public Korbit setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public Korbit setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getChange() {
        return change;
    }

    public Korbit setChange(String change) {
        this.change = change;
        return this;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public Korbit setChangePercent(String changePercent) {
        this.changePercent = changePercent;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Korbit setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }
}
