package edu.yevynchuk.eventapp.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getEvent() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event addEvent(@RequestBody Event event) throws Exception {
        return eventService.addEvent(event);
    }

    @PutMapping
    public Event updateEvent(@RequestBody Event event) throws Exception {
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}