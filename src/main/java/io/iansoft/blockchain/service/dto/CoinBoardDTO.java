package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.iansoft.blockchain.domain.enumeration.CoinBoardType;

/**
 * A DTO for the CoinBoard entity.
 */
public class CoinBoardDTO implements Serializable {

    private Long id;

    private String title;

    private String context;

    private CoinBoardType coninBoardType;

    private ZonedDateTime createdat;

    private ZonedDateTime updatedat;

    private Long coinId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public CoinBoardType getConinBoardType() {
        return coninBoardType;
    }

    public void setConinBoardType(CoinBoardType coninBoardType) {
        this.coninBoardType = coninBoardType;
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

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
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

        CoinBoardDTO coinBoardDTO = (CoinBoardDTO) o;
        if(coinBoardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinBoardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinBoardDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", context='" + getContext() + "'" +
            ", coninBoardType='" + getConinBoardType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
