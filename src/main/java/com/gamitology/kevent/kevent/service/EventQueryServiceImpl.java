package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.constant.EventFileTypes;
import com.gamitology.kevent.kevent.entity.EventImageFile;
import com.gamitology.kevent.kevent.repository.EventImageFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class EventQueryServiceImpl implements EventQueryService {

    @Value("${upload_path}")
    private String uploadPath;

    private final EventImageFileRepository eventImageFileRepository;

    public EventQueryServiceImpl(EventImageFileRepository eventImageFileRepository) {
        this.eventImageFileRepository = eventImageFileRepository;
    }

    @Override
    public FileInputStream getZoneImage(Integer eventId) throws FileNotFoundException {
        EventImageFile imageFile = eventImageFileRepository.findByEventIdAndFileType(eventId, EventFileTypes.ZONE.typeName)
                .orElse(null);
        if (imageFile == null) {
            return null;
        }

        File file = new File(uploadPath + "\\" + imageFile.getFilePath());
        return new FileInputStream(file);
    }
}
