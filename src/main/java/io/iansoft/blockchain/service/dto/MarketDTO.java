package io.iansoft.blockchain.service.dto;


import io.iansoft.blockchain.domain.enumeration.ApiType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Market entity.
 */
public class MarketDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private String country;

    @NotNull
    @Size(max = 255)
    private String url;

    private ApiType apiType;

    private String currency;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public ApiType getApiType() {
        return apiType;
    }

    public void setApiType(ApiType apiType) {
        this.apiType = apiType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketDTO marketDTO = (MarketDTO) o;
        return Objects.equals(id, marketDTO.id) &&
            Objects.equals(name, marketDTO.name) &&
            Objects.equals(country, marketDTO.country) &&
            Objects.equals(url, marketDTO.url) &&
            apiType == marketDTO.apiType &&
            Objects.equals(currency, marketDTO.currency) &&
            Objects.equals(createdBy, marketDTO.createdBy) &&
            Objects.equals(createdDate, marketDTO.createdDate) &&
            Objects.equals(lastModifiedBy, marketDTO.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, marketDTO.lastModifiedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, country, url, apiType, currency, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "MarketDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", url='" + url + '\'' +
            ", apiType=" + apiType +
            ", currency='" + currency + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
