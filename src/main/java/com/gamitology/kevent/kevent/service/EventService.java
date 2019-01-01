package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.entity.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    Event getEventById(long id);
    Event addEvent(EventDto eventDto);
    Event updateEvent(long id, EventDto eventDto);
    ResponseEntity<String> updateArtists(long id, List<EventArtistDto> eventArtists);
}
