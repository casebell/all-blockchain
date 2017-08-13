package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.iansoft.blockchain.domain.enumeration.CoinBoardType;

/**
 * A CoinBoard.
 */
@Entity
@Table(name = "coin_board")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coinboard")
public class CoinBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "context")
    private String context;

    @Enumerated(EnumType.STRING)
    @Column(name = "conin_board_type")
    private CoinBoardType coninBoardType;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @Column(name = "updatedat")
    private ZonedDateTime updatedat;

    @ManyToOne
    private Coin coin;

    @OneToMany(mappedBy = "coinBoard")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CoinBoardComment> coinBoardComments = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CoinBoard title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public CoinBoard context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public CoinBoardType getConinBoardType() {
        return coninBoardType;
    }

    public CoinBoard coninBoardType(CoinBoardType coninBoardType) {
        this.coninBoardType = coninBoardType;
        return this;
    }

    public void setConinBoardType(CoinBoardType coninBoardType) {
        this.coninBoardType = coninBoardType;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public CoinBoard createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public ZonedDateTime getUpdatedat() {
        return updatedat;
    }

    public CoinBoard updatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
        return this;
    }

    public void setUpdatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public Coin getCoin() {
        return coin;
    }

    public CoinBoard coin(Coin coin) {
        this.coin = coin;
        return this;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public Set<CoinBoardComment> getCoinBoardComments() {
        return coinBoardComments;
    }

    public CoinBoard coinBoardComments(Set<CoinBoardComment> coinBoardComments) {
        this.coinBoardComments = coinBoardComments;
        return this;
    }

    public CoinBoard addCoinBoardComment(CoinBoardComment coinBoardComment) {
        this.coinBoardComments.add(coinBoardComment);
        coinBoardComment.setCoinBoard(this);
        return this;
    }

    public CoinBoard removeCoinBoardComment(CoinBoardComment coinBoardComment) {
        this.coinBoardComments.remove(coinBoardComment);
        coinBoardComment.setCoinBoard(null);
        return this;
    }

    public void setCoinBoardComments(Set<CoinBoardComment> coinBoardComments) {
        this.coinBoardComments = coinBoardComments;
    }

    public User getUser() {
        return user;
    }

    public CoinBoard user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoinBoard coinBoard = (CoinBoard) o;
        if (coinBoard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinBoard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinBoard{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", context='" + getContext() + "'" +
            ", coninBoardType='" + getConinBoardType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
