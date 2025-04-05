package edu.yevynchuk.eventapp.service;

import edu.yevynchuk.eventapp.dto.EventUserDTO;
import edu.yevynchuk.eventapp.mapper.EventUserMapper;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.repository.EventRepository;
import edu.yevynchuk.eventapp.repository.UserRepository;
import edu.yevynchuk.eventapp.repository.EventUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventUserService {

    private final EventUserRepository eventUserRepository;
    private final EventUserMapper eventUserMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public EventUserDTO addEventUser(EventUserDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        EventUser eventUser = eventUserMapper.toEntity(dto, user, event);
        return eventUserMapper.toDto(eventUserRepository.save(eventUser));
    }

    public void deleteEventUser(Long id) {
        eventUserRepository.deleteById(id);
    }

    public List<EventUserDTO> getAllEventUsers() {
        return eventUserRepository.findAll()
                .stream()
                .map(eventUserMapper::toDto)
                .collect(Collectors.toList());
    }

    public EventUserDTO getEventUserById(Long id) {
        EventUser eventUser = eventUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EventUser not found with id " + id));
        return eventUserMapper.toDto(eventUser);
    }

    public EventUserDTO updateEventUser(Long id, EventUserDTO dto) {
        EventUser existing = eventUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EventUser not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        existing.setUser(user);
        existing.setEvent(event);
        existing.setRole(dto.getRole());

        return eventUserMapper.toDto(eventUserRepository.save(existing));
    }





}
