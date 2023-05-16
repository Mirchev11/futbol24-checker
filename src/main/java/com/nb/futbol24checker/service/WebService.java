package com.nb.futbol24checker.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WebService {

    @Autowired
    private RestTemplateBuilder builder;
    private RestTemplate template;

    private final String getUrl = "https://www.futbol24.com/matchDayXml/?Day=";

    public void makeGetRestCall(String date) {
        log.info("============================================================");

        log.info("Making a GET call to: {}", getUrl+date);
        String response = template.getForObject(getUrl+date, String.class);
        log.info("Response received is: ");
        log.info(response);
        log.info("Response end!");
        log.info("============================================================");
    }


    @PostConstruct
    private void generateRestTemplateForPost() {
        template = builder.build();
    }

}
