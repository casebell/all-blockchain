package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A AirDrop.
 */
@Entity
@Table(name = "air_drop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "airdrop")
public class AirDrop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "coin_name", length = 255, nullable = false)
    private String coinName;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "finish_date", nullable = false)
    private Instant finishDate;

    @Column(name = "context")
    private String context;

    @Column(name = "jhi_value", precision=10, scale=2)
    private BigDecimal value;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "reddit")
    private String reddit;

    @Column(name = "website")
    private String website;

    @Column(name = "airdrop_page")
    private String airdropPage;

    @Column(name = "bitcoin_talk")
    private String bitcoinTalk;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public AirDrop coinName(String coinName) {
        this.coinName = coinName;
        return this;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public AirDrop startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public AirDrop finishDate(Instant finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public String getContext() {
        return context;
    }

    public AirDrop context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public BigDecimal getValue() {
        return value;
    }

    public AirDrop value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTelegram() {
        return telegram;
    }

    public AirDrop telegram(String telegram) {
        this.telegram = telegram;
        return this;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getTwitter() {
        return twitter;
    }

    public AirDrop twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public AirDrop facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getReddit() {
        return reddit;
    }

    public AirDrop reddit(String reddit) {
        this.reddit = reddit;
        return this;
    }

    public void setReddit(String reddit) {
        this.reddit = reddit;
    }

    public String getWebsite() {
        return website;
    }

    public AirDrop website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAirdropPage() {
        return airdropPage;
    }

    public AirDrop airdropPage(String airdropPage) {
        this.airdropPage = airdropPage;
        return this;
    }

    public void setAirdropPage(String airdropPage) {
        this.airdropPage = airdropPage;
    }

    public String getBitcoinTalk() {
        return bitcoinTalk;
    }

    public AirDrop bitcoinTalk(String bitcoinTalk) {
        this.bitcoinTalk = bitcoinTalk;
        return this;
    }

    public void setBitcoinTalk(String bitcoinTalk) {
        this.bitcoinTalk = bitcoinTalk;
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
        AirDrop airDrop = (AirDrop) o;
        if (airDrop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airDrop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AirDrop{" +
            "id=" + getId() +
            ", coinName='" + getCoinName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", context='" + getContext() + "'" +
            ", value=" + getValue() +
            ", telegram='" + getTelegram() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", reddit='" + getReddit() + "'" +
            ", website='" + getWebsite() + "'" +
            ", airdropPage='" + getAirdropPage() + "'" +
            ", bitcoinTalk='" + getBitcoinTalk() + "'" +
            "}";
    }
}
