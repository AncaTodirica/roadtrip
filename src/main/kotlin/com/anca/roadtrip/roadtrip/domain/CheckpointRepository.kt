package com.anca.roadtrip.roadtrip.domain

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
interface CheckpointRepository : ReactiveMongoRepository<Checkpoint, String> {

    fun findByName(name: String): Flux<Checkpoint>

    fun findFirstByOrderByDateAsc(): Mono<Checkpoint>

    fun findFirstByOrderByDateDesc(): Mono<Checkpoint>

    @Query("{ 'date' : {\$gt: ?0 } }")
    fun findNextCheckpoints(now: LocalDateTime): Flux<Checkpoint>
}
