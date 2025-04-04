package edu.yevynchuk.eventapp.service;

import edu.yevynchuk.eventapp.dto.EventDTO;
import edu.yevynchuk.eventapp.exception.EventNotFoundException;
import edu.yevynchuk.eventapp.mapper.EventMapper;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    public void deleteEvent(long id) {
        eventRepository.deleteEventUsersByEventId(id);
        eventRepository.deleteById(id);
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
        return eventMapper.toDTO(event);
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        return eventMapper.toDTO(eventRepository.save(event));
    }

    public EventDTO updateEvent(Long id, EventDTO dto) throws Exception {
        if (!eventRepository.existsById(id)) {
            throw new Exception("Event not found");
        }
        Event event = eventMapper.toEntity(dto);
        event.setId(id);
        return eventMapper.toDTO(eventRepository.save(event));
    }


}
