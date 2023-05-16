package com.nb.futbol24checker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class RunnerService implements ApplicationRunner {

    @Autowired
    private WebService webService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("date.to.check") == null) {
            throw new RuntimeException("Date not present!");
        }
        webService.makeGetRestCall(args.getOptionValues("date.to.check").get(0));
        System.out.println("We are going to check date: " + args.getOptionValues("date.to.check"));

    }

}
