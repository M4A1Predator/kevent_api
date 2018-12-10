package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/events")
@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public List<Event> getEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody EventDto eventDto) {
        Event savedEvent = eventService.addEvent(eventDto);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

}
