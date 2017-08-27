package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Kraken.
 */
@Entity
@Table(name = "kraken")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "kraken")
public class Kraken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "last", nullable = false)
    private String last;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private ZonedDateTime createdat;

    private String symbol;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLast() {
        return last;
    }

    public Kraken last(String last) {
        this.last = last;
        return this;
    }

    public void setLast(String last) {
        this.last = last;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Kraken symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }


    public Kraken createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kraken kraken = (Kraken) o;
        if (kraken.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kraken.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kraken{" +
            "id=" + getId() +
            ", last='" + getLast() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
