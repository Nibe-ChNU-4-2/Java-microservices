package edu.yevynchuk.eventapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.yevynchuk.eventapp.dto.EventDTO;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class EventControllerIntegrationMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddEventValidationFailed() throws Exception {
        EventDTO eventDTO = EventDTO.builder()
                .title("")
                .description("")
                .startTime(null)
                .endTime(null)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testAddEventSuccess() throws Exception {
        EventDTO eventDTO = EventDTO.builder()
                .title("Test Event")
                .description("This is a test event.")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("Test Event")));
    }

    @Test
    void testUpdateEventSuccess() throws Exception {
        Event event = Event.builder()
                .title("Old Name")
                .description("desc")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .build();

        Event savedEvent = eventRepository.save(event);

        EventDTO updateDTO = EventDTO.builder()
                .title("Updated Name")
                .description("Updated Desc")
                .startTime(LocalDateTime.now().plusDays(3))
                .endTime(LocalDateTime.now().plusDays(4))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/events/{id}", savedEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("Updated Name")));
    }

    @Test
    void testDeleteEventSuccess() throws Exception {
        Event event = Event.builder()
                .title("To Delete")
                .description("desc")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .build();

        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{id}", savedEvent.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/events/{id}", savedEvent.getId()))
                .andExpect(status().isNotFound());
    }
}
