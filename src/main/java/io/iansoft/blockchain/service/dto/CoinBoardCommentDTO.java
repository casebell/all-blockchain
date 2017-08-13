package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CoinBoardComment entity.
 */
public class CoinBoardCommentDTO implements Serializable {

    private Long id;

    private String context;

    private Long groupNo;

    private Long parent;

    private Long depth;

    private ZonedDateTime createdat;

    private ZonedDateTime updatedat;

    private Long coinBoardId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Long groupNo) {
        this.groupNo = groupNo;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public ZonedDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public Long getCoinBoardId() {
        return coinBoardId;
    }

    public void setCoinBoardId(Long coinBoardId) {
        this.coinBoardId = coinBoardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoinBoardCommentDTO coinBoardCommentDTO = (CoinBoardCommentDTO) o;
        if(coinBoardCommentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinBoardCommentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinBoardCommentDTO{" +
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
