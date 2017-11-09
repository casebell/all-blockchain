package io.iansoft.blockchain.service;


import io.iansoft.blockchain.service.dto.*;

import java.util.List;

public interface CoinApiService {

    List<BithumbDataDTO> getBithumb();

    void getCoinApi();

    List<KorbitDTO> getKorbit();

    List<BitflyerDTO> getBitflyer();

    List<BittrexDTO> getBittrex();
}
