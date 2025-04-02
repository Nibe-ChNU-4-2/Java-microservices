package edu.yevynchuk.eventapp.config;

import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.Role;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.repository.EventRepository;
import edu.yevynchuk.eventapp.repository.UserRepository;
import edu.yevynchuk.eventapp.repository.EventUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataConfig {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventUserRepository eventUserRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            User user1 = User.builder()
                    .username("user1")
                    .password("password1")
                    .email("user1@example.com")
                    .build();

            User user2 = User.builder()
                    .username("user2")
                    .password("password2")
                    .email("user2@example.com")
                    .build();

            User user3 = User.builder()
                    .username("user3")
                    .password("password3")
                    .email("user3@example.com")
                    .build();

            userRepository.saveAll(List.of(user1, user2, user3));

            Event event1 = Event.builder()
                    .title("Spring Boot Workshop")
                    .description("Навчальний воркшоп по Spring Boot")
                    .startTime(LocalDateTime.of(2025, 3, 10, 10, 0))
                    .endTime(LocalDateTime.of(2025, 3, 10, 14, 0))
                    .build();

            Event event2 = Event.builder()
                    .title("Java Meetup")
                    .description("Зустріч розробників Java")
                    .startTime(LocalDateTime.of(2025, 3, 12, 18, 0))
                    .endTime(LocalDateTime.of(2025, 3, 12, 21, 0))
                    .build();

            eventRepository.saveAll(List.of(event1, event2));

            EventUser eventUser1 = EventUser.builder()
                    .event(event1)
                    .user(user1)
                    .role(Role.ROLE_OWNER)
                    .build();

            EventUser eventUser2 = EventUser.builder()
                    .event(event1)
                    .user(user2)
                    .role(Role.ROLE_PARTICIPANT)
                    .build();

            EventUser eventUser3 = EventUser.builder()
                    .event(event2)
                    .user(user3)
                    .role(Role.ROLE_ADMIN)
                    .build();

            eventUserRepository.saveAll(List.of(eventUser1, eventUser2, eventUser3));
        };
    }
}
