package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.constant.EventFileTypes;
import com.gamitology.kevent.kevent.dto.request.ZoneDetailRequest;
import com.gamitology.kevent.kevent.entity.Event;
import com.gamitology.kevent.kevent.entity.EventImageFile;
import com.gamitology.kevent.kevent.repository.EventImageFileRepository;
import com.gamitology.kevent.kevent.repository.EventRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class EventCommandServiceImpl implements EventCommandService {

    @Value("${upload_path}")
    private String uploadPath;

    private final EventImageFileRepository eventImageFileRepository;
    private final EventRepository eventRepository;

    public EventCommandServiceImpl(EventImageFileRepository eventImageFileRepository,
                                   EventRepository eventRepository) {
        this.eventImageFileRepository = eventImageFileRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventImageFile uploadZoneImage(Integer eventId, MultipartFile file) throws IOException {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = "event-" + eventId + "-zone." + fileExt;
        String parentPath = "zones";

        InputStream is = file.getInputStream();
        String folderPath = uploadPath + "/" + parentPath;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        // save file
        Files.copy(is, Paths.get(folderPath + "/" + fileName),
                StandardCopyOption.REPLACE_EXISTING);
        is.close();

        // save file info
        EventImageFile imageFile = eventImageFileRepository.findByEventIdAndFileType(eventId, EventFileTypes.ZONE.typeName)
                .orElse(new EventImageFile());
        String path = parentPath + "/" + fileName;
        imageFile.setFileName(fileName);
        imageFile.setFilePath(path);
        imageFile.setFileExt(fileExt);
        imageFile.setFileType(EventFileTypes.ZONE.typeName);
        Event event = new Event();
        event.setId(eventId);
        imageFile.setEvent(event);
        return eventImageFileRepository.save(imageFile);
    }

    @Override
    public Event updateZoneDetail(int eventId, ZoneDetailRequest zoneDetailRequest) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return null;
        }

        event.setZoneDetail(zoneDetailRequest.getPriceDetail());

        return eventRepository.save(event);
    }
}
