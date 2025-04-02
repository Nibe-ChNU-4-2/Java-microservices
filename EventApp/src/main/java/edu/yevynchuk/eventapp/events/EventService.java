package edu.yevynchuk.eventapp.events;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null);
    }

    public Event addEvent(Event event) throws Exception {
        if (event.getTitle() == null)
            throw new Exception("Title is empty");
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) throws Exception {
        if (!eventRepository.existsById(event.getId())) {
            throw new Exception("Event not found");
        }
        return eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}