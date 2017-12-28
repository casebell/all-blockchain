package io.iansoft.blockchain.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Ticker entity.
 */
public class TickerDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long marketCoinId;

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

    public Long getMarketCoinId() {
        return marketCoinId;
    }

    public void setMarketCoinId(Long marketCoinId) {
        this.marketCoinId = marketCoinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TickerDTO tickerDTO = (TickerDTO) o;
        return Objects.equals(id, tickerDTO.id) &&
            Objects.equals(userId, tickerDTO.userId) &&
            Objects.equals(marketCoinId, tickerDTO.marketCoinId) &&
            Objects.equals(createdBy, tickerDTO.createdBy) &&
            Objects.equals(createdDate, tickerDTO.createdDate) &&
            Objects.equals(lastModifiedBy, tickerDTO.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, tickerDTO.lastModifiedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, marketCoinId, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "TickerDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", marketCoinId=" + marketCoinId +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
