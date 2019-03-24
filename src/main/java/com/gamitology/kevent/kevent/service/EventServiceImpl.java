package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.entity.EventArtist;
import com.gamitology.kevent.kevent.repository.EventArtistRepository;
import com.gamitology.kevent.kevent.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventArtistRepository eventArtistRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    @Override
    public Event addEvent(EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    @Transactional
    @Override
    public Event updateEvent(long id, UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(id);
        if (event == null) {
            return null;
        }

        event.setName(updateEventRequest.getName());
        event.setDescription(updateEventRequest.getDescription());
        event.setLocation(updateEventRequest.getLocation());
        event.setPerformTime(updateEventRequest.getPerformTime());
        event.setTicketStartTime(updateEventRequest.getTicketStartTime());
        event.setTicketEndTime(updateEventRequest.getTicketEndTime());
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    @Transactional
    @Override
    public Event updateArtists(long id, List<EventArtistDto> eventArtists) {
        // Get event
        Event event = eventRepository.findById(id);

        // Create artists list
        List<EventArtist> eventArtistList = new ArrayList<>();
        for(EventArtistDto eventArtistDto: eventArtists) {

            // Check duplicate
            List<EventArtist> existArtists = event.getEventArtists().stream().filter(ea -> ea.getArtist().getId() == eventArtistDto.getArtistId()).collect(Collectors.toList());
            if (!existArtists.isEmpty()) {
                continue;
            }

            EventArtist ea = new EventArtist();
            ea.setEvent(event);

            Artist a = new Artist();
            a.setId(eventArtistDto.getArtistId());
            ea.setArtist(a);

            eventArtistList.add(ea);
        }

        // Update artist list
        eventArtistRepository.saveAll(eventArtistList);

        List<EventArtist> currentEAList = eventArtistRepository.findByEventId(event.getId());

        // Event timestamp
        event.setModifiedAt(new Date());
        event = eventRepository.save(event);
        event = eventRepository.findById(id);

        // Remove deleted one
        for (EventArtist ea :
                currentEAList) {
            Optional<EventArtistDto> existArtistDto = eventArtists.stream()
                    .filter(e -> e.getArtistId() == ea.getArtist().getId())
                    .findFirst();
            if (!existArtistDto.isPresent()) {
                eventArtistRepository.delete(ea);
            }
        }

        return event;
    }
}
