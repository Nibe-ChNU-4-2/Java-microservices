package edu.yevynchuk.eventapp.controller;

import edu.yevynchuk.eventapp.dto.EventDTO;
import edu.yevynchuk.eventapp.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public EventDTO addEvent(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.addEvent(eventDTO);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody @Valid EventDTO eventDTO) throws Exception {
        return eventService.updateEvent(id, eventDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
