package com.careydevelopment.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.careydevelopment.crm"})
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class,args);
    }
}
