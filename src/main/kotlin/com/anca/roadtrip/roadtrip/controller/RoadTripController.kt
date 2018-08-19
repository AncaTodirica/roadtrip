package com.anca.roadtrip.roadtrip.controller

import com.anca.roadtrip.roadtrip.domain.Checkpoint
import com.anca.roadtrip.roadtrip.domain.CheckpointRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.LocalDateTime.now

@RestController
@RequestMapping("/api")
class RoadTripController(val repository: CheckpointRepository) {

    @PutMapping("/v1/add")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    fun addNewCheckpoint(@RequestBody checkPoint: Checkpoint) = repository.insert(checkPoint)

    @GetMapping("/v1/find")
    fun find(@RequestParam name: String): Flux<Checkpoint> = repository.findByName(name)

    @GetMapping("/v1/first")
    fun findFirst() = repository.findFirstByOrderByDateAsc()

    @GetMapping("/v1/last")
    fun findLast() = repository.findFirstByOrderByDateDesc()

    @GetMapping("/v1/next")
    fun next() = repository.findNextCheckpoints(now())

    @GetMapping("/v1/all")
    fun all() = repository.findAll()
}
