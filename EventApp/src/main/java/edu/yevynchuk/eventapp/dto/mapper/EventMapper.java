package edu.yevynchuk.eventapp.dto.mapper;

import edu.yevynchuk.eventapp.dto.EventDTO;
import edu.yevynchuk.eventapp.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
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
        return EventDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
