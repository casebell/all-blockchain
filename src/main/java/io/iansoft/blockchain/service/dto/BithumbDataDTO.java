package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class BithumbDataDTO implements Serializable {

    private Long id;
    private String symbol;
    private String opening_price;
    private String closing_price;
    private String min_price;
    private String max_price;
    private String average_price;
    private String units_traded;
    private String volume_1day;
    private String volume_7day;
    private String buy_price;
    private String sell_price;
    private ZonedDateTime createdat;


    public Long getId() {
        return id;
    }

    public BithumbDataDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public BithumbDataDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public BithumbDataDTO setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public String getOpening_price() {
        return opening_price;
    }

    public BithumbDataDTO setOpening_price(String opening_price) {
        this.opening_price = opening_price;
        return this;
    }

    public String getClosing_price() {
        return closing_price;
    }

    public BithumbDataDTO setClosing_price(String closing_price) {
        this.closing_price = closing_price;
        return this;
    }

    public String getMin_price() {
        return min_price;
    }

    public BithumbDataDTO setMin_price(String min_price) {
        this.min_price = min_price;
        return this;
    }

    public String getMax_price() {
        return max_price;
    }

    public BithumbDataDTO setMax_price(String max_price) {
        this.max_price = max_price;
        return this;
    }

    public String getAverage_price() {
        return average_price;
    }

    public BithumbDataDTO setAverage_price(String average_price) {
        this.average_price = average_price;
        return this;
    }

    public String getUnits_traded() {
        return units_traded;
    }

    public BithumbDataDTO setUnits_traded(String units_traded) {
        this.units_traded = units_traded;
        return this;
    }

    public String getVolume_1day() {
        return volume_1day;
    }

    public BithumbDataDTO setVolume_1day(String volume_1day) {
        this.volume_1day = volume_1day;
        return this;
    }

    public String getVolume_7day() {
        return volume_7day;
    }

    public BithumbDataDTO setVolume_7day(String volume_7day) {
        this.volume_7day = volume_7day;
        return this;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public BithumbDataDTO setBuy_price(String buy_price) {
        this.buy_price = buy_price;
        return this;
    }

    public String getSell_price() {
        return sell_price;
    }

    public BithumbDataDTO setSell_price(String sell_price) {
        this.sell_price = sell_price;
        return this;
    }

    @java.lang.Override
    public java.lang.String toString() {
        final java.lang.StringBuilder sb = new java.lang.StringBuilder("BithumbDataDTO{");
        sb.append("id=").append(id);
        sb.append(", symbol=").append(symbol);
        sb.append(", opening_price=").append(opening_price);
        sb.append(", closing_price=").append(closing_price);
        sb.append(", min_price=").append(min_price);
        sb.append(", max_price=").append(max_price);
        sb.append(", average_price=").append(average_price);
        sb.append(", units_traded=").append(units_traded);
        sb.append(", volume_1day=").append(volume_1day);
        sb.append(", volume_7day=").append(volume_7day);
        sb.append(", buy_price=").append(buy_price);
        sb.append(", sell_price=").append(sell_price);
        sb.append(", createdat=").append(createdat);
        sb.append('}');
        return sb.toString();
    }
}
