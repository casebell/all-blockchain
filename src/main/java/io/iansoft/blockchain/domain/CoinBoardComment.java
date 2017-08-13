package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CoinBoardComment.
 */
@Entity
@Table(name = "coin_board_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coinboardcomment")
public class CoinBoardComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "context")
    private String context;

    @Column(name = "group_no")
    private Long groupNo;

    @Column(name = "parent")
    private Long parent;

    @Column(name = "depth")
    private Long depth;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @Column(name = "updatedat")
    private ZonedDateTime updatedat;

    @ManyToOne
    private CoinBoard coinBoard;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public CoinBoardComment context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getGroupNo() {
        return groupNo;
    }

    public CoinBoardComment groupNo(Long groupNo) {
        this.groupNo = groupNo;
        return this;
    }

    public void setGroupNo(Long groupNo) {
        this.groupNo = groupNo;
    }

    public Long getParent() {
        return parent;
    }

    public CoinBoardComment parent(Long parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getDepth() {
        return depth;
    }

    public CoinBoardComment depth(Long depth) {
        this.depth = depth;
        return this;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public CoinBoardComment createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public ZonedDateTime getUpdatedat() {
        return updatedat;
    }

    public CoinBoardComment updatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
        return this;
    }

    public void setUpdatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public CoinBoard getCoinBoard() {
        return coinBoard;
    }

    public CoinBoardComment coinBoard(CoinBoard coinBoard) {
        this.coinBoard = coinBoard;
        return this;
    }

    public void setCoinBoard(CoinBoard coinBoard) {
        this.coinBoard = coinBoard;
    }

    public User getUser() {
        return user;
    }

    public CoinBoardComment user(User user) {
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
        CoinBoardComment coinBoardComment = (CoinBoardComment) o;
        if (coinBoardComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinBoardComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinBoardComment{" +
            "id=" + getId() +
            ", context='" + getContext() + "'" +
            ", groupNo='" + getGroupNo() + "'" +
            ", parent='" + getParent() + "'" +
            ", depth='" + getDepth() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
