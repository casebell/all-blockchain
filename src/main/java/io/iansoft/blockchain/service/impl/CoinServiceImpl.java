package io.iansoft.blockchain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.iansoft.blockchain.service.CoinService;
import io.iansoft.blockchain.domain.Coin;
import io.iansoft.blockchain.repository.CoinRepository;
import io.iansoft.blockchain.repository.search.CoinSearchRepository;
import io.iansoft.blockchain.service.dto.CoinDTO;
import io.iansoft.blockchain.service.dto.KorbitDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Coin.
 */
@Service
@Transactional
public class CoinServiceImpl implements CoinService {

    private final Logger log = LoggerFactory.getLogger(CoinServiceImpl.class);
    private final CoinRepository coinRepository;
    private final CoinSearchRepository coinSearchRepository;
    private final ModelMapper modelMapper;

    public CoinServiceImpl(CoinRepository coinRepository, CoinSearchRepository coinSearchRepository, ModelMapper modelMapper) {
        this.coinRepository = coinRepository;
        this.coinSearchRepository = coinSearchRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public KorbitDTO getKorbitBitcoin() {
        return getKorbitDTO("btc_krw");
    }

    @Override
    public KorbitDTO getKorbitEtc() {

        return getKorbitDTO("etc_krw");
    }

    @Override
    public KorbitDTO getKorbitEth() {

        return getKorbitDTO("eth_krw");
    }

    @Override
    public KorbitDTO getKorbitXrp() {

        return getKorbitDTO("xrp_krw");
    }


    private KorbitDTO getKorbitDTO(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/plain;charset=utf-8"));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> korbitRes = restTemplate
            .exchange("https://api.korbit.co.kr/v1/ticker/detailed?currency_pair=" +currency, HttpMethod.GET,entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        KorbitDTO korbitDTO = new KorbitDTO();
        try {
            korbitDTO =mapper.readValue(korbitRes.getBody(), KorbitDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return korbitDTO;
    }

    /**
     * Save a coin.
     *
     * @param coinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoinDTO save(CoinDTO coinDTO) {
        log.debug("Request to save Coin : {}", coinDTO);
        Coin coin = modelMapper.map(coinDTO, Coin.class);
        coin = coinRepository.save(coin);
        CoinDTO result = modelMapper.map(coin, CoinDTO.class);
        coinSearchRepository.save(coin);
        return result;
    }

    /**
     *  Get all the coins.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinDTO> findAll() {
        log.debug("Request to get all Coins");
        return coinRepository.findAll().stream()
            .map( coin-> modelMapper.map(coin,CoinDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one coin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoinDTO findOne(Long id) {
        log.debug("Request to get Coin : {}", id);
        Coin coin = coinRepository.findOne(id);
        return modelMapper.map(coin, CoinDTO.class);
    }

    /**
     *  Delete the  coin by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coin : {}", id);
        coinRepository.delete(id);
        coinSearchRepository.delete(id);
    }

    /**
     * Search for the coin corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinDTO> search(String query) {
        log.debug("Request to search Coins for query {}", query);
        return StreamSupport
            .stream(coinSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(coin-> modelMapper.map(coin,CoinDTO.class))
            .collect(Collectors.toList());
    }
}
