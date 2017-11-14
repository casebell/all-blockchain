
package io.iansoft.blockchain.service.impl;
import io.iansoft.blockchain.domain.FixerExchangeRates;
import io.iansoft.blockchain.repository.FixerExchangeRatesRepository;
import io.iansoft.blockchain.service.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService{

    private static final String fixerURL ="https://api.fixer.io/latest?base=";
    private FixerExchangeRatesRepository fixerExchangeRatesRepository;
    private ModelMapper modelMapper;

    public ExchangeRateServiceImpl(FixerExchangeRatesRepository fixerExchangeRatesRepository,
                                   ModelMapper modelMapper) {
        this.fixerExchangeRatesRepository = fixerExchangeRatesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
   // @Scheduled(cron = "0 1 * * * ?")
    @Scheduled(cron = "*/10 * * * * *")
    public void getExchangeRate(){


        exchangeRateRest("USD");
        exchangeRateRest("EUR");
        exchangeRateRest("CNY");
        exchangeRateRest("JPY");
        //fixerExchangeRatesRepository.save(exchangeRates);
    }

    @Override
    public List<FixerExchangeRates> getFixers() {
        List<FixerExchangeRates> fixerExchangeRates = new ArrayList<>();
        fixerExchangeRates.add(getFixer("USD"));
        fixerExchangeRates.add(getFixer("EUR"));
        fixerExchangeRates.add(getFixer("CNY"));
        fixerExchangeRates.add(getFixer("JPY"));
        return fixerExchangeRates;
    }

    private FixerExchangeRates getFixer(String currency) {
        return fixerExchangeRatesRepository.findFirst1ByBaseOrderByCreatedDateDesc(currency);
    }

    private void exchangeRateRest(String currency){
        RestTemplate restTemplate = new RestTemplate();
        Map fixerMap = restTemplate.getForObject(fixerURL + currency, Map.class);
        FixerExchangeRates fixerExchangeRates = modelMapper.map(fixerMap.get("rates"),FixerExchangeRates.class);
        fixerExchangeRates.setBase(currency);
        fixerExchangeRatesRepository.save(fixerExchangeRates);
    }
}
