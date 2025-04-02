package edu.yevynchuk.eventapp.events;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event")
@Getter
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
