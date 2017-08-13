package io.iansoft.blockchain.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="bithumb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bithumb implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @NotNull
    private String symbol;
    private String opening_price;
    @NotNull
    private String closing_price;
    private String min_price;
    private String max_price;
    private String average_price;
    private String units_traded;
    private String volume_1day;
    private String volume_7day;
    private String buy_price;
    private String sell_price;
    private String date;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public Bithumb setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Bithumb setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getOpening_price() {
        return opening_price;
    }

    public Bithumb setOpening_price(String opening_price) {
        this.opening_price = opening_price;
        return this;
    }

    public String getClosing_price() {
        return closing_price;
    }

    public Bithumb setClosing_price(String closing_price) {
        this.closing_price = closing_price;
        return this;
    }

    public String getMin_price() {
        return min_price;
    }

    public Bithumb setMin_price(String min_price) {
        this.min_price = min_price;
        return this;
    }

    public String getMax_price() {
        return max_price;
    }

    public Bithumb setMax_price(String max_price) {
        this.max_price = max_price;
        return this;
    }

    public String getAverage_price() {
        return average_price;
    }

    public Bithumb setAverage_price(String average_price) {
        this.average_price = average_price;
        return this;
    }

    public String getUnits_traded() {
        return units_traded;
    }

    public Bithumb setUnits_traded(String units_traded) {
        this.units_traded = units_traded;
        return this;
    }

    public String getVolume_1day() {
        return volume_1day;
    }

    public Bithumb setVolume_1day(String volume_1day) {
        this.volume_1day = volume_1day;
        return this;
    }

    public String getVolume_7day() {
        return volume_7day;
    }

    public Bithumb setVolume_7day(String volume_7day) {
        this.volume_7day = volume_7day;
        return this;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public Bithumb setBuy_price(String buy_price) {
        this.buy_price = buy_price;
        return this;
    }

    public String getSell_price() {
        return sell_price;
    }

    public Bithumb setSell_price(String sell_price) {
        this.sell_price = sell_price;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Bithumb setDate(String date) {
        this.date = date;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Bithumb setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }
}
