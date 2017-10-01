package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.Authority;
import io.iansoft.blockchain.domain.Bitfinex;
import io.iansoft.blockchain.service.dto.BitfinexDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BitfinexMapper {

    public BitfinexDTO bitfinexToBitfinexDTO(Bitfinex bitfinex) {
        return new BitfinexDTO(bitfinex);
    }

    public List<BitfinexDTO> bitfinexsToBitfinexDTOs(List<Bitfinex> bitfinexs) {
        return bitfinexs.stream()
            .filter(Objects::nonNull)
            .map(this::bitfinexToBitfinexDTO)
            .collect(Collectors.toList());
    }

    public Bitfinex bitfinexDTOToBitfinex(BitfinexDTO bitfinexDTO) {
        if (bitfinexDTO == null) {
            return null;
        } else {
            Bitfinex bitfinex = new Bitfinex();
            bitfinex.setId(bitfinexDTO.getId());
            bitfinex.setMid(bitfinexDTO.getMid());
            bitfinex.setBid(bitfinexDTO.getBid());
            bitfinex.setAsk(bitfinexDTO.getAsk());
            bitfinex.setLast_price(bitfinexDTO.getLast_price());
            bitfinex.setLow(bitfinexDTO.getLow());
            bitfinex.setCreatedat(bitfinexDTO.getCreatedat());

            return bitfinex;
        }
    }

    public List<Bitfinex> bitfinexDTOsToBitfinexs(List<BitfinexDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::bitfinexDTOToBitfinex)
            .collect(Collectors.toList());
    }

    public Bitfinex bitfinexFromId(Long id) {
        if (id == null) {
            return null;
        }
        Bitfinex bitfinex = new Bitfinex();
        bitfinex.setId(id);
        return bitfinex;
    }

    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}
