package com.anca.roadtrip.roadtrip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration


@Configuration
@SpringBootApplication
class RoadtripApplication

fun main(args: Array<String>) {
    runApplication<RoadtripApplication>(*args)
}
