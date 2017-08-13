package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.service.CoinApiService;
import io.iansoft.blockchain.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoinApiResource {

    private final CoinApiService coinApiService;
    public CoinApiResource(CoinApiService coinApiService) {
        this.coinApiService = coinApiService;
    }

    @GetMapping("/coin-api/bithumb")
    public ResponseEntity<List<BithumbDataDTO>> getBithumb(){
        return ResponseEntity.ok(coinApiService.getBithumb());
    }

    @GetMapping("/coin-api/korbit")
    public ResponseEntity<List<KorbitDTO>> getKorbit(){
        return ResponseEntity.ok( coinApiService.getKorbit());
    }

    @GetMapping("/coin-api/okcoin-cn")
    public ResponseEntity<List<OkCoinCnDTO>> getOkCoinCn(){
        return ResponseEntity.ok( coinApiService.getOkCoinCn());
    }


    @GetMapping("/coin-api/bitflyer")
    public ResponseEntity<List<BitflyerDTO>> getBitflyer(){
        return ResponseEntity.ok( coinApiService.getBitflyer());
    }

    @GetMapping("/coin-api/bittrex")
    public ResponseEntity<List<BittrexDTO>> getBittrex(){
        return ResponseEntity.ok( coinApiService.getBittrex());
    }




}
