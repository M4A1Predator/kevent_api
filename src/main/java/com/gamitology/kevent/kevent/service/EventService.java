package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.dto.response.EventResponse;
import com.gamitology.kevent.kevent.entity.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    List<EventResponse> getEventsPublic();
    Event getEventById(long id);
    Event addEvent(EventDto eventDto);
    Event updateEvent(long id, UpdateEventRequest eventDto);
    Event updateArtists(long id, List<EventArtistDto> eventArtists);
    Event uploadCover(long eventId, MultipartFile cover) throws IOException;
    FileInputStream getCover(int eventId) throws FileNotFoundException;
    Event disableEvent(long eventId);
}
