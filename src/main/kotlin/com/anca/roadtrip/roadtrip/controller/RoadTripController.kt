package com.anca.roadtrip.roadtrip.controller

import com.anca.roadtrip.roadtrip.domain.Checkpoint
import com.anca.roadtrip.roadtrip.domain.CheckpointRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime.now

@RestController
@RequestMapping("/api/v1/checkpoint")
class RoadTripController(val repository: CheckpointRepository) {

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    fun addNewCheckpoint(@RequestBody checkPoint: Checkpoint) = repository.insert(checkPoint)

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): Mono<Checkpoint> = repository.findById(id)

    @GetMapping
    fun findFirst() = repository.findFirstByOrderByDateAsc()

    @GetMapping("/last")
    fun findLast() = repository.findFirstByOrderByDateDesc()

    @GetMapping("/next")
    fun next() = repository.findNextCheckpoints(now())

    @GetMapping("/all")
    fun all() = repository.findAll()
}
