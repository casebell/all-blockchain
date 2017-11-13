package io.iansoft.blockchain.service;

import org.springframework.scheduling.annotation.Scheduled;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;

public interface ExchangeRateService {

    void getExchangeRate() throws MalformedURLException, JAXBException;
}
