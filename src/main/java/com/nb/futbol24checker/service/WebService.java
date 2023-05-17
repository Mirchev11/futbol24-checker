package com.nb.futbol24checker.service;

import com.nb.futbol24checker.model.FutbolResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class WebService {

    @Autowired
    private RestTemplateBuilder builder;
    private RestTemplate template;

    private final String getUrl = "https://www.futbol24.com/matchDayXml/?Day=";

    public FutbolResponse makeGetRestCall(String date) {
        System.out.println("====================================================================================");
        System.out.println("Making a GET call to: " + getUrl+date);
        FutbolResponse response = template.getForEntity(getUrl+date, FutbolResponse.class).getBody();
        System.out.println("====================================================================================");
        return response;
    }

    @PostConstruct
    private void generateRestTemplateForPost() {
        template = builder.additionalMessageConverters( new MarshallingHttpMessageConverter()).build();
    }

}
