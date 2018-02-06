package io.iansoft.blockchain.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class UpbitDTO implements Serializable {
    String code;
    String candleDateTime;
    String candleDateTimeKst;
    long openingPrice;
    long highPrice;
    long lowPrice;
    long tradePrice;
    long candleAccTradeVolume;
    long candleAccTradePrice;
    long  timestamp;
    long unit;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCandleDateTime() {
        return candleDateTime;
    }

    public void setCandleDateTime(String candleDateTime) {
        this.candleDateTime = candleDateTime;
    }

    public String getCandleDateTimeKst() {
        return candleDateTimeKst;
    }

    public void setCandleDateTimeKst(String candleDateTimeKst) {
        this.candleDateTimeKst = candleDateTimeKst;
    }

    public long getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    }

    public long getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(long highPrice) {
        this.highPrice = highPrice;
    }

    public long getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(long lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(long tradePrice) {
        this.tradePrice = tradePrice;
    }

    public long getCandleAccTradeVolume() {
        return candleAccTradeVolume;
    }

    public void setCandleAccTradeVolume(long candleAccTradeVolume) {
        this.candleAccTradeVolume = candleAccTradeVolume;
    }

    public long getCandleAccTradePrice() {
        return candleAccTradePrice;
    }

    public void setCandleAccTradePrice(long candleAccTradePrice) {
        this.candleAccTradePrice = candleAccTradePrice;
    }

    public long getTimestamp() {
        return timestamp;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpbitDTO upbitDTO = (UpbitDTO) o;
        return openingPrice == upbitDTO.openingPrice &&
            highPrice == upbitDTO.highPrice &&
            lowPrice == upbitDTO.lowPrice &&
            tradePrice == upbitDTO.tradePrice &&
            candleAccTradeVolume == upbitDTO.candleAccTradeVolume &&
            candleAccTradePrice == upbitDTO.candleAccTradePrice &&
            timestamp == upbitDTO.timestamp &&
            unit == upbitDTO.unit &&
            Objects.equals(code, upbitDTO.code) &&
            Objects.equals(candleDateTime, upbitDTO.candleDateTime) &&
            Objects.equals(candleDateTimeKst, upbitDTO.candleDateTimeKst);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, candleDateTime, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, candleAccTradeVolume, candleAccTradePrice, timestamp, unit);
    }

    @Override
    public String toString() {
        return "UpbitDTO{" +
            "code='" + code + '\'' +
            ", candleDateTime='" + candleDateTime + '\'' +
            ", candleDateTimeKst='" + candleDateTimeKst + '\'' +
            ", openingPrice=" + openingPrice +
            ", highPrice=" + highPrice +
            ", lowPrice=" + lowPrice +
            ", tradePrice=" + tradePrice +
            ", candleAccTradeVolume=" + candleAccTradeVolume +
            ", candleAccTradePrice=" + candleAccTradePrice +
            ", timestamp=" + timestamp +
            ", unit=" + unit +
            '}';
    }
}
