package edu.yevynchuk.eventapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.yevynchuk.eventapp.dto.EventUserDTO;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.model.Role;
import edu.yevynchuk.eventapp.repository.EventRepository;
import edu.yevynchuk.eventapp.repository.EventUserRepository;
import edu.yevynchuk.eventapp.repository.UserRepository;
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
public class EventUserControllerIntegrationMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventUserRepository eventUserRepository;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    void testGetAllEventUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eventUser"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddEventUserValidationFailed() throws Exception {
        EventUserDTO dto = new EventUserDTO(); // без заповнення полів

        mockMvc.perform(MockMvcRequestBuilders.post("/eventUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void testAddEventUserSuccess() throws Exception {
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("securePassword123")
                .build();
        user = userRepository.save(user);

        Event event = Event.builder()
                .title("Test Event")
                .description("desc")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .build();
        event = eventRepository.save(event);

        EventUserDTO dto = EventUserDTO.builder()
                .userId(user.getId())
                .eventId(event.getId())
                .role(Role.ROLE_PARTICIPANT)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/eventUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.eventId").value(event.getId()))
                .andExpect(jsonPath("$.role").value("ROLE_PARTICIPANT"));
    }

    @Test
    void testDeleteEventUserSuccess() throws Exception {
        User user = User.builder()
                .username("Test User")
                .email("test@example.com")
                .password("securePassword123")
                .build();
        user = userRepository.save(user);

        Event event = Event.builder()
                .title("Test Event")
                .description("desc")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .build();
        event = eventRepository.save(event);

        EventUser eventUser = EventUser.builder()
                .user(user)
                .event(event)
                .role(Role.ROLE_ADMIN)
                .build();
        eventUserRepository.save(eventUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/eventUser/{id}", eventUser.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/eventUser/{id}", eventUser.getId()))
                .andExpect(status().isNotFound());
    }
}
