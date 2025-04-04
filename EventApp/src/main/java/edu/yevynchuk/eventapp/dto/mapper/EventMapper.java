package edu.yevynchuk.eventapp.mapper;

import edu.yevynchuk.eventapp.dto.EventDTO;
import edu.yevynchuk.eventapp.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(EventDTO dto) {
        if (dto == null) return null;

        return Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public EventDTO toDTO(Event entity) {
        if (entity == null) return null;

        EventDTO dto = new EventDTO();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
