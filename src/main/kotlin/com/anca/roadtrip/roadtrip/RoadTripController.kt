package com.anca.roadtrip.roadtrip

import com.anca.roadtrip.roadtrip.domain.Checkpoint
import com.anca.roadtrip.roadtrip.domain.CheckpointRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.LocalDateTime.now

@RestController
class RoadTripController(val repository: CheckpointRepository) {

    @PutMapping("/add")
    fun addNewCheckpoint(@RequestBody checkPoint: Checkpoint) = repository.insert(checkPoint)

    @GetMapping("/find")
    fun find(@RequestParam name: String): Flux<Checkpoint> = repository.findByName(name)

    @GetMapping("/first")
    fun findFirst() = repository.findFirstByOrderByDateAsc()

    @GetMapping("/last")
    fun findLast() = repository.findFirstByOrderByDateDesc()

    @GetMapping("/next")
    fun next() = repository.findNextCheckpoints(now())
}
