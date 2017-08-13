package io.iansoft.blockchain.service.dto;
import java.io.Serializable;

public class BithumbDTO implements Serializable {

    private String status;
    private BithumbDataDTO data;

    public String getStatus() {
        return status;
    }

    public BithumbDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public BithumbDataDTO getData() {
        return data;
    }

    public BithumbDTO setData(BithumbDataDTO data) {
        this.data = data;
        return this;
    }
}

