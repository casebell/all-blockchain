package io.iansoft.blockchain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.repository.*;
import io.iansoft.blockchain.service.CoinApiService;
import io.iansoft.blockchain.service.dto.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
    private static final String QTUM_SYMBOL = "qtum";
    private static final String BTG_SYMBOL = "btg";
    private static final String EOS_SYMBOL = "eos";
    private final Logger log = LoggerFactory.getLogger(CoinApiServiceImpl.class);
    private final BithumbRepository bithumbRepository;
    private final KorbitRepository korbitRepository;
    private final KrakenRepository krakenRepository;
    private final BitflyerRepository bitflyerRepository;
    private final BittrexRepository bittrexRepository;
    private final MarketCoinRepository marketCoinRepository;
    private final QuoteRepository quoteRepository;

    private final ModelMapper modelMapper;

    private ZonedDateTime zonedDateTime;

    public CoinApiServiceImpl(BithumbRepository bithumbRepository,
                              KorbitRepository korbitRepository,
                              KrakenRepository krakenRepository,
                              BitflyerRepository bitflyerRepository,
                              BittrexRepository bittrexRepository,
                              MarketCoinRepository marketCoinRepository, QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.bithumbRepository = bithumbRepository;
        this.korbitRepository = korbitRepository;
        this.krakenRepository = krakenRepository;
        this.bitflyerRepository = bitflyerRepository;
        this.bittrexRepository = bittrexRepository;
        this.marketCoinRepository = marketCoinRepository;
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<BithumbDataDTO> getBithumb() {
        List<BithumbDataDTO> getBithumb = bithumbRepository.findBihumbs().stream()
            .map(bithumb -> modelMapper.map(bithumb,BithumbDataDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
        return getBithumb;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KorbitDTO> getKorbit() {
        return korbitRepository.findKorbits().stream()
            .map( korbit -> modelMapper.map(korbit,KorbitDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public List<BitflyerDTO> getBitflyer() {
        return bitflyerRepository.findBitflyers().stream()
            .map( bitflyer -> modelMapper.map(bitflyer,BitflyerDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BittrexDTO> getBittrex() {
        return bittrexRepository.findBittrexs().stream()
            .map(bittrex -> modelMapper.map(bittrex,BittrexDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Scheduled(cron = "*/15 * * * * *")
    public void getCoinApi() {
        zonedDateTime = ZonedDateTime.now();

        getBithumbRest().forEach(this::saveBithumb);
        List<KorbitDTO> korbitDTOS = new ArrayList();
        korbitDTOS.add(getKorbitRestBtc());
        korbitDTOS.add(getKorbitRestEth());
        korbitDTOS.add(getKorbitRestEtc());
        korbitDTOS.add(getKorbitRestXrp());
        korbitDTOS.forEach(this::saveKorbit);

        BitflyerDTO bitflyerDTO = getBitflyerResBtc();
        saveBitflyer(bitflyerDTO);

        getBittrexs().forEach(this::saveBittrex);

        getKrakens().forEach(this::saveKraken);

       // getBitfinex().forEach(this::saveBitfinex);

    }


    private void saveBithumb(BithumbDataDTO bithumbDTO) {
        Bithumb bithumb = modelMapper.map(bithumbDTO,Bithumb.class);
        bithumb.setCreatedat(zonedDateTime);
        bithumbRepository.save(bithumb);
    }


    private void saveKorbit(KorbitDTO korbitDTO) {
        //log.debug("korbinDto = {} getting time is {} ", korbitDTO.toString(), LocalDateTime.now());
        Korbit korbit = modelMapper.map(korbitDTO,Korbit.class);
        korbit.setCreatedat(zonedDateTime);
        korbitRepository.save(korbit);
    }


    private void saveBitflyer(BitflyerDTO bitflyerDTO) {
        Bitflyer bitflyer = modelMapper.map(bitflyerDTO,Bitflyer.class);
        bitflyer.setCreatedat(zonedDateTime);
        bitflyerRepository.save(bitflyer);
    }

    private void saveBittrex(BittrexDTO bittrexDTO) {
        Bittrex bittrex = modelMapper.map(bittrexDTO,Bittrex.class);
        bittrex.setCreatedat(zonedDateTime);
        bittrexRepository.save(bittrex);
    }

    private void saveKraken(KrakenDTO krakenDTO) {
        Kraken kraken = modelMapper.map(krakenDTO, Kraken.class);
        kraken.setCreatedat(zonedDateTime);
        krakenRepository.save(kraken);
    }

/*   private void saveBitfinex(BitfinexDTO bitfinexDTO) {
        Bitfinex bitfinex = modelMapper.map(bitfinexDTO, Bitfinex.class);
        bitfinex.setCreatedat(zonedDateTime);
        bitfinexRepository.save(bitfinex);
    }
 */

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

   /*  private List<BitfinexDTO> getBitfinex() {
        List<BitfinexDTO> bitfinexDTOs = new ArrayList();

        bitfinexDTOs.add(getBitfinexRest("btcusd"));
        bitfinexDTOs.add(getBitfinexRest("ethusd"));
        bitfinexDTOs.add(getBitfinexRest("xrpusd"));
        bitfinexDTOs.add(getBitfinexRest("dshusd"));
        bitfinexDTOs.add(getBitfinexRest("ltcusd"));
        bitfinexDTOs.add(getBitfinexRest("etcusd"));
        bitfinexDTOs.add(getBitfinexRest("bchusd"));
        bitfinexDTOs.add(getBitfinexRest("zecusd"));
        bitfinexDTOs.add(getBitfinexRest("xmrusd"));

        return bitfinexDTOs;
    } */

    private KrakenDTO getKrakenRest(String market) {

        RestTemplate restTemplate = new RestTemplate();
        Map krakenMap = restTemplate.getForObject("https://api.kraken.com/0/public/Ticker?pair=" + market, Map.class);
        KrakenDTO krakenDTO = new KrakenDTO();
        switch (market) {
            case "BTCEUR":
                krakenDTO = setKrakenRest(krakenMap, BTC_SYMBOL, "XXBTZEUR");
                break;
            case "ETHEUR":
                krakenDTO = setKrakenRest(krakenMap, ETH_SYMBOL, "XETHZEUR");
                break;
            case "XRPEUR":
                krakenDTO = setKrakenRest(krakenMap, XRP_SYMBOL, "XXRPZEUR");
                break;
            case "DASHEUR":
                krakenDTO = setKrakenRest(krakenMap, DASH_SYMBOL, "DASHEUR");
                break;
            case "LTCEUR":
                krakenDTO = setKrakenRest(krakenMap, LTC_SYMBOL, "XLTCZEUR");
                break;
            case "ETCEUR":
                krakenDTO = setKrakenRest(krakenMap, ETC_SYMBOL, "XETCZEUR");
                break;
            case "BCHEUR":
                krakenDTO = setKrakenRest(krakenMap, BCH_SYMBOL, "BCHEUR");
                break;
            case "ZECEUR":
                krakenDTO = setKrakenRest(krakenMap, ZEC_SYMBOL, "XZECZEUR");
                break;
            case "XMREUR":
                krakenDTO = setKrakenRest(krakenMap, XMR_SYMBOL, "XXMRZEUR");
                break;
        }
        return krakenDTO ;
    }
/*
    private BitfinexDTO getBitfinexRest(String market) {

        RestTemplate restTemplate = new RestTemplate();
        BitfinexDTO bitfinexDTO = restTemplate.getForObject("https://api.bitfinex.com/v1/pubticker/" + market, BitfinexDTO.class);
        switch (market) {
            case "btcusd":
                bitfinexDTO.setSymbol(BTC_SYMBOL);
                break;
            case "ethusd":
                bitfinexDTO.setSymbol(ETH_SYMBOL);
                break;
            case "xrpusd":
                bitfinexDTO.setSymbol(XRP_SYMBOL);
                break;
            case "dshusd":
                bitfinexDTO.setSymbol(DASH_SYMBOL);
                break;
            case "ltcusd":
                bitfinexDTO.setSymbol(LTC_SYMBOL);
                break;
            case "etcusd":
                bitfinexDTO.setSymbol(ETC_SYMBOL);
                break;
            case "bchusd":
                bitfinexDTO.setSymbol(BCH_SYMBOL);
                break;
            case "zecusd":
                bitfinexDTO.setSymbol(ZEC_SYMBOL);
                break;
            case "xmrusd":
                bitfinexDTO.setSymbol(XMR_SYMBOL);
                break;
        }

        return bitfinexDTO ;
    }
 */


    private KrakenDTO setKrakenRest(Map krakenMap, String symbol, String krakenSymbol) {
        KrakenDTO krakenDTO = new KrakenDTO();
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/plain;charset=utf-8"));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> bitflyerRes = restTemplate
            .exchange("https://api.bitflyer.jp/v1/ticker", HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        BitflyerDTO bitflyerDTO = new BitflyerDTO();
        try {
            bitflyerDTO = mapper.readValue(bitflyerRes.getBody(), BitflyerDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        RestTemplate restTemplate = new RestTemplate();
        BitflyerDTO bitflyerDTO = restTemplate
            .getForObject("https://api.bitflyer.jp/v1/ticker", BitflyerDTO.class);*/
        bitflyerDTO.setSymbol(BTC_SYMBOL);
        return bitflyerDTO;
    }


    private List<BithumbDataDTO> getBithumbRest() {
        RestTemplate restTemplate = new RestTemplate();
        Map bithumbsMap = restTemplate
            .getForObject("https://api.bithumb.com/public/ticker/all", Map.class);
        Map<Object, Object> bithumbMap = (Map<Object, Object>) bithumbsMap.get("data");
        bithumbQuote(bithumbMap);
        List<BithumbDataDTO> bithumbDataDTOS = new ArrayList<>();
        BithumbDataDTO bithumbDTOBtc = modelMapper.map(bithumbMap.get("BTC"), BithumbDataDTO.class);
        bithumbDTOBtc.setSymbol(BTC_SYMBOL);
        BithumbDataDTO bithumbDTOEth = modelMapper.map(bithumbMap.get("ETH"), BithumbDataDTO.class);
        bithumbDTOEth.setSymbol(ETH_SYMBOL);
        BithumbDataDTO bithumbDTODash = modelMapper.map(bithumbMap.get("DASH"), BithumbDataDTO.class);
        bithumbDTODash.setSymbol(DASH_SYMBOL);
        BithumbDataDTO bithumbDTOLtc = modelMapper.map(bithumbMap.get("LTC"), BithumbDataDTO.class);
        bithumbDTOLtc.setSymbol(LTC_SYMBOL);
        BithumbDataDTO bithumbDTOEtc = modelMapper.map(bithumbMap.get("ETC"), BithumbDataDTO.class);
        bithumbDTOEtc.setSymbol(ETC_SYMBOL);
        BithumbDataDTO bithumbDTOXrp = modelMapper.map(bithumbMap.get("XRP"), BithumbDataDTO.class);
        bithumbDTOXrp.setSymbol(XRP_SYMBOL);
        BithumbDataDTO bithumbDTOBch = modelMapper.map(bithumbMap.get("BCH"), BithumbDataDTO.class);
        bithumbDTOBch.setSymbol(BCH_SYMBOL);
        BithumbDataDTO bithumbDTOXmr = modelMapper.map(bithumbMap.get("XMR"), BithumbDataDTO.class);
        bithumbDTOXmr.setSymbol(XMR_SYMBOL);
        BithumbDataDTO bithumbDTOZec = modelMapper.map(bithumbMap.get("ZEC"), BithumbDataDTO.class);
        bithumbDTOZec.setSymbol(ZEC_SYMBOL);
        BithumbDataDTO bithumbDTOQtum = modelMapper.map(bithumbMap.get("QTUM"), BithumbDataDTO.class);
        bithumbDTOQtum.setSymbol(QTUM_SYMBOL);

        bithumbDataDTOS.add(bithumbDTOBtc);
        bithumbDataDTOS.add(bithumbDTOEth);
        bithumbDataDTOS.add(bithumbDTOXrp);
        bithumbDataDTOS.add(bithumbDTODash);
        bithumbDataDTOS.add(bithumbDTOLtc);
        bithumbDataDTOS.add(bithumbDTOEtc);

        bithumbDataDTOS.add(bithumbDTOBch);
        bithumbDataDTOS.add(bithumbDTOZec);
        bithumbDataDTOS.add(bithumbDTOXmr);

        bithumbDataDTOS.add(bithumbDTOQtum);



        return bithumbDataDTOS;
    }

    private void bithumbQuote(Map<Object, Object> bithumbMap) {
        List<Quote> quotes = new ArrayList<>();
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("BTC"),1));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("ETH"),2));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("XRP"),3));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("DASH"),4));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("LTC"),5));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("ETC"),6));

        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("BCH"),7));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("ZEC"),8));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("XMR"),9));

        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("QTUM"),10));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("BTG"),11));
        quotes.add(bithumbToQuote((Map<String, String>) bithumbMap.get("EOS"),12));
        quoteRepository.save(quotes);
    }

    private Quote bithumbToQuote(Map<String,String> map,long marketCoinId) {
        MarketCoin marketCoin = marketCoinRepository.findOne(marketCoinId);
        return new Quote().lastPrice(new BigDecimal(map.get("closing_price")))
                                 .volume(new BigDecimal(map.get("volume_1day")))
                                 .lowPrice(new BigDecimal(map.get("min_price")))
                                 .highPrice(new BigDecimal(map.get("max_price")))
                                 .avgPrice(new BigDecimal(map.get("average_price")))
                                 .buyPrice(new BigDecimal(map.get("buy_price")))
                                 .sellPrice(new BigDecimal(map.get("sell_price")))
                                 .openingPrice(new BigDecimal(map.get("opening_price")))
                                 .closingPrice(new BigDecimal(map.get("closing_price")))
                                // .quoteTime(Instant.parse(map.get("date")))
                                 .marketCoin(marketCoin);


    }
}
