package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class KorbitDTO implements Serializable {

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

    public KorbitDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public KorbitDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public KorbitDTO setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public KorbitDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getLast() {
        return last;
    }

    public KorbitDTO setLast(String last) {
        this.last = last;
        return this;
    }

    public String getBid() {
        return bid;
    }

    public KorbitDTO setBid(String bid) {
        this.bid = bid;
        return this;
    }

    public String getAsk() {
        return ask;
    }

    public KorbitDTO setAsk(String ask) {
        this.ask = ask;
        return this;
    }

    public String getLow() {
        return low;
    }

    public KorbitDTO setLow(String low) {
        this.low = low;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public KorbitDTO setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public KorbitDTO setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getChange() {
        return change;
    }

    public KorbitDTO setChange(String change) {
        this.change = change;
        return this;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public KorbitDTO setChangePercent(String changePercent) {
        this.changePercent = changePercent;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KorbitDTO{");
        sb.append("id=").append(id);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", last='").append(last).append('\'');
        sb.append(", bid='").append(bid).append('\'');
        sb.append(", ask='").append(ask).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", volume='").append(volume).append('\'');
        sb.append(", change='").append(change).append('\'');
        sb.append(", changePercent='").append(changePercent).append('\'');
        sb.append(", createdat=").append(createdat);
        sb.append('}');
        return sb.toString();
    }
}
