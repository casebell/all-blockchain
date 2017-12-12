package io.iansoft.blockchain.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A NewsLike.
 */
@Entity
@Table(name = "news_like")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "newslike")
public class NewsLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private NewsBoard newsBoard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsLike id(long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public NewsLike user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NewsBoard getNewsBoard() {
        return newsBoard;
    }

    public NewsLike newsBoard(NewsBoard newsBoard) {
        this.newsBoard = newsBoard;
        return this;
    }

    public void setNewsBoard(NewsBoard newsBoard) {
        this.newsBoard = newsBoard;
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
        NewsLike newsLike = (NewsLike) o;
        if (newsLike.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsLike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewsLike{" +
            "id=" + getId() +
            "}";
    }
}
