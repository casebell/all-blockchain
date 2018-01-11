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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public static final String UPBIT_URL = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.";
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
                              MarketCoinRepository marketCoinRepository,
                              QuoteRepository quoteRepository, ModelMapper modelMapper) {
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

        getBithumbRest(); //.forEach(this::saveBithumb);
        List<KorbitDTO> korbitDTOS = new ArrayList();
        korbitDTOS.add(getKorbitRestBtc());
        korbitDTOS.add(getKorbitRestEth());
        korbitDTOS.add(getKorbitRestEtc());
        korbitDTOS.add(getKorbitRestXrp());
        korbitDTOS.forEach(this::saveKorbit);

        BitflyerDTO bitflyerDTO = getBitflyerResBtc();
        saveBitflyer(bitflyerDTO);

        getBittrexs().forEach(this::saveBittrex);

      //  getKrakens().forEach(this::saveKraken);

        getUpBit();

    }

    private void getUpBit() {
        List<UpbitCoinDTO> upbitCoinDTOS = new ArrayList<UpbitCoinDTO>(){{
           add(new UpbitCoinDTO("KRW-BTC",1101)); add(new UpbitCoinDTO("KRW-ETH",1102));
           add(new UpbitCoinDTO("KRW-XRP",1103)); add(new UpbitCoinDTO("KRW-DASH",1104));
           add(new UpbitCoinDTO("KRW-LTC",1105)); add(new UpbitCoinDTO("KRW-ETC",1106));
           add(new UpbitCoinDTO("KRW-BCC",1107)); add(new UpbitCoinDTO("KRW-ZEC",1108));
           add(new UpbitCoinDTO("KRW-XMR",1109)); add(new UpbitCoinDTO("KRW-NEO",1110));
           add(new UpbitCoinDTO("KRW-QTUM",1111)); add(new UpbitCoinDTO("KRW-BTG",1112));
           add(new UpbitCoinDTO("KRW-SNT",1113)); add(new UpbitCoinDTO("KRW-ADA",1114));
           add(new UpbitCoinDTO("KRW-TIX",1115)); add(new UpbitCoinDTO("KRW-STEEM",1116));
           add(new UpbitCoinDTO("KRW-MTL",1117)); add(new UpbitCoinDTO("KRW-POWR",1118));
           add(new UpbitCoinDTO("KRW-XEM",1119)); add(new UpbitCoinDTO("KRW-KMD",1120));
           add(new UpbitCoinDTO("KRW-LSK",1121)); add(new UpbitCoinDTO("KRW-MER",1122));
           add(new UpbitCoinDTO("KRW-EMC2",1123)); add(new UpbitCoinDTO("KRW-GRS",1124));
           add(new UpbitCoinDTO("KRW-SBD",1125)); add(new UpbitCoinDTO("KRW-STORJ",1126));
           add(new UpbitCoinDTO("KRW-WAVES",1127));
           add(new UpbitCoinDTO("KRW-OMG",1128)); add(new UpbitCoinDTO("KRW-STRAT",1129));
           add(new UpbitCoinDTO("KRW-REP",1130)); add(new UpbitCoinDTO("KRW-PIVX",1131));
            add(new UpbitCoinDTO("KRW-VTC",1132));add(new UpbitCoinDTO("KRW-ARDR",1133));
        }};
        List<Quote> quotes = new ArrayList<>();
        for (UpbitCoinDTO upbitCoinDTO :upbitCoinDTOS) {
            RestTemplate restTemplate = new RestTemplate();
            ArrayList upBitMap = restTemplate.getForObject(UPBIT_URL +upbitCoinDTO.getName(), ArrayList.class);

            UpbitDTO upbitDTO = modelMapper.map(upBitMap.get(0), UpbitDTO.class);
            Quote quote = marketToQuote(upbitCoinDTO.getMarketCoinId(),String.valueOf(upbitDTO.getTradePrice()),"0",
                String.valueOf(upbitDTO.getLowPrice()),String.valueOf(upbitDTO.getHighPrice()),"0","0","0",String.valueOf(upbitDTO.getHighPrice()));
            quotes.add(quote);
        }

        quoteRepository.save(quotes);
    }

    private Quote marketToQuote(long marketCoinId, String lastPrice, String volume, String lowPrice,
                                String highPrice, String avgPrice, String buyPrice,
                                String sellPrice, String openingPrice) {
        MarketCoin marketCoin = marketCoinRepository.findOne(marketCoinId);
        return new Quote().lastPrice(new BigDecimal(lastPrice))
            .volume(new BigDecimal(volume))
            .lowPrice(new BigDecimal(lowPrice))
            .highPrice(new BigDecimal(highPrice))
            .avgPrice(new BigDecimal(avgPrice))
            .buyPrice(new BigDecimal(buyPrice))
            .sellPrice(new BigDecimal(sellPrice))
            .openingPrice(new BigDecimal(openingPrice))
            .closingPrice(new BigDecimal(lastPrice))
            // .quoteTime(Instant.parse(map.get("date")))
            .marketCoin(marketCoin);
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


    private void getBithumbRest() {
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

        bithumbDataDTOS.forEach(x->
            x.setCreatedat(zonedDateTime)
        );


        bithumbRepository.save(bithumbDataDTOS.stream().map(x->modelMapper.map(x,Bithumb.class)).collect(Collectors.toList()));
    }

    private void bithumbQuote(Map<Object, Object> bithumbMap) {
        List<Quote> quotes = new ArrayList<>();
        quotes.add(marketToQuote(1, ((Map<String, String>) bithumbMap.get("BTC")).get("closing_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("BTC")).get("min_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("max_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("average_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("buy_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("sell_price"), ((Map<String, String>) bithumbMap.get("BTC")).get("opening_price")));
        quotes.add(marketToQuote(2, ((Map<String, String>) bithumbMap.get("ETH")).get("closing_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("ETH")).get("min_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("max_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("average_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("buy_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("sell_price"), ((Map<String, String>) bithumbMap.get("ETH")).get("opening_price")));
        quotes.add(marketToQuote(3, ((Map<String, String>) bithumbMap.get("XRP")).get("closing_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("XRP")).get("min_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("max_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("average_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("buy_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("sell_price"), ((Map<String, String>) bithumbMap.get("XRP")).get("opening_price")));
        quotes.add(marketToQuote(4, ((Map<String, String>) bithumbMap.get("DASH")).get("closing_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("DASH")).get("min_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("max_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("average_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("buy_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("sell_price"), ((Map<String, String>) bithumbMap.get("DASH")).get("opening_price")));
        quotes.add(marketToQuote(5, ((Map<String, String>) bithumbMap.get("LTC")).get("closing_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("LTC")).get("min_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("max_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("average_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("buy_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("sell_price"), ((Map<String, String>) bithumbMap.get("LTC")).get("opening_price")));
        quotes.add(marketToQuote(6, ((Map<String, String>) bithumbMap.get("ETC")).get("closing_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("ETC")).get("min_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("max_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("average_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("buy_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("sell_price"), ((Map<String, String>) bithumbMap.get("ETC")).get("opening_price")));

        quotes.add(marketToQuote(7, ((Map<String, String>) bithumbMap.get("BCH")).get("closing_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("BCH")).get("min_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("max_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("average_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("buy_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("sell_price"), ((Map<String, String>) bithumbMap.get("BCH")).get("opening_price")));
        quotes.add(marketToQuote(8, ((Map<String, String>) bithumbMap.get("ZEC")).get("closing_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("ZEC")).get("min_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("max_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("average_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("buy_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("sell_price"), ((Map<String, String>) bithumbMap.get("ZEC")).get("opening_price")));
        quotes.add(marketToQuote(9, ((Map<String, String>) bithumbMap.get("XMR")).get("closing_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("XMR")).get("min_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("max_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("average_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("buy_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("sell_price"), ((Map<String, String>) bithumbMap.get("XMR")).get("opening_price")));

        quotes.add(marketToQuote(10, ((Map<String, String>) bithumbMap.get("QTUM")).get("closing_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("QTUM")).get("min_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("max_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("average_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("buy_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("sell_price"), ((Map<String, String>) bithumbMap.get("QTUM")).get("opening_price")));
        quotes.add(marketToQuote(11, ((Map<String, String>) bithumbMap.get("BTG")).get("closing_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("BTG")).get("min_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("max_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("average_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("buy_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("sell_price"), ((Map<String, String>) bithumbMap.get("BTG")).get("opening_price")));
        quotes.add(marketToQuote(12, ((Map<String, String>) bithumbMap.get("EOS")).get("closing_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("volume_1day"), ((Map<String, String>) bithumbMap.get("EOS")).get("min_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("max_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("average_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("buy_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("sell_price"), ((Map<String, String>) bithumbMap.get("EOS")).get("opening_price")));
        quoteRepository.save(quotes);
    }




}
