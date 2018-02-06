package io.iansoft.blockchain.service;

import io.iansoft.blockchain.domain.FixerExchangeRates;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.util.List;

public interface ExchangeRateService {

    void getExchangeRate() throws MalformedURLException, JAXBException;

    List<FixerExchangeRates> getFixers();
}
