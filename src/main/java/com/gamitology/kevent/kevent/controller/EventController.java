package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
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

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable("eventId") long eventid) {
        return ResponseEntity.ok(eventService.getEventById(eventid));
    }

//    @PutMapping("/{eventId}")
//    @PreAuthorize("hasAuthority('admin')")
//    public ResponseEntity<Event> updateEventById(@PathVariable("eventId") long eventId, @Valid @RequestBody EventDto eventDto) {
//        Event savedEvent = eventService.updateEvent(eventId, eventDto);
//        return ResponseEntity.ok(savedEvent);
//    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody EventDto eventDto) {
        Event savedEvent = eventService.addEvent(eventDto);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventId") long eventId, @Valid @RequestBody UpdateEventRequest eventRequest) {
        Event updatedEvent = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok().body(updatedEvent);
    }

    @PutMapping("/{eventId}/updateArtists")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> updateArtists(@PathVariable("eventId") long eventId, @Valid @RequestBody List<EventArtistDto> eventArtists) {
        return eventService.updateArtists(eventId, eventArtists);
    }

}
