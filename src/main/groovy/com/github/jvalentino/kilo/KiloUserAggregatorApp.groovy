package com.github.jvalentino.kilo

import groovy.transform.CompileDynamic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.EnableKafka

/**
 * Main application
 */
@SpringBootApplication
@CompileDynamic
@EnableKafka
class KiloUserAggregatorApp {

    static void main(String[] args) {
        SpringApplication.run(KiloUserAggregatorApp, args)
    }

}
