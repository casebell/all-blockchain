package io.iansoft.blockchain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.iansoft.blockchain.domain.enumeration.ConsensusAlgorithms;

/**
 * A Coin.
 */
@Entity
@Table(name = "coin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coin")
public class Coin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "founder")
    private String founder;

    @Enumerated(EnumType.STRING)
    @Column(name = "consensus_algorithms")
    private ConsensusAlgorithms consensusAlgorithms;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "white_paper")
    private String whitePaper;

    @Column(name = "context")
    private String context;

    @Column(name = "releaseat")
    private ZonedDateTime releaseat;

    @Column(name = "createdat")
    private ZonedDateTime createdat;

    @Column(name = "updatedat")
    private ZonedDateTime updatedat;

    @OneToMany(mappedBy = "coin")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resource> resources = new HashSet<>();

    @OneToMany(mappedBy = "coin")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CoinBoard> boards = new HashSet<>();

    @OneToOne
    @JsonIgnore
    private Bitfinex bitfinex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public Coin founder(String founder) {
        this.founder = founder;
        return this;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public ConsensusAlgorithms getConsensusAlgorithms() {
        return consensusAlgorithms;
    }

    public Coin consensusAlgorithms(ConsensusAlgorithms consensusAlgorithms) {
        this.consensusAlgorithms = consensusAlgorithms;
        return this;
    }

    public void setConsensusAlgorithms(ConsensusAlgorithms consensusAlgorithms) {
        this.consensusAlgorithms = consensusAlgorithms;
    }

    public String getHomepage() {
        return homepage;
    }

    public Coin homepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getWhitePaper() {
        return whitePaper;
    }

    public Coin whitePaper(String whitePaper) {
        this.whitePaper = whitePaper;
        return this;
    }

    public void setWhitePaper(String whitePaper) {
        this.whitePaper = whitePaper;
    }

    public String getContext() {
        return context;
    }

    public Coin context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ZonedDateTime getReleaseat() {
        return releaseat;
    }

    public Coin releaseat(ZonedDateTime releaseat) {
        this.releaseat = releaseat;
        return this;
    }

    public void setReleaseat(ZonedDateTime releaseat) {
        this.releaseat = releaseat;
    }

    public ZonedDateTime getCreatedat() {
        return createdat;
    }

    public Coin createdat(ZonedDateTime createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(ZonedDateTime createdat) {
        this.createdat = createdat;
    }

    public ZonedDateTime getUpdatedat() {
        return updatedat;
    }

    public Coin updatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
        return this;
    }

    public void setUpdatedat(ZonedDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Coin resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public Coin addResource(Resource resource) {
        this.resources.add(resource);
        resource.setCoin(this);
        return this;
    }

    public Coin removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.setCoin(null);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<CoinBoard> getBoards() {
        return boards;
    }

    public Coin boards(Set<CoinBoard> coinBoards) {
        this.boards = coinBoards;
        return this;
    }

    public Coin addBoard(CoinBoard coinBoard) {
        this.boards.add(coinBoard);
        coinBoard.setCoin(this);
        return this;
    }

    public Coin removeBoard(CoinBoard coinBoard) {
        this.boards.remove(coinBoard);
        coinBoard.setCoin(null);
        return this;
    }

    public Bitfinex getBitfinex() {
        return bitfinex;
    }

    public void setBitfinex(Bitfinex bitfinex) {
        this.bitfinex = bitfinex;
    }

    public void setBoards(Set<CoinBoard> coinBoards) {
        this.boards = coinBoards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coin coin = (Coin) o;
        if (coin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coin{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", founder='").append(founder).append('\'');
        sb.append(", consensusAlgorithms=").append(consensusAlgorithms);
        sb.append(", homepage='").append(homepage).append('\'');
        sb.append(", whitePaper='").append(whitePaper).append('\'');
        sb.append(", context='").append(context).append('\'');
        sb.append(", releaseat=").append(releaseat);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", resources=").append(resources);
        sb.append(", boards=").append(boards);
        sb.append(", bitfinex=").append(bitfinex);
        sb.append('}');
        return sb.toString();
    }
}
