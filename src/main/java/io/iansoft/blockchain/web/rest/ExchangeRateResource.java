package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.domain.FixerExchangeRates;
import io.iansoft.blockchain.service.CoinApiService;
import io.iansoft.blockchain.service.ExchangeRateService;
import io.iansoft.blockchain.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeRateResource {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateResource(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/coin-api/fixer")
    public ResponseEntity<List<FixerExchangeRates>> getFixers(){

        return ResponseEntity.ok(exchangeRateService.getFixers());
    }
}
