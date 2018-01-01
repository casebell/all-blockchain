package io.iansoft.blockchain.service.dto;


import io.iansoft.blockchain.domain.enumeration.ApiType;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Ticker entity.
 */
public class MyTickerDTO implements Serializable {

    private Long id;

    private ApiType apiType;

    private String marketName;

    private String coinName;

    private long marketCoinId;

    private int sequence;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApiType getApiType() {
        return apiType;
    }

    public void setApiType(ApiType apiType) {
        this.apiType = apiType;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getMarketCoinId() {
        return marketCoinId;
    }

    public void setMarketCoinId(long marketCoinId) {
        this.marketCoinId = marketCoinId;
    }

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
        MyTickerDTO that = (MyTickerDTO) o;
        return marketCoinId == that.marketCoinId &&
            sequence == that.sequence &&
            Objects.equals(id, that.id) &&
            apiType == that.apiType &&
            Objects.equals(marketName, that.marketName) &&
            Objects.equals(coinName, that.coinName) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, apiType, marketName, coinName, marketCoinId, sequence, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "MyTickerDTO{" +
            "id=" + id +
            ", apiType=" + apiType +
            ", marketName='" + marketName + '\'' +
            ", coinName='" + coinName + '\'' +
            ", marketCoinId=" + marketCoinId +
            ", sequence=" + sequence +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
