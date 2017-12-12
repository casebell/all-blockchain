package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A NewsBoard.
 */
@Entity
@Table(name = "news_board")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "newsboard")
public class NewsBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "url",length=500)
    @Size(max = 500)
    private String url;

    @NotNull
    @Size(max = 8000)
    @Column(name = "context", length = 8000, nullable = false)
    private String context;

    @Column(name = "hit")
    private Long hit;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "newsBoard")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NewsLike> newsLikes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public NewsBoard title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public NewsBoard url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContext() {
        return context;
    }

    public NewsBoard context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getHit() {
        return hit;
    }

    public NewsBoard hit(Long hit) {
        this.hit = hit;
        return this;
    }

    public void setHit(Long hit) {
        this.hit = hit;
    }

    public User getUser() {
        return user;
    }

    public NewsBoard user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<NewsLike> getNewsLikes() {
        return newsLikes;
    }

    public void setNewsLikes(Set<NewsLike> newsLikes) {
        this.newsLikes = newsLikes;
    }

    public NewsBoard newsLikes(Set<NewsLike> newsLikes) {
        this.newsLikes = newsLikes;
        return this;
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
        NewsBoard newsBoard = (NewsBoard) o;
        if (newsBoard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsBoard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewsBoard{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", context='" + getContext() + "'" +
            ", hit='" + getHit() + "'" +
            "}";
    }
}
