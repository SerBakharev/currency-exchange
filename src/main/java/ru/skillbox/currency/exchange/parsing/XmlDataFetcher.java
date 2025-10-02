package ru.skillbox.currency.exchange.parsing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class XmlDataFetcher {
    @Value("${api.xml-data-url}")
    private String xmlDataUrl;


    private final RestTemplate restTemplate;

    public XmlDataFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchXmlString() {
        return restTemplate.getForObject(xmlDataUrl, String.class);
    }

}
