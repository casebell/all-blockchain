package io.iansoft.blockchain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.repository.*;
import io.iansoft.blockchain.service.CoinApiService;
import io.iansoft.blockchain.service.dto.*;
import io.iansoft.blockchain.service.mapper.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CoinApiServiceImpl implements CoinApiService {
    private static final String BTC_SYMBOL = "btc";
    private static final String ETH_SYMBOL = "eth";
    private static final String LTC_SYMBOL = "ltc";
    private static final String XRP_SYMBOL = "xrp";
    private static final String DASH_SYMBOL = "dash";
    private static final String ETC_SYMBOL = "etc";
    private static final String BCH_SYMBOL = "bch";
    private static final String ZEC_SYMBOL = "zec";
    private static final String XMR_SYMBOL = "xmr";
    private static final String NEO_SYMBOL = "neo";
    private final Logger log = LoggerFactory.getLogger(CoinApiServiceImpl.class);
    private final BithumbRepository bithumbRepository;
    private final BithumbMapper bithumbMapper;
    private final KorbitMapper korbitMapper;
    private final KorbitRepository korbitRepository;
    private final KrakenRepository krakenRepository;
    private final OkCoinCnMapper okCoinCnMapper;
    private final OkCoinCnRepository okCoinCnRepository;
    private final BitflyerRepository bitflyerRepository;
    private final BitflyerMapper bitflyerMapper;
    private final BittrexRepository bittrexRepository;
    private final BittrexMapper bittrexMapper;
    private final KrakenMapper krakenMapper;
    private final ModelMapper modelMapper;

    private ZonedDateTime zonedDateTime;

    public CoinApiServiceImpl(BithumbRepository bithumbRepository, BithumbMapper bithumbMapper, KorbitMapper korbitMapper, KorbitRepository korbitRepository, KrakenRepository krakenRepository, OkCoinCnMapper okCoinCnMapper, OkCoinCnRepository okCoinCnRepository, BitflyerRepository bitflyerRepository, BitflyerMapper bitflyerMapper, BittrexRepository bittrexRepository, BittrexMapper bittrexMapper, KrakenMapper krakenMapper, ModelMapper modelMapper) {
        this.bithumbRepository = bithumbRepository;
        this.bithumbMapper = bithumbMapper;
        this.korbitMapper = korbitMapper;
        this.korbitRepository = korbitRepository;
        this.krakenRepository = krakenRepository;
        this.okCoinCnMapper = okCoinCnMapper;
        this.okCoinCnRepository = okCoinCnRepository;
        this.bitflyerRepository = bitflyerRepository;
        this.bitflyerMapper = bitflyerMapper;
        this.bittrexRepository = bittrexRepository;
        this.bittrexMapper = bittrexMapper;
        this.krakenMapper = krakenMapper;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<BithumbDataDTO> getBithumb() {
        return bithumbRepository.findBihumbs().stream()
            .map(bithumbMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KorbitDTO> getKorbit() {
        return korbitRepository.findKorbits().stream()
            .map(korbitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OkCoinCnDTO> getOkCoinCn() {
        return okCoinCnRepository.findOkCOinCn().stream()
            .map(okCoinCnMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public List<BitflyerDTO> getBitflyer() {
        return bitflyerRepository.findBitflyers().stream()
            .map(bitflyerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BittrexDTO> getBittrex() {
        return bittrexRepository.findBittrexs().stream()
            .map(bittrexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Scheduled(cron = "*/15 * * * * *")
    public void getCoinApi() {
        List<BithumbDTO> bithumbDTOS = new ArrayList();
        bithumbDTOS.add(getBithumbRestBtc());
        bithumbDTOS.add(getBithumbRestEth());
        bithumbDTOS.add(getBithumbRestXrp());
        bithumbDTOS.add(getBithumbRestDash());
        bithumbDTOS.add(getBithumbRestLtc());
        bithumbDTOS.add(getBithumbRestEtc());
        bithumbDTOS.add(getBithumbRestBch());
        zonedDateTime = ZonedDateTime.now();
        bithumbDTOS.forEach(this::saveBithumb);

        List<KorbitDTO> korbitDTOS = new ArrayList();
        korbitDTOS.add(getKorbitRestBtc());
        korbitDTOS.add(getKorbitRestEth());
        korbitDTOS.add(getKorbitRestEtc());
        korbitDTOS.add(getKorbitRestXrp());
        korbitDTOS.forEach(this::saveKorbit);

        List<OkCoinCnDTO> okCoinDTOS = new ArrayList();
        okCoinDTOS.add(getOkCoinCnRestBtc());
        okCoinDTOS.add(getOkCoinCnRestEth());
        okCoinDTOS.add(getOkCoinCnRestLtc());
        okCoinDTOS.add(getOkCoinCnRestEtc());
        okCoinDTOS.add(getOkCoinCnRestBch());
        okCoinDTOS.forEach(this::saveOkCoinCn);

        BitflyerDTO bitflyerDTO = getBitflyerResBtc();
        saveBitflyer(bitflyerDTO);

        getBittrexs().forEach(this::saveBittrex);

        getKrakens().forEach(this::saveKraken);
    }


    private void saveBithumb(BithumbDTO bithumbDTO) {
        if ("0000".equals(bithumbDTO.getStatus())) {
            //log.debug("bitcoinDto = {} getting time is {} ", bithumbDTO.toString(), LocalDateTime.now());
            Bithumb bithumb = bithumbMapper.toEntity(bithumbDTO.getData());
            bithumb.setCreatedat(zonedDateTime);
            bithumbRepository.save(bithumb);
        } else {
            log.error("get bithumb api error  code : {} ", bithumbDTO.getStatus());
        }
    }


    private void saveKorbit(KorbitDTO korbitDTO) {
        //log.debug("korbinDto = {} getting time is {} ", korbitDTO.toString(), LocalDateTime.now());
        Korbit korbit = korbitMapper.toEntity(korbitDTO);
        korbit.setCreatedat(zonedDateTime);
        korbitRepository.save(korbit);
    }

    private void saveOkCoinCn(OkCoinCnDTO okCoinCnDTO) {
        // log.debug("bitcoinDto = {} getting time is {} ", korbitDTO.toString(), LocalDateTime.now());
        OkCoinCn okCoinCn = okCoinCnMapper.toEntity(okCoinCnDTO);
        okCoinCn.setCreatedat(zonedDateTime);
        okCoinCnRepository.save(okCoinCn);
    }

    private void saveBitflyer(BitflyerDTO bitflyerDTO) {
        Bitflyer bitflyer = bitflyerMapper.toEntity(bitflyerDTO);
        bitflyer.setCreatedat(zonedDateTime);
        bitflyerRepository.save(bitflyer);
    }

    private void saveBittrex(BittrexDTO bittrexDTO) {
        Bittrex bittrex = bittrexMapper.toEntity(bittrexDTO);
        bittrex.setCreatedat(zonedDateTime);
        bittrexRepository.save(bittrex);
    }

    private void saveKraken(KrakenDTO krakenDTO) {
        Kraken kraken = krakenMapper.toEntity(krakenDTO);
        kraken.setCreatedat(zonedDateTime);
        krakenRepository.save(kraken);
    }


    private KorbitDTO getKorbitRestBtc() {
        return getKorbitDTO("btc_krw");
    }

    private KorbitDTO getKorbitRestEth() {
        return getKorbitDTO("eth_krw");
    }

    private KorbitDTO getKorbitRestEtc() {
        return getKorbitDTO("etc_krw");
    }

    private KorbitDTO getKorbitRestXrp() {
        return getKorbitDTO("xrp_krw");
    }

    private KorbitDTO getKorbitDTO(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/plain;charset=utf-8"));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> korbitRes = restTemplate
            .exchange("https://api.korbit.co.kr/v1/ticker/detailed?currency_pair=" + currency, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        KorbitDTO korbitDTO = new KorbitDTO();
        try {
            korbitDTO = mapper.readValue(korbitRes.getBody(), KorbitDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ("btc_krw".equals(currency))
            korbitDTO.setSymbol(BTC_SYMBOL);
        else if ("eth_krw".equals(currency))
            korbitDTO.setSymbol(ETH_SYMBOL);
        else if ("etc_krw".equals(currency))
            korbitDTO.setSymbol(ETC_SYMBOL);
        else
            korbitDTO.setSymbol(XRP_SYMBOL);

        return korbitDTO;
    }

    /*
    BTC https://www.okcoin.cn/api/v1/ticker.do?symbol=btc_cny
LTC https://www.okcoin.cn/api/v1/ticker.do?symbol=ltc_cny
ETH https://www.okcoin.cn/api/v1/ticker.do?symbol=eth_cny
     */
    private OkCoinCnDTO getOkCoinCnRestBtc() {
        return getOkCoinCnDTO("btc_cny");
    }

    private OkCoinCnDTO getOkCoinCnRestEth() {
        return getOkCoinCnDTO("eth_cny");
    }

    private OkCoinCnDTO getOkCoinCnRestEtc() {
        return getOkCoinCnDTO("etc_cny");
    }
    private OkCoinCnDTO getOkCoinCnRestBch() {
        return getOkCoinCnDTO("bcc_cny");
    }

    private OkCoinCnDTO getOkCoinCnRestLtc() {
        return getOkCoinCnDTO("ltc_cny");
    }

    private OkCoinCnDTO getOkCoinCnDTO(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        String okCoinCnRes = restTemplate.getForObject("https://www.okcoin.cn/api/v1/ticker.do?symbol=" + currency, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map map = new HashMap();
        try {
            map = mapper.readValue(okCoinCnRes, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkCoinCnDTO okCoinCnDTO = modelMapper.map(map.get("ticker"), OkCoinCnDTO.class);
        if ("btc_cny".equals(currency))
            okCoinCnDTO.setSymbol(BTC_SYMBOL);
        else if ("eth_cny".equals(currency))
            okCoinCnDTO.setSymbol(ETH_SYMBOL);
        else if ("ltc_cny".equals(currency))
            okCoinCnDTO.setSymbol(LTC_SYMBOL);
        else if ("etc_cny".equals(currency))
            okCoinCnDTO.setSymbol(ETC_SYMBOL);
        else if ("bcc_cny".equals(currency))
            okCoinCnDTO.setSymbol(BCH_SYMBOL);

        okCoinCnDTO.setSymbol(currency);
        return okCoinCnDTO;
    }

    private List<BittrexDTO> getBittrexs() {
        List<BittrexDTO> bittrexDTOS = new ArrayList();

        bittrexDTOS.add(getBittrexRest("USDT-BTC"));
        bittrexDTOS.add(getBittrexRest("USDT-ETH"));
        bittrexDTOS.add(getBittrexRest("USDT-XRP"));
        bittrexDTOS.add(getBittrexRest("USDT-DASH"));
        bittrexDTOS.add(getBittrexRest("USDT-LTC"));
        bittrexDTOS.add(getBittrexRest("USDT-ETC"));
        bittrexDTOS.add(getBittrexRest("USDT-BCC"));
        bittrexDTOS.add(getBittrexRest("USDT-ZEC"));
        bittrexDTOS.add(getBittrexRest("USDT-XMR"));
        bittrexDTOS.add(getBittrexRest("USDT-NEO"));

        return bittrexDTOS;
    }

    private List<KrakenDTO> getKrakens() {
        List<KrakenDTO> krakenDTOS = new ArrayList();

        krakenDTOS.add(getKrakenRest("BTCEUR"));
        krakenDTOS.add(getKrakenRest("ETHEUR"));
        krakenDTOS.add(getKrakenRest("XRPEUR"));
        krakenDTOS.add(getKrakenRest("DASHEUR"));
        krakenDTOS.add(getKrakenRest("LTCEUR"));
        krakenDTOS.add(getKrakenRest("ETCEUR"));
        krakenDTOS.add(getKrakenRest("BCHEUR"));
        krakenDTOS.add(getKrakenRest("ZECEUR"));
        krakenDTOS.add(getKrakenRest("XMREUR"));

        return krakenDTOS;
    }

    private KrakenDTO getKrakenRest(String market) {

        RestTemplate restTemplate = new RestTemplate();
        Map krakenMap = restTemplate.getForObject("https://api.kraken.com/0/public/Ticker?pair=" + market, Map.class);
        KrakenDTO krakenDTO = new KrakenDTO();
        switch (market) {
            case "BTCEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, BTC_SYMBOL, "XXBTZEUR");
                break;
            case "ETHEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, ETH_SYMBOL, "XETHZEUR");
                break;
            case "XRPEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, XRP_SYMBOL, "XXRPZEUR");
                break;
            case "DASHEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, DASH_SYMBOL, "DASHEUR");
                break;
            case "LTCEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, LTC_SYMBOL, "XLTCZEUR");
                break;
            case "ETCEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, ETC_SYMBOL, "XETCZEUR");
                break;
            case "BCHEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, BCH_SYMBOL, "BCHEUR");
                break;
            case "ZECEUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, ZEC_SYMBOL, "XZECZEUR");
                break;
            case "XMREUR":
                krakenDTO = setKrakenRest(krakenMap, krakenDTO, XMR_SYMBOL, "XXMRZEUR");
                break;
        }

        return krakenDTO ;
    }

    private KrakenDTO setKrakenRest(Map krakenMap, KrakenDTO krakenDTO, String symbol, String krakenSymbol) {
        List<String> last = (List) ((Map)((Map) krakenMap.get("result")).get(krakenSymbol)).get("c");
        krakenDTO.setLast(last.get(0));
        krakenDTO.setSymbol(symbol);
        return krakenDTO;
    }

    private BittrexDTO getBittrexRest(String market) {

        RestTemplate restTemplate = new RestTemplate();
        Map bittrexMap = restTemplate.getForObject("https://bittrex.com/api/v1.1/public/getticker?market=" + market, Map.class);
        Map<Object, Object> resultBittrexMap = (Map<Object, Object>) bittrexMap.get("result");
        BittrexDTO bittrexDTO = modelMapper.map(resultBittrexMap, BittrexDTO.class);
        switch (market) {
            case "USDT-BTC":
                bittrexDTO.setSymbol(BTC_SYMBOL);
                break;
            case "USDT-ETH":
                bittrexDTO.setSymbol(ETH_SYMBOL);
                break;
            case "USDT-XRP":
                bittrexDTO.setSymbol(XRP_SYMBOL);
                break;
            case "USDT-DASH":
                bittrexDTO.setSymbol(DASH_SYMBOL);
                break;
            case "USDT-LTC":
                bittrexDTO.setSymbol(LTC_SYMBOL);
                break;
            case "USDT-ETC":
                bittrexDTO.setSymbol(ETC_SYMBOL);
                break;
            case "USDT-BCC":
                bittrexDTO.setSymbol(BCH_SYMBOL);
                break;
            case "USDT-ZEC":
                bittrexDTO.setSymbol(ZEC_SYMBOL);
                break;
            case "USDT-XMR":
                bittrexDTO.setSymbol(XMR_SYMBOL);
                break;
            case "USDT-NEO":
                bittrexDTO.setSymbol(NEO_SYMBOL);
                break;
        }
        return bittrexDTO ;
    }


    private BitflyerDTO getBitflyerResBtc() {
        RestTemplate restTemplate = new RestTemplate();
        BitflyerDTO bitflyerDTO = restTemplate
            .getForObject("https://api.bitflyer.jp/v1/ticker", BitflyerDTO.class);
        bitflyerDTO.setSymbol(BTC_SYMBOL);
        return bitflyerDTO;
    }


    private BithumbDTO getBithumbRestBtc() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/btc", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(BTC_SYMBOL);
        return bithumbDTO;
    }


    private BithumbDTO getBithumbRestEth() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/eth", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(ETH_SYMBOL);
        return bithumbDTO;
    }

    private BithumbDTO getBithumbRestXrp() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/xrp", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(XRP_SYMBOL);
        return bithumbDTO;
    }


    private BithumbDTO getBithumbRestDash() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/dash", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(DASH_SYMBOL);
        return bithumbDTO;
    }

    private BithumbDTO getBithumbRestLtc() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/ltc", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(LTC_SYMBOL);
        return bithumbDTO;
    }


    private BithumbDTO getBithumbRestEtc() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/etc", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(ETC_SYMBOL);
        return bithumbDTO;
    }

    private BithumbDTO getBithumbRestBch() {
        RestTemplate restTemplate = new RestTemplate();
        BithumbDTO bithumbDTO = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/bch", BithumbDTO.class);
        bithumbDTO.getData().setSymbol(BCH_SYMBOL);
        return bithumbDTO;
    }
}
