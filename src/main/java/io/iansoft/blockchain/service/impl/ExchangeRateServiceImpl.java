
package io.iansoft.blockchain.service.impl;
import io.iansoft.blockchain.domain.EcbExchangeRates;
import io.iansoft.blockchain.repository.EcbExchangeRatesRepository;
import io.iansoft.blockchain.service.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService{

    private String apiURL ="http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private EcbExchangeRatesRepository ecbExchangeRatesRepository;

    public ExchangeRateServiceImpl(EcbExchangeRatesRepository ecbExchangeRatesRepository) {
        this.ecbExchangeRatesRepository = ecbExchangeRatesRepository;
    }

    @Override
   // @Scheduled(cron = "0 1 * * * ?")
    @Scheduled(cron = "*/10 * * * * *")
    public void getExchangeRate() throws MalformedURLException, JAXBException {

        URL url = new URL(apiURL);
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;
//        Document doc;
//        try {
//            builder = factory.newDocumentBuilder();
//            doc =  builder.parse(url.openStream());
//        } catch (ParserConfigurationException | SAXException | IOException ex) {
//            ex.printStackTrace();
//        }

        JAXBContext jaxbContext = JAXBContext.newInstance(EcbExchangeRates.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EcbExchangeRates exchangeRates = (EcbExchangeRates) jaxbUnmarshaller.unmarshal(url);
        ecbExchangeRatesRepository.save(exchangeRates);
    }
}
