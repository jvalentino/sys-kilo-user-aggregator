package com.github.jvalentino.kilo


import org.springframework.boot.SpringApplication
import spock.lang.Specification

class KiloUserAggregatorAppTest extends Specification {

    def setup() {
        GroovyMock(SpringApplication, global:true)
    }

    def "test main"() {
        when:
        KiloUserAggregatorApp.main(null)

        then:
        1 * SpringApplication.run(KiloUserAggregatorApp, null)
    }

}
