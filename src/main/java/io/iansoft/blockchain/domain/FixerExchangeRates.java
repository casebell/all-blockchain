

package io.iansoft.blockchain.domain;
import io.iansoft.blockchain.domain.enumeration.CurrencyType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ecb_exchange_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ecb_exchange_rate")
public class FixerExchangeRates extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String base;

    private BigDecimal USD;
    private BigDecimal JPY;
    private BigDecimal BGN;
    private BigDecimal CZK;
    private BigDecimal DKK;
    private BigDecimal GBP;
    private BigDecimal HUF;
    private BigDecimal PLN;
    private BigDecimal RON;
    private BigDecimal SEK;
    private BigDecimal CHF;
    private BigDecimal NOK;
    private BigDecimal HRK;
    private BigDecimal RUB;
    private BigDecimal TRY;
    private BigDecimal AUD;
    private BigDecimal BRL;
    private BigDecimal CAD;
    private BigDecimal CNY;
    private BigDecimal HKD;
    private BigDecimal IDR;
    private BigDecimal ILS;
    private BigDecimal INR;
    private BigDecimal KRW;
    private BigDecimal MXN;
    private BigDecimal MYR;
    private BigDecimal NZD;
    private BigDecimal PHP;
    private BigDecimal SGD;
    private BigDecimal THB;
    private BigDecimal EUR;

    public BigDecimal getEUR() {
        return EUR;
    }

    public void setEUR(BigDecimal EUR) {
        this.EUR = EUR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public BigDecimal getUSD() {
        return USD;
    }

    public void setUSD(BigDecimal USD) {
        this.USD = USD;
    }

    public BigDecimal getJPY() {
        return JPY;
    }

    public void setJPY(BigDecimal JPY) {
        this.JPY = JPY;
    }

    public BigDecimal getBGN() {
        return BGN;
    }

    public void setBGN(BigDecimal BGN) {
        this.BGN = BGN;
    }

    public BigDecimal getCZK() {
        return CZK;
    }

    public void setCZK(BigDecimal CZK) {
        this.CZK = CZK;
    }

    public BigDecimal getDKK() {
        return DKK;
    }

    public void setDKK(BigDecimal DKK) {
        this.DKK = DKK;
    }

    public BigDecimal getGBP() {
        return GBP;
    }

    public void setGBP(BigDecimal GBP) {
        this.GBP = GBP;
    }

    public BigDecimal getHUF() {
        return HUF;
    }

    public void setHUF(BigDecimal HUF) {
        this.HUF = HUF;
    }

    public BigDecimal getPLN() {
        return PLN;
    }

    public void setPLN(BigDecimal PLN) {
        this.PLN = PLN;
    }

    public BigDecimal getRON() {
        return RON;
    }

    public void setRON(BigDecimal RON) {
        this.RON = RON;
    }

    public BigDecimal getSEK() {
        return SEK;
    }

    public void setSEK(BigDecimal SEK) {
        this.SEK = SEK;
    }

    public BigDecimal getCHF() {
        return CHF;
    }

    public void setCHF(BigDecimal CHF) {
        this.CHF = CHF;
    }

    public BigDecimal getNOK() {
        return NOK;
    }

    public void setNOK(BigDecimal NOK) {
        this.NOK = NOK;
    }

    public BigDecimal getHRK() {
        return HRK;
    }

    public void setHRK(BigDecimal HRK) {
        this.HRK = HRK;
    }

    public BigDecimal getRUB() {
        return RUB;
    }

    public void setRUB(BigDecimal RUB) {
        this.RUB = RUB;
    }

    public BigDecimal getTRY() {
        return TRY;
    }

    public void setTRY(BigDecimal TRY) {
        this.TRY = TRY;
    }

    public BigDecimal getAUD() {
        return AUD;
    }

    public void setAUD(BigDecimal AUD) {
        this.AUD = AUD;
    }

    public BigDecimal getBRL() {
        return BRL;
    }

    public void setBRL(BigDecimal BRL) {
        this.BRL = BRL;
    }

    public BigDecimal getCAD() {
        return CAD;
    }

    public void setCAD(BigDecimal CAD) {
        this.CAD = CAD;
    }

    public BigDecimal getCNY() {
        return CNY;
    }

    public void setCNY(BigDecimal CNY) {
        this.CNY = CNY;
    }

    public BigDecimal getHKD() {
        return HKD;
    }

    public void setHKD(BigDecimal HKD) {
        this.HKD = HKD;
    }

    public BigDecimal getIDR() {
        return IDR;
    }

    public void setIDR(BigDecimal IDR) {
        this.IDR = IDR;
    }

    public BigDecimal getILS() {
        return ILS;
    }

    public void setILS(BigDecimal ILS) {
        this.ILS = ILS;
    }

    public BigDecimal getINR() {
        return INR;
    }

    public void setINR(BigDecimal INR) {
        this.INR = INR;
    }

    public BigDecimal getKRW() {
        return KRW;
    }

    public void setKRW(BigDecimal KRW) {
        this.KRW = KRW;
    }

    public BigDecimal getMXN() {
        return MXN;
    }

    public void setMXN(BigDecimal MXN) {
        this.MXN = MXN;
    }

    public BigDecimal getMYR() {
        return MYR;
    }

    public void setMYR(BigDecimal MYR) {
        this.MYR = MYR;
    }

    public BigDecimal getNZD() {
        return NZD;
    }

    public void setNZD(BigDecimal NZD) {
        this.NZD = NZD;
    }

    public BigDecimal getPHP() {
        return PHP;
    }

    public void setPHP(BigDecimal PHP) {
        this.PHP = PHP;
    }

    public BigDecimal getSGD() {
        return SGD;
    }

    public void setSGD(BigDecimal SGD) {
        this.SGD = SGD;
    }

    public BigDecimal getTHB() {
        return THB;
    }

    public void setTHB(BigDecimal THB) {
        this.THB = THB;
    }
}
