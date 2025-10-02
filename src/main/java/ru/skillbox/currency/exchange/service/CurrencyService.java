package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.parsing.XmlDataFetcher;
import ru.skillbox.currency.exchange.parsing.model.ValCurs;
import ru.skillbox.currency.exchange.parsing.model.Valute;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;
    private final XmlDataFetcher xmlDataFetcher;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public List<Currency> findAll() {
        return repository.findAll();
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return  mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public Currency updateByCharCode(Valute valute) {
        Currency updatedCurrency = repository.findByIsoCharCode(valute.getCharCode());
        updatedCurrency.setName(valute.getName());
        updatedCurrency.setNominal((long) valute.getNominal());
        updatedCurrency.setValue(Double.parseDouble(valute.getValue().replace(',', '.')));
        updatedCurrency.setIsoNumCode(Long.valueOf(valute.getNumCode()));

        return repository.save(updatedCurrency);
    }

    public List<Valute> getCurrencyList(String xmlString) throws JAXBException {

           JAXBContext context = JAXBContext.newInstance(ValCurs.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           StringReader reader = new StringReader(xmlString);
           ValCurs listOfCourses = (ValCurs) unmarshaller.unmarshal(reader);
           log.info("courses were taken from site and saved into special list");
           return listOfCourses.getValuteList();
        }

    @Scheduled(fixedRateString = "${api.task.frequency}")
    public void updateCurrenciesDates() throws Exception{
        List<Valute> valuteList = getCurrencyList(xmlDataFetcher.fetchXmlString());
        for(Valute valute : valuteList) {
            if(repository.existsByIsoCharCode(valute.getCharCode())) {
                updateByCharCode(valute);
                log.info("existed currency " + valute.getName() + " was updated");
            } else {
                repository.save(mapper.convertFromXmlToEntity(valute));
                log.info("new currency " + valute.getName() + " was saved into database");
            }
        }
    }




}
