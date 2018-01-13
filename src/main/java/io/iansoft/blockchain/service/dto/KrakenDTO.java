package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Kraken entity.
 */
public class KrakenDTO implements Serializable {

    private Long id;

    @NotNull
    private String last;

    @NotNull
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KrakenDTO krakenDTO = (KrakenDTO) o;
        if(krakenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), krakenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KrakenDTO{" +
            "id=" + getId() +
            ", last='" + getLast() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
