package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.KeventApplication;
import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.SearchEventRequest;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.dto.response.EventImageFileResponse;
import com.gamitology.kevent.kevent.dto.response.EventPageResponse;
import com.gamitology.kevent.kevent.dto.response.EventResponse;
import com.gamitology.kevent.kevent.dto.response.UploadCoverResponse;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.entity.EventImageFile;
import com.gamitology.kevent.kevent.service.EventCommandService;
import com.gamitology.kevent.kevent.service.EventQueryService;
import com.gamitology.kevent.kevent.service.EventService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private EventCommandService eventCommandService;

    @Autowired
    private EventQueryService eventQueryService;

    @GetMapping
    public List<Event> getEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/public")
    public ResponseEntity getEventsPublic(SearchEventRequest searchEventRequest) {
        EventPageResponse response = eventService.getEventsPublic(searchEventRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/{eventId}")
    public ResponseEntity getEventPublic(@PathVariable("eventId")int eventId) {
        EventResponse eventResponse = eventService.getEventPublic(eventId);
        if (eventResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("eventId") Integer eventid) {
        return ResponseEntity.ok(eventService.getEventById(eventid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody EventDto eventDto) {
        Event savedEvent = eventService.addEvent(eventDto);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventId") Integer eventId, @Valid @RequestBody UpdateEventRequest eventRequest) {
        Event updatedEvent = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok().body(updatedEvent);
    }

    @PutMapping("/{eventId}/updateArtists")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Event> updateArtists(@PathVariable("eventId") Integer eventId, @Valid @RequestBody List<EventArtistDto> eventArtists) {
        Event updatedEvent = eventService.updateArtists(eventId, eventArtists);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/{eventId}/cover")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity uploadCover(@PathVariable("eventId") int eventId, @RequestParam("cover") MultipartFile file) throws IOException {
        try {
            EventImageFileResponse response= eventService.uploadCover(eventId, file);
//            UploadCoverResponse response = new UploadCoverResponse();
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
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{eventId}")
    public ResponseEntity deleteEvent(@PathVariable("eventId") int eventId) {
        Event event = eventService.disableEvent(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{eventId}/images/zone")
    public ResponseEntity uploadZoneImage(@PathVariable("eventId") int eventId, @RequestParam("file") MultipartFile file)
            throws IOException {
        EventImageFile imageFile = eventCommandService.uploadZoneImage(eventId, file);
        if (imageFile == null) {
            return ResponseEntity.badRequest().build();
        }

        EventImageFileResponse response = new EventImageFileResponse();
        response.setFileName(imageFile.getFileName());
        response.setEventId(eventId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{eventId}/images/zone", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<byte[]> getImageZone(@PathVariable("eventId") int eventId) throws IOException {
        FileInputStream fis = eventQueryService.getZoneImage(eventId);
        if(fis == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] img = IOUtils.toByteArray(fis);
        fis.close();
        return ResponseEntity.ok(img);
    }
}
