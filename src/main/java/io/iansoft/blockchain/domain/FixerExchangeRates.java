package io.iansoft.blockchain.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;


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

    private double USD;
    private double JPY;
    private double BGN;
    private double CZK;
    private double DKK;
    private double GBP;
    private double HUF;
    private double PLN;
    private double RON;
    private double SEK;
    private double CHF;
    private double NOK;
    private double HRK;
    private double RUB;
    private double TRY;
    private double AUD;
    private double BRL;
    private double CAD;
    private double CNY;
    private double HKD;
    private double IDR;
    private double ILS;
    private double INR;
    private double KRW;
    private double MXN;
    private double MYR;
    private double NZD;
    private double PHP;
    private double SGD;
    private double THB;
    private double EUR;

    public double getEUR() {
        return EUR;
    }

    public void setEUR(double EUR) {
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

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getJPY() {
        return JPY;
    }

    public void setJPY(double JPY) {
        this.JPY = JPY;
    }

    public double getBGN() {
        return BGN;
    }

    public void setBGN(double BGN) {
        this.BGN = BGN;
    }

    public double getCZK() {
        return CZK;
    }

    public void setCZK(double CZK) {
        this.CZK = CZK;
    }

    public double getDKK() {
        return DKK;
    }

    public void setDKK(double DKK) {
        this.DKK = DKK;
    }

    public double getGBP() {
        return GBP;
    }

    public void setGBP(double GBP) {
        this.GBP = GBP;
    }

    public double getHUF() {
        return HUF;
    }

    public void setHUF(double HUF) {
        this.HUF = HUF;
    }

    public double getPLN() {
        return PLN;
    }

    public void setPLN(double PLN) {
        this.PLN = PLN;
    }

    public double getRON() {
        return RON;
    }

    public void setRON(double RON) {
        this.RON = RON;
    }

    public double getSEK() {
        return SEK;
    }

    public void setSEK(double SEK) {
        this.SEK = SEK;
    }

    public double getCHF() {
        return CHF;
    }

    public void setCHF(double CHF) {
        this.CHF = CHF;
    }

    public double getNOK() {
        return NOK;
    }

    public void setNOK(double NOK) {
        this.NOK = NOK;
    }

    public double getHRK() {
        return HRK;
    }

    public void setHRK(double HRK) {
        this.HRK = HRK;
    }

    public double getRUB() {
        return RUB;
    }

    public void setRUB(double RUB) {
        this.RUB = RUB;
    }

    public double getTRY() {
        return TRY;
    }

    public void setTRY(double TRY) {
        this.TRY = TRY;
    }

    public double getAUD() {
        return AUD;
    }

    public void setAUD(double AUD) {
        this.AUD = AUD;
    }

    public double getBRL() {
        return BRL;
    }

    public void setBRL(double BRL) {
        this.BRL = BRL;
    }

    public double getCAD() {
        return CAD;
    }

    public void setCAD(double CAD) {
        this.CAD = CAD;
    }

    public double getCNY() {
        return CNY;
    }

    public void setCNY(double CNY) {
        this.CNY = CNY;
    }

    public double getHKD() {
        return HKD;
    }

    public void setHKD(double HKD) {
        this.HKD = HKD;
    }

    public double getIDR() {
        return IDR;
    }

    public void setIDR(double IDR) {
        this.IDR = IDR;
    }

    public double getILS() {
        return ILS;
    }

    public void setILS(double ILS) {
        this.ILS = ILS;
    }

    public double getINR() {
        return INR;
    }

    public void setINR(double INR) {
        this.INR = INR;
    }

    public double getKRW() {
        return KRW;
    }

    public void setKRW(double KRW) {
        this.KRW = KRW;
    }

    public double getMXN() {
        return MXN;
    }

    public void setMXN(double MXN) {
        this.MXN = MXN;
    }

    public double getMYR() {
        return MYR;
    }

    public void setMYR(double MYR) {
        this.MYR = MYR;
    }

    public double getNZD() {
        return NZD;
    }

    public void setNZD(double NZD) {
        this.NZD = NZD;
    }

    public double getPHP() {
        return PHP;
    }

    public void setPHP(double PHP) {
        this.PHP = PHP;
    }

    public double getSGD() {
        return SGD;
    }

    public void setSGD(double SGD) {
        this.SGD = SGD;
    }

    public double getTHB() {
        return THB;
    }

    public void setTHB(double THB) {
        this.THB = THB;
    }
}
