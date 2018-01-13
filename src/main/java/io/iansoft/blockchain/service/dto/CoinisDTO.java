package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Coinis entity.
 */
public class CoinisDTO implements Serializable {

    private Long id;

    @NotNull
    private String closeprice;

    private String highprice;

    private String itemcode;

    private String lowprice;

    private String openprice;

    private String prevcloseprice;

    private String tradeamount;

    private String tradedaebi;

    private String tradedaebirate;

    private String tradedate;

    private String tradevolumn;

    @NotNull
    private String symbol;

    @NotNull
    private ZonedDateTime createdat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }

    public String getHighprice() {
        return highprice;
    }

    public void setHighprice(String highprice) {
        this.highprice = highprice;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getLowprice() {
        return lowprice;
    }

    public void setLowprice(String lowprice) {
        this.lowprice = lowprice;
    }

    public String getOpenprice() {
        return openprice;
    }

    public void setOpenprice(String openprice) {
        this.openprice = openprice;
    }

    public String getPrevcloseprice() {
        return prevcloseprice;
    }

    public void setPrevcloseprice(String prevcloseprice) {
        this.prevcloseprice = prevcloseprice;
    }

    public String getTradeamount() {
        return tradeamount;
    }

    public void setTradeamount(String tradeamount) {
        this.tradeamount = tradeamount;
    }

    public String getTradedaebi() {
        return tradedaebi;
    }

    public void setTradedaebi(String tradedaebi) {
        this.tradedaebi = tradedaebi;
    }

    public String getTradedaebirate() {
        return tradedaebirate;
    }

    public void setTradedaebirate(String tradedaebirate) {
        this.tradedaebirate = tradedaebirate;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getTradevolumn() {
        return tradevolumn;
    }

    public void setTradevolumn(String tradevolumn) {
        this.tradevolumn = tradevolumn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

        CoinisDTO coinisDTO = (CoinisDTO) o;
        if(coinisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinisDTO{" +
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
