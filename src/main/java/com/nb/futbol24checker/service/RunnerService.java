package com.nb.futbol24checker.service;

import com.nb.futbol24checker.model.FutbolResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class RunnerService implements ApplicationRunner {

    @Autowired
    private WebService webService;
    @Autowired
    private FileWriterService fileWriterService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String dateAsStringWithNewFormat = validateArguments(args);
        System.out.println("====================================================================================");
        System.out.println("We are going to check date: " + args.getOptionValues("date.to.check"));
        System.out.println("====================================================================================");
        FutbolResponse response = webService.makeGetRestCall(dateAsStringWithNewFormat);
        fileWriterService.filterMatchesAndWriteToCsv(dateAsStringWithNewFormat, response);
    }

    private String validateArguments(ApplicationArguments args) {
        if(args.getOptionValues("date.to.check") == null) {
            throw new RuntimeException("Please pass --date.to.check argument with a date format of YYYY-DD-MM");
        }
        String dateAsString = args.getOptionValues("date.to.check").get(0);
        if(dateAsString == null || dateAsString.isBlank()) {
            throw new RuntimeException("Date not present!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date;
        try{
            date = LocalDate.parse(dateAsString, formatter);
        } catch (DateTimeParseException e) {
            log.error("Date format must be YYYY-MM-DD");
            throw new RuntimeException(String.format("Invalid date format used in %s", dateAsString), e);
        }
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

}
