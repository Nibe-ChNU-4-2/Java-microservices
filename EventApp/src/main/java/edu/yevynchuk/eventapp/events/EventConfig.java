package edu.yevynchuk.eventapp.events;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class EventConfig {

    private final EventRepository eventRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
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

            Event event3 = Event.builder()
                    .title("DevOps Bootcamp")
                    .description("Курс з DevOps для початківців")
                    .startTime(LocalDateTime.of(2025, 3, 15, 9, 0))
                    .endTime(LocalDateTime.of(2025, 3, 15, 17, 0))
                    .build();

            Event event4 = Event.builder()
                    .title("Hackathon")
                    .description("24-годинний хакатон для розробників")
                    .startTime(LocalDateTime.of(2025, 3, 20, 12, 0))
                    .endTime(LocalDateTime.of(2025, 3, 21, 12, 0))
                    .build();

            eventRepository.saveAll(List.of(event1, event2, event3, event4));
        };
    }
}