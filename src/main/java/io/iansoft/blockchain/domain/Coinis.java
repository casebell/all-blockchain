package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Coinis.
 */
@Entity
@Table(name = "coinis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coinis")
public class Coinis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "closeprice", nullable = false)
    private String closeprice;

    @Column(name = "highprice")
    private String highprice;

    @Column(name = "itemcode")
    private String itemcode;

    @Column(name = "lowprice")
    private String lowprice;

    @Column(name = "openprice")
    private String openprice;

    @Column(name = "prevcloseprice")
    private String prevcloseprice;

    @Column(name = "tradeamount")
    private String tradeamount;

    @Column(name = "tradedaebi")
    private String tradedaebi;

    @Column(name = "tradedaebirate")
    private String tradedaebirate;

    @Column(name = "tradedate")
    private String tradedate;

    @Column(name = "tradevolumn")
    private String tradevolumn;

    @NotNull
    @Column(name = "symbol", nullable = false)
    private String symbol;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private ZonedDateTime createdat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCloseprice() {
        return closeprice;
    }

    public Coinis closeprice(String closeprice) {
        this.closeprice = closeprice;
        return this;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }

    public String getHighprice() {
        return highprice;
    }

    public Coinis highprice(String highprice) {
        this.highprice = highprice;
        return this;
    }

    public void setHighprice(String highprice) {
        this.highprice = highprice;
    }

    public String getItemcode() {
        return itemcode;
    }

    public Coinis itemcode(String itemcode) {
        this.itemcode = itemcode;
        return this;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getLowprice() {
        return lowprice;
    }

    public Coinis lowprice(String lowprice) {
        this.lowprice = lowprice;
        return this;
    }

    public void setLowprice(String lowprice) {
        this.lowprice = lowprice;
    }

    public String getOpenprice() {
        return openprice;
    }

    public Coinis openprice(String openprice) {
        this.openprice = openprice;
        return this;
    }

    public void setOpenprice(String openprice) {
        this.openprice = openprice;
    }

    public String getPrevcloseprice() {
        return prevcloseprice;
    }

    public Coinis prevcloseprice(String prevcloseprice) {
        this.prevcloseprice = prevcloseprice;
        return this;
    }

    public void setPrevcloseprice(String prevcloseprice) {
        this.prevcloseprice = prevcloseprice;
    }

    public String getTradeamount() {
        return tradeamount;
    }

    public Coinis tradeamount(String tradeamount) {
        this.tradeamount = tradeamount;
        return this;
    }

    public void setTradeamount(String tradeamount) {
        this.tradeamount = tradeamount;
    }

    public String getTradedaebi() {
        return tradedaebi;
    }

    public Coinis tradedaebi(String tradedaebi) {
        this.tradedaebi = tradedaebi;
        return this;
    }

    public void setTradedaebi(String tradedaebi) {
        this.tradedaebi = tradedaebi;
    }

    public String getTradedaebirate() {
        return tradedaebirate;
    }

    public Coinis tradedaebirate(String tradedaebirate) {
        this.tradedaebirate = tradedaebirate;
        return this;
    }

    public void setTradedaebirate(String tradedaebirate) {
        this.tradedaebirate = tradedaebirate;
    }

    public String getTradedate() {
        return tradedate;
    }

    public Coinis tradedate(String tradedate) {
        this.tradedate = tradedate;
        return this;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getTradevolumn() {
        return tradevolumn;
    }

    public Coinis tradevolumn(String tradevolumn) {
        this.tradevolumn = tradevolumn;
        return this;
    }

    public void setTradevolumn(String tradevolumn) {
        this.tradevolumn = tradevolumn;
    }

    public String getSymbol() {
        return symbol;
    }

    public Coinis symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Coinis createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coinis coinis = (Coinis) o;
        if (coinis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coinis{" +
            "id=" + getId() +
            ", closeprice='" + getCloseprice() + "'" +
            ", highprice='" + getHighprice() + "'" +
            ", itemcode='" + getItemcode() + "'" +
            ", lowprice='" + getLowprice() + "'" +
            ", openprice='" + getOpenprice() + "'" +
            ", prevcloseprice='" + getPrevcloseprice() + "'" +
            ", tradeamount='" + getTradeamount() + "'" +
            ", tradedaebi='" + getTradedaebi() + "'" +
            ", tradedaebirate='" + getTradedaebirate() + "'" +
            ", tradedate='" + getTradedate() + "'" +
            ", tradevolumn='" + getTradevolumn() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
