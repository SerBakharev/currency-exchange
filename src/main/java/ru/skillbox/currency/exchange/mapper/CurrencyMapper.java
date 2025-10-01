package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.ShortDto;
import ru.skillbox.currency.exchange.dto.ShortDtoListResponse;
import ru.skillbox.currency.exchange.entity.Currency;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    ShortDto convertToShort(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    default ShortDtoListResponse currencyListToShortDtoListResponse(List<Currency> listOfCurrencies) {
        List<ShortDto> response = new ArrayList<>();
        for (Currency currency : listOfCurrencies) {
            ShortDto shortDto = convertToShort(currency);
            response.add(shortDto);
        }
        ShortDtoListResponse shortDtoListResponse = new ShortDtoListResponse();
        shortDtoListResponse.setCurrencies(response);
        return shortDtoListResponse;
    }
}
