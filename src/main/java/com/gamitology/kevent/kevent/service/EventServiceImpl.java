package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.constant.EventFileTypes;
import com.gamitology.kevent.kevent.dto.EventDto;
import com.gamitology.kevent.kevent.dto.TicketSellingDto;
import com.gamitology.kevent.kevent.dto.request.*;
import com.gamitology.kevent.kevent.dto.response.*;
import com.gamitology.kevent.kevent.entity.*;
import com.gamitology.kevent.kevent.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private EventImageFileRepository eventImageFileRepository;

    @Autowired
    private TicketSellingInfoRepository ticketSellingInfoRepository;

    @Value("${upload_path}")
    private String uploadPath;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findByEnabled(true);
    }

    public EventPageResponse getEventsPublic(SearchEventRequest searchEventRequest) {

        Sort.Order orderPerformTime = Sort.Order.desc("performTime");
        Pageable pageable = PageRequest.of(searchEventRequest.getPage() - 1, searchEventRequest.getPerPage(), Sort.by(orderPerformTime));

        Page<Event> eventPage = eventRepository.findByEnabledAndNameContainingIgnoreCase(
                true, searchEventRequest.getQ(), pageable
        );
        List<Event> eventList = eventPage.get().collect(Collectors.toList());
        List<EventResponse> eventResponseList = new ArrayList<>();
        for (Event event :
                eventList) {
            EventResponse eventResponse = new EventResponse();
            modelMapper.map(event, eventResponse);

            // artists
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

            // perform time
            List<PerformDateTime> performDateTimes = event.getPerformanceList().stream().map(p -> {
                PerformDateTime performDateTime = new PerformDateTime();
                performDateTime.setDatetime(p.getPerformTime());
                performDateTime.setNote(p.getNote());
                return performDateTime;
            }).collect(Collectors.toList());
            eventResponse.setPerformDateTimeList(performDateTimes);

            // ticket selling
            List<TicketSellingDto> ticketSellingDtoList = event.getTicketSellingInfoList().stream().map(t -> {
                TicketSellingDto ticketSellingDto = new TicketSellingDto();
                ticketSellingDto.setApproach(t.getApproach());
                ticketSellingDto.setNote(t.getNote());
                ticketSellingDto.setTicketStartTime(t.getStartDatetime());
                ticketSellingDto.setTicketEndTime(t.getEndDatetime());
                return ticketSellingDto;
            }).collect(Collectors.toList());
            eventResponse.setTicketSellingList(ticketSellingDtoList);

            eventResponseList.add(eventResponse);
        }
        EventPageResponse response = new EventPageResponse();
        response.setPage(searchEventRequest.getPage());
        response.setData(eventResponseList);
        response.setTotalPage(eventPage.getTotalPages());
        return response;
    }

    @Override
    public EventResponse getEventById(Integer id) {
        Event event = eventRepository.findByIdAndEnabledTrue(id).orElse(null);
        if (event == null || !event.isEnabled()) {
            return null;
        }

        // map
        EventResponse eventResponse = new EventResponse();
        modelMapper.map(event, eventResponse);

        // artists
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

        // perform time
        List<PerformDateTime> performDateTimes = event.getPerformanceList().stream().map(p -> {
            PerformDateTime performDateTime = new PerformDateTime();
            performDateTime.setDatetime(p.getPerformTime());
            performDateTime.setNote(p.getNote());
            return performDateTime;
        }).collect(Collectors.toList());
        eventResponse.setPerformDateTimeList(performDateTimes);

        // ticket selling
        List<TicketSellingDto> tickSellingListRes = event.getTicketSellingInfoList().stream().map(t -> {
            TicketSellingDto ticketSelling = new TicketSellingDto();
            ticketSelling.setApproach(t.getApproach());
            ticketSelling.setNote(t.getNote());
            ticketSelling.setTicketStartTime(t.getStartDatetime());
            ticketSelling.setTicketEndTime(t.getEndDatetime());
            return ticketSelling;
        }).collect(Collectors.toList());
        eventResponse.setTicketSellingList(tickSellingListRes);

        return eventResponse;
    }

    @Transactional
    @Override
    public Event addEvent(EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        event.setEnabled(true);
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event updateEvent(Integer id, UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return null;
        }

        event.setName(updateEventRequest.getName());
        event.setDescription(updateEventRequest.getDescription());
        event.setLocation(updateEventRequest.getLocation());
        event.setTicketStartTime(updateEventRequest.getTicketStartTime());
        event.setTicketEndTime(updateEventRequest.getTicketEndTime());
        if (updateEventRequest.getOnlineDetail() != null && !updateEventRequest.getOnlineDetail().trim().isEmpty()) {
            event.setOnlineDetail(updateEventRequest.getOnlineDetail());
        }

        // save perform time
        // remove old perform times
        performanceRepository.removeByEventId(event.getId());

        // ticket selling info
        if (updateEventRequest.getTicketSellingList() != null) {
            // clear old data
            ticketSellingInfoRepository.removeByEventId(event.getId());

            // save data
            for (TicketSellingDto ticketSellingReq :
                    updateEventRequest.getTicketSellingList()) {
                TicketSellingInfo ticketSellingInfo = new TicketSellingInfo();
                ticketSellingInfo.setEventId(event.getId());
                ticketSellingInfo.setApproach(ticketSellingReq.getApproach());
                ticketSellingInfo.setStartDatetime(ticketSellingReq.getTicketStartTime());
                ticketSellingInfo.setEndDatetime(ticketSellingReq.getTicketEndTime());
                ticketSellingInfo.setNote(ticketSellingReq.getNote());
                ticketSellingInfo.setCreatedAt(new Date());
                ticketSellingInfoRepository.save(ticketSellingInfo);
            }
        }

        // save
        List<Performance> performances = new ArrayList<>();
        for (PerformTimeRequest p : updateEventRequest.getPerformDateTimeList()) {
            Performance performance = new Performance();
            performance.setEventId(event.getId());
            performance.setPerformTime(p.getDatetime());
            performances.add(performance);
        }
        performanceRepository.saveAll(performances);

        event.setModifiedAt(new Date());

        // Update event
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event updateArtists(Integer id, List<EventArtistDto> eventArtists) {
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
    public EventImageFileResponse uploadCover(Integer eventId, MultipartFile cover) throws IOException {
        String fileExt = FilenameUtils.getExtension(cover.getOriginalFilename());
        String fileName = "event-" + eventId + "-cover." + fileExt;
        String coverPath = "covers";

        InputStream is = cover.getInputStream();
        String folderPath = uploadPath + "/" + coverPath;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        // save file
        Files.copy(is, Paths.get(folderPath + "/" + fileName),
                StandardCopyOption.REPLACE_EXISTING);
        is.close();

        // get event
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return null;
        }

        // save file info/
        EventImageFile imageFile = eventImageFileRepository.findByEventIdAndFileType(eventId, EventFileTypes.COVER.typeName).orElse(null);
        if (imageFile == null) {
            imageFile = new EventImageFile();
        }
        String path = coverPath + "/" + fileName;
        imageFile.setFileName(fileName);
        imageFile.setFilePath(path);
        imageFile.setFileExt(fileExt);
        imageFile.setFileType(EventFileTypes.COVER.typeName);
        imageFile.setEvent(event);
        eventImageFileRepository.save(imageFile);

        EventImageFileResponse response = new EventImageFileResponse();
        response.setEventId(eventId);
        response.setFileName(fileName);
        return response;
    }

    @Override
    public FileInputStream getCover(int eventId) throws FileNotFoundException {
        Optional<EventImageFile> coverFile = eventImageFileRepository.findByEventIdAndFileType(eventId, EventFileTypes.COVER.typeName);
        if (!coverFile.isPresent()) {
            return null;
        }
        File file = new File(uploadPath + "/" + coverFile.get().getFilePath());
        return new FileInputStream(file);
    }

    public Event disableEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if(event == null) {
            return null;
        }
        event.setEnabled(false);
        return eventRepository.save(event);
    }

    @Override
    public EventResponse getEventPublic(Integer eventId) {

        Event event = eventRepository.findByIdAndEnabledTrue(eventId).orElse(null);
        if (event == null || !event.isEnabled()) {
            return null;
        }

        // map
        EventResponse eventResponse = new EventResponse();
        modelMapper.map(event, eventResponse);

        // artists
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

        // perform time
        List<PerformDateTime> performDateTimes = event.getPerformanceList().stream().map(p -> {
            PerformDateTime performDateTime = new PerformDateTime();
            performDateTime.setDatetime(p.getPerformTime());
            performDateTime.setNote(p.getNote());
            return performDateTime;
        }).collect(Collectors.toList());
        eventResponse.setPerformDateTimeList(performDateTimes);

        // ticket selling
        List<TicketSellingDto> tickSellingListRes = event.getTicketSellingInfoList().stream().map(t -> {
            TicketSellingDto ticketSelling = new TicketSellingDto();
            ticketSelling.setApproach(t.getApproach());
            ticketSelling.setNote(t.getNote());
            ticketSelling.setTicketStartTime(t.getStartDatetime());
            ticketSelling.setTicketEndTime(t.getEndDatetime());
            return ticketSelling;
        }).collect(Collectors.toList());
        eventResponse.setTicketSellingList(tickSellingListRes);

        // cover file


        return eventResponse;
    }
}
