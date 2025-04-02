package edu.yevynchuk.eventapp.controller;

import edu.yevynchuk.eventapp.model.EventUser;
import edu.yevynchuk.eventapp.model.Event;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.service.EventUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventUser")
@RequiredArgsConstructor
public class EventUserController {

    private final EventUserService eventUserService;

    @GetMapping
    public List<EventUser> getAllEventUsers() {
        return eventUserService.getAllEventUsers();
    }

    @GetMapping("/{id}")
    public EventUser getEventUser(@PathVariable Long id) {
        return eventUserService.getEventUserById(id);
    }

    @PostMapping
    public EventUser addEventUser(@RequestBody EventUser eventUser) {
        return eventUserService.addEventUser(eventUser);
    }

    @DeleteMapping("/{id}")
    public void deleteEventUser(@PathVariable Long id) {
        eventUserService.deleteEventUser(id);
    }

    @PutMapping("/{id}")
    public EventUser updateEventUser(@PathVariable Long id, @RequestBody EventUser eventUser) {
        eventUser.setId(id);
        return eventUserService.addEventUser(eventUser);
    }

}
