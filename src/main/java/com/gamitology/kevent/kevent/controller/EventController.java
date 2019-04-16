package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.dto.response.EventResponse;
import com.gamitology.kevent.kevent.dto.response.UploadCoverResponse;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.service.EventService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequestMapping("/events")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/public")
    public ResponseEntity getEventsPublic() {
        List<EventResponse> eventResponseList = eventService.getEventsPublic();
        return ResponseEntity.ok(eventResponseList);
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
    public ResponseEntity<Event> updateArtists(@PathVariable("eventId") long eventId, @Valid @RequestBody List<EventArtistDto> eventArtists) {
        Event updatedEvent = eventService.updateArtists(eventId, eventArtists);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/{eventId}/cover")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity uploadCover(@PathVariable("eventId") int eventId, @RequestParam("cover") MultipartFile file) throws IOException {
        try {
            Event event = eventService.uploadCover(eventId, file);
            UploadCoverResponse response = new UploadCoverResponse();
            response.setPath(event.getCoverPath());
            return ResponseEntity.ok(response);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{eventId}/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<byte[]> getCover(@PathVariable("eventId") int eventId) throws IOException {
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(eventService.getCover(eventId));
        try {
            FileInputStream fis = eventService.getCover(eventId);
            if(fis == null) {
                return ResponseEntity.notFound().build();
            }
            byte[] img = IOUtils.toByteArray(fis);
            fis.close();
            return ResponseEntity.ok(img);
        } catch (FileNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity deleteEvent(@PathVariable("eventId") int eventId) {
        Event event = eventService.disableEvent(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }
}
