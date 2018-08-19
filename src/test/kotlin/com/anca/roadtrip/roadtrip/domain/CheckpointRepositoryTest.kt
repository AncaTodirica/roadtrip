package com.anca.roadtrip.roadtrip.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.test
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class CheckpointRepositoryTest {

    @Autowired
    lateinit var repository: CheckpointRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll().subscribe()
    }

    @Test
    fun `retrieves first checkpoint`() {
        repository.insert(
            listOf(
                checkpoint("id1", "City1", LocalDateTime.now().minusDays(4)),
                checkpoint("id2", "City2", LocalDateTime.now())
            )
        )

        repository.findFirstByOrderByDateAsc()
            .test()
            .expectNextMatches {
                assertThat(it)
                    .isEqualToIgnoringGivenFields(checkpoint("id1", "City1", LocalDateTime.now()), "date")
                true
            }
    }


    @Test
    fun `retrieves last checkpoint`() {
        repository.insert(
            listOf(
                checkpoint("id1", "City1", LocalDateTime.now().minusDays(4)),
                checkpoint("id2", "City2", LocalDateTime.now())
            )
        )

        repository.findFirstByOrderByDateDesc()
            .test()
            .expectNextMatches {
                assertThat(it)
                    .isEqualToIgnoringGivenFields(checkpoint("id2", "City2", LocalDateTime.now()), "date")
                true
            }
    }

    @Test
    fun `retrieves all checkpoints`() {
        repository.insert(
            listOf(
                checkpoint("id1", "City1", LocalDateTime.now().minusDays(4)),
                checkpoint("id2", "City2", LocalDateTime.now())
            )
        )

        repository.findAll()
            .test()
            .expectNextMatches {
                assertThat(it)
                    .isEqualToIgnoringGivenFields(checkpoint("id1", "City1", LocalDateTime.now()), "date")
                    .isEqualToIgnoringGivenFields(checkpoint("id2", "City2", LocalDateTime.now()), "date")
                true
            }
    }


    @Test
    fun `finds checkpoint details by name`() {
        repository.insert(
            listOf(
                checkpoint("id1", "City1", LocalDateTime.now().minusDays(4)),
                checkpoint("id2", "City2", LocalDateTime.now())
            )
        )

        repository.findByName("City2")
            .test()
            .expectNextMatches {
                assertThat(it)
                    .isEqualToIgnoringGivenFields(checkpoint("id2", "City2", LocalDateTime.now()), "date")
                true
            }
    }


    @Test
    fun `finds next checkpoint`() {
        repository.insert(
            listOf(
                checkpoint("id1", "NextCity", LocalDateTime.now().plusDays(4)),
                checkpoint("id2", "LastCity", LocalDateTime.now().minusDays(1))
            )
        )

        repository.findNextCheckpoints(LocalDateTime.now())
            .test()
            .expectNextMatches {
                assertThat(it)
                    .isEqualToIgnoringGivenFields(checkpoint("id1", "NextCity", LocalDateTime.now()), "date")
                true
            }
    }

    fun checkpoint(id: String, name: String, date: LocalDateTime) =
        Checkpoint(
            id = id,
            date = date,
            name = name
        )
}
