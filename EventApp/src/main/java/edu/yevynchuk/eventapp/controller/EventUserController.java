package edu.yevynchuk.eventapp.controller;

import edu.yevynchuk.eventapp.dto.EventUserDTO;
import edu.yevynchuk.eventapp.service.EventUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventUser")
@RequiredArgsConstructor
public class EventUserController {

    private final EventUserService eventUserService;

    @GetMapping
    public List<EventUserDTO> getAllEventUsers() {
        return eventUserService.getAllEventUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventUserDTO> getEventUser(@PathVariable Long id) {
        try {
            EventUserDTO dto = eventUserService.getEventUserById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public EventUserDTO addEventUser(@Valid @RequestBody EventUserDTO dto) {
        return eventUserService.addEventUser(dto);
    }

    @PutMapping("/{id}")
    public EventUserDTO updateEventUser(@PathVariable Long id, @Valid @RequestBody EventUserDTO dto) {
        return eventUserService.updateEventUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteEventUser(@PathVariable Long id) {
        eventUserService.deleteEventUser(id);
    }
}
