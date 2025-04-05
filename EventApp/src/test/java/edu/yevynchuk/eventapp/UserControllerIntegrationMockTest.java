package edu.yevynchuk.eventapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.yevynchuk.eventapp.dto.UserCreateDTO;
import edu.yevynchuk.eventapp.model.User;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class UserControllerIntegrationMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddUserValidationFailed() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("", "", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        User user = User.builder()
                .email("test@example.com")
                .username("Test User")
                .password("somePassword")
                .build();

        User savedUser = userRepository.save(user);

        savedUser.setUsername("Updated Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is("Updated Name")));
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        User user = User.builder()
                .email("delete@example.com")
                .username("To Delete")
                .password("somePassword")
                .build();

        User savedUser = userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", savedUser.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", savedUser.getId()))
                .andExpect(status().isNotFound());
    }
}
