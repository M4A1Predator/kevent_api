package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.request.EventArtistDto;
import com.gamitology.kevent.kevent.dto.request.SearchEventRequest;
import com.gamitology.kevent.kevent.dto.request.UpdateEventRequest;
import com.gamitology.kevent.kevent.dto.response.EventArtistResponse;
import com.gamitology.kevent.kevent.dto.response.EventResponse;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.entity.EventArtist;
import com.gamitology.kevent.kevent.repository.EventArtistRepository;
import com.gamitology.kevent.kevent.repository.EventRepository;
import org.apache.logging.log4j.core.util.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventArtistRepository eventArtistRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${upload_path}")
    private String uploadPath;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findByEnabled(true);
    }

    public List<EventResponse> getEventsPublic(SearchEventRequest searchEventRequest) {

        Pageable pageable = PageRequest.of(searchEventRequest.getPage() - 1, searchEventRequest.getPageSize());

        Page<Event> eventPage = eventRepository.findByEnabledAndNameContaining(true, "", pageable);
        List<Event> eventList = eventPage.get().collect(Collectors.toList());
        List<EventResponse> eventResponseList = new ArrayList<>();
        for (Event event :
                eventList) {
            EventResponse eventResponse = new EventResponse();
            modelMapper.map(event, eventResponse);

            List<EventArtistResponse> earList = new ArrayList<>();
            for (EventArtist ea :
                    event.getEventArtists()) {
                EventArtistResponse ear = new EventArtistResponse();
                ear.setArtistId((int) ea.getArtist().getId());
                ear.setName(ea.getArtist().getName());
                ear.setDetail(ea.getArtist().getDetail());
                ear.setNote(ea.getNote());

                earList.add(ear);
            }

            eventResponse.setEventArtistList(earList);

            eventResponseList.add(eventResponse);
        }
        return eventResponseList;
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Event addEvent(EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        event.setEnabled(true);
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    @Transactional
    @Override
    public Event updateEvent(long id, UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(id).orElse(null);
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
        Event event = eventRepository.findById(id).orElse(null);

        // Create artists list
        List<EventArtist> eventArtistList = new ArrayList<>();
        for (EventArtistDto eventArtistDto : eventArtists) {

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
        event = eventRepository.findById(id).orElse(null);

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

    @Override
    public Event uploadCover(long eventId, MultipartFile cover) throws IOException {
        String fileExt = FilenameUtils.getExtension(cover.getOriginalFilename());
        String fileName = "event-" + eventId + "-cover." + fileExt;

        InputStream is = cover.getInputStream();
        String folderPath = uploadPath + "\\covers";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(is, Paths.get(folderPath + "\\" + fileName),
                StandardCopyOption.REPLACE_EXISTING);
        is.close();

        // get event
        Event event = eventRepository.findById(eventId).orElse(null);
        event.setCoverPath(fileName);
        return eventRepository.save(event);
    }

    @Override
    public FileInputStream getCover(int eventId) throws FileNotFoundException {
        Optional<Event> event = eventRepository.findById(eventId);
        if (!event.isPresent()) {
            return null;
        }
        File file = new File(uploadPath + "\\covers\\" + "event-" + eventId + "-cover.jpg");
        return new FileInputStream(file);
    }

    public Event disableEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if(event == null) {
            return null;
        }
        event.setEnabled(false);
        return eventRepository.save(event);
    }

    @Override
    public EventResponse getEventPublic(Integer eventId) {

        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null || !event.isEnabled()) {
            return null;
        }

        // map
        EventResponse eventResponse = new EventResponse();
        modelMapper.map(event, eventResponse);

        List<EventArtistResponse> earList = new ArrayList<>();
        for (EventArtist ea :
                event.getEventArtists()) {
            EventArtistResponse ear = new EventArtistResponse();
            ear.setArtistId((int) ea.getArtist().getId());
            ear.setName(ea.getArtist().getName());
            ear.setDetail(ea.getArtist().getDetail());
            ear.setNote(ea.getNote());

            earList.add(ear);
        }

        eventResponse.setEventArtistList(earList);
        return eventResponse;
    }
}
