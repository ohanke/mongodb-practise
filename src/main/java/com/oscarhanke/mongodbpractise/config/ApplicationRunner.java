package com.oscarhanke.mongodbpractise.config;

import com.oscarhanke.mongodbpractise.service.StudentQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final StudentQueries studentQueries;

    @Override
    public void run(String... args) throws Exception {

    }
}
