package io.iansoft.blockchain.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
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

    private String currency;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketDTO marketDTO = (MarketDTO) o;
        if(marketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", url='" + getUrl() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
