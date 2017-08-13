package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class BitflyerDTO implements Serializable {
    private Long id;
    private String symbol;
    private ZonedDateTime createdat;
    private String product_code;
    private String timestamp;
    private Integer tick_id;
    private Integer best_bid;
    private Integer best_ask;
    private Integer best_bid_size;
    private Integer best_ask_size;
    private Integer total_bid_depth;
    private Integer total_ask_depth;
    private Integer ltp;
    private Integer volume;
    private Integer volume_by_product;

    public Long getId() {
        return id;
    }

    public BitflyerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public BitflyerDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public BitflyerDTO setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public String getProduct_code() {
        return product_code;
    }

    public BitflyerDTO setProduct_code(String product_code) {
        this.product_code = product_code;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BitflyerDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getTick_id() {
        return tick_id;
    }

    public BitflyerDTO setTick_id(Integer tick_id) {
        this.tick_id = tick_id;
        return this;
    }

    public Integer getBest_bid() {
        return best_bid;
    }

    public BitflyerDTO setBest_bid(Integer best_bid) {
        this.best_bid = best_bid;
        return this;
    }

    public Integer getBest_ask() {
        return best_ask;
    }

    public BitflyerDTO setBest_ask(Integer best_ask) {
        this.best_ask = best_ask;
        return this;
    }

    public Integer getBest_bid_size() {
        return best_bid_size;
    }

    public BitflyerDTO setBest_bid_size(Integer best_bid_size) {
        this.best_bid_size = best_bid_size;
        return this;
    }

    public Integer getBest_ask_size() {
        return best_ask_size;
    }

    public BitflyerDTO setBest_ask_size(Integer best_ask_size) {
        this.best_ask_size = best_ask_size;
        return this;
    }

    public Integer getTotal_bid_depth() {
        return total_bid_depth;
    }

    public BitflyerDTO setTotal_bid_depth(Integer total_bid_depth) {
        this.total_bid_depth = total_bid_depth;
        return this;
    }

    public Integer getTotal_ask_depth() {
        return total_ask_depth;
    }

    public BitflyerDTO setTotal_ask_depth(Integer total_ask_depth) {
        this.total_ask_depth = total_ask_depth;
        return this;
    }

    public Integer getLtp() {
        return ltp;
    }

    public BitflyerDTO setLtp(Integer ltp) {
        this.ltp = ltp;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public BitflyerDTO setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Integer getVolume_by_product() {
        return volume_by_product;
    }

    public BitflyerDTO setVolume_by_product(Integer volume_by_product) {
        this.volume_by_product = volume_by_product;
        return this;
    }
}
