// EventUserMapper.java
package edu.yevynchuk.eventapp.mapper;

import edu.yevynchuk.eventapp.dto.EventUserDTO;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventUserMapper {

    public EventUser toEntity(EventUserDTO dto, User user, Event event) {
        return EventUser.builder()
                .user(user)
                .event(event)
                .role(dto.getRole())
                .build();
    }

    public EventUserDTO toDto(EventUser eventUser) {
        return EventUserDTO.builder()
                .userId(eventUser.getUser().getId())
                .eventId(eventUser.getEvent().getId())
                .role(eventUser.getRole())
                .build();
    }
}
