package edu.yevynchuk.eventapp.service;

import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.repository.EventUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventUserService {

    private final EventUserRepository eventUserRepository;

    public EventUser addEventUser(EventUser eventUser) {
        return eventUserRepository.save(eventUser);
    }

    public void deleteEventUser(Long id) {
        eventUserRepository.deleteById(id);
    }

    public List<EventUser> getAllEventUsers() {
        return eventUserRepository.findAll();
    }

    public EventUser getEventUserById(Long id) {
        Optional<EventUser> eventUser = eventUserRepository.findById(id);
        return eventUser.orElse(null);
    }

}
