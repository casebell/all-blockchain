package io.iansoft.blockchain.service.dto;

public class UpbitCoinDTO {
    String name;
    int marketCoinId;

    public UpbitCoinDTO() {
    }

    public UpbitCoinDTO(String name, int marketCoinId) {
        this.name = name;
        this.marketCoinId = marketCoinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarketCoinId() {
        return marketCoinId;
    }

    public void setMarketCoinId(int marketCoinId) {
        this.marketCoinId = marketCoinId;
    }
}
