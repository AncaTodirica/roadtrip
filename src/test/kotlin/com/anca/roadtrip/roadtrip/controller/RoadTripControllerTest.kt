package com.anca.roadtrip.roadtrip.controller

import com.anca.roadtrip.roadtrip.domain.Checkpoint
import com.anca.roadtrip.roadtrip.domain.CheckpointRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(RoadTripController::class)
@AutoConfigureMockMvc()
class RoadTripControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var repository: CheckpointRepository

    @Test
    fun `adding new checkpoints is successful`() {
        mockMvc
            .perform(
                put("/api/v1/add")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(Checkpoint("id1", "City1", LocalDateTime.now())))
            )
            .andExpect(status().isAccepted)
    }

    @Test
    fun `requesting first checkpoint is successful`() {
        mockMvc
            .perform(
                get("/api/v1/first")
            )
            .andExpect(status().is2xxSuccessful)
        verify(repository).findFirstByOrderByDateAsc()
    }

    @Test
    fun `requesting last checkpoint is successful`() {
        mockMvc
            .perform(
                get("/api/v1/last")
            )
            .andExpect(status().is2xxSuccessful)
        verify(repository).findFirstByOrderByDateDesc()
    }

    @Test
    fun `requesting next checkpoint is successful`() {
        mockMvc
            .perform(
                get("/api/v1/next")
            )
            .andExpect(status().is2xxSuccessful)
        verify(repository).findNextCheckpoints(any())
    }

    @Test
    fun `requesting checkpoint by name is successful`() {
        mockMvc
            .perform(
                get("/api/v1/find")
                    .param("name", "City1")
            )
            .andExpect(status().is2xxSuccessful)
        verify(repository).findByName("City1")
    }
}
