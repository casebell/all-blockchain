package io.iansoft.blockchain.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.iansoft.blockchain.domain.enumeration.ResourceType;

/**
 * A DTO for the Resource entity.
 */
public class ResourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private Long size;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ResourceType resourceType;

    private Long coinId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if(resourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", size='" + getSize() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            "}";
    }
}
