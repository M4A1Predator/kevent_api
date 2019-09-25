package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.SearchEventRequest;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.dto.response.EventImageFileResponse;
import com.gamitology.kevent.kevent.dto.response.EventPageResponse;
import com.gamitology.kevent.kevent.dto.response.EventResponse;
import com.gamitology.kevent.kevent.entity.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    EventPageResponse getEventsPublic(SearchEventRequest request);
    EventResponse getEventById(Integer id);
    Event addEvent(EventDto eventDto);
    Event updateEvent(Integer id, UpdateEventRequest eventDto);
    Event updateArtists(Integer id, List<EventArtistDto> eventArtists);
    EventImageFileResponse uploadCover(Integer eventId, MultipartFile cover) throws IOException;
    FileInputStream getCover(int eventId) throws FileNotFoundException;
    Event disableEvent(Integer eventId);

    EventResponse getEventPublic(Integer eventId);
}
