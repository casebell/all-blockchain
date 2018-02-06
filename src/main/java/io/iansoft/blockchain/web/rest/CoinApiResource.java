package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.iansoft.blockchain.domain.Quote;
import io.iansoft.blockchain.repository.QuoteRepository;
import io.iansoft.blockchain.service.CoinApiService;
import io.iansoft.blockchain.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CoinApiResource {

    private final CoinApiService coinApiService;

    private final QuoteRepository quoteRepository;


    private final ModelMapper modelMapper;
    public CoinApiResource(CoinApiService coinApiService, QuoteRepository quoteRepository,
                          ModelMapper modelMapper) {
        this.coinApiService = coinApiService;
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/coin-api/bithumb")
    public ResponseEntity<List<BithumbDataDTO>> getBithumb(){

        return ResponseEntity.ok(coinApiService.getBithumb());
    }

    @GetMapping("/coin-api/korbit")
    public ResponseEntity<List<KorbitDTO>> getKorbit(){

        return ResponseEntity.ok( coinApiService.getKorbit());
    }

    @GetMapping("/coin-api/bitflyer")
    public ResponseEntity<List<BitflyerDTO>> getBitflyer(){
        return ResponseEntity.ok( coinApiService.getBitflyer());
    }

    @GetMapping("/coin-api/bittrex")
    public ResponseEntity<List<BittrexDTO>> getBittrex(){
        return ResponseEntity.ok( coinApiService.getBittrex());
    }

    @GetMapping("/coin-api/upbit/{upBitsMarketCoinIds}")
    @Timed
    public ResponseEntity<List<QuoteDTO>> getLastUpBits(@PathVariable Long[] upBitsMarketCoinIds) {
      //  log.debug("REST request to get Quote : {}", Arrays.toString(upBitsMarketCoinIds));
        List<Quote> quotes = new ArrayList<>();
        for (Long upBitsMarketCoin: upBitsMarketCoinIds) {
            Quote quote = quoteRepository.findFirstByMarketCoinIdOrderByIdDesc(upBitsMarketCoin);
            quotes.add(quote);
        }
        List<QuoteDTO> quoteDTO = quotes.stream().map(x->modelMapper.map(x,QuoteDTO.class)).collect(Collectors.toList());

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quoteDTO));
    }



}
