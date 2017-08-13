package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.iansoft.blockchain.domain.enumeration.ConsensusAlgorithms;

/**
 * A DTO for the Coin entity.
 */
public class CoinDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String founder;

    private ConsensusAlgorithms consensusAlgorithms;

    private String homepage;

    private String whitePaper;

    private String context;

    private ZonedDateTime releaseat;

    private ZonedDateTime createdat;

    private ZonedDateTime updatedat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public ConsensusAlgorithms getConsensusAlgorithms() {
        return consensusAlgorithms;
    }

    public void setConsensusAlgorithms(ConsensusAlgorithms consensusAlgorithms) {
        this.consensusAlgorithms = consensusAlgorithms;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getWhitePaper() {
        return whitePaper;
    }

    public void setWhitePaper(String whitePaper) {
        this.whitePaper = whitePaper;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ZonedDateTime getReleaseat() {
        return releaseat;
    }

    public void setReleaseat(ZonedDateTime releaseat) {
        this.releaseat = releaseat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoinDTO coinDTO = (CoinDTO) o;
        if(coinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoinDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", founder='" + getFounder() + "'" +
            ", consensusAlgorithms='" + getConsensusAlgorithms() + "'" +
            ", homepage='" + getHomepage() + "'" +
            ", whitePaper='" + getWhitePaper() + "'" +
            ", context='" + getContext() + "'" +
            ", releaseat='" + getReleaseat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
