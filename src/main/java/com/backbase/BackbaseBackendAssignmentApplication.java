package com.backbase;

import com.backbase.data.DBInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackbaseBackendAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackbaseBackendAssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(DBInit dbInit) {
        return args -> dbInit.init();
    }
}
