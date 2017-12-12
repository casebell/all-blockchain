package io.iansoft.blockchain.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the NewsLike entity.
 */
public class NewsLikeDTO implements Serializable {

    private Long id;

    private Long userId;

    private String userLogin;

    private Long newsBoardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getNewsBoardId() {
        return newsBoardId;
    }

    public void setNewsBoardId(Long newsBoardId) {
        this.newsBoardId = newsBoardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewsLikeDTO newsLikeDTO = (NewsLikeDTO) o;
        if(newsLikeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsLikeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewsLikeDTO{" +
            "id=" + getId() +
            "}";
    }
}
