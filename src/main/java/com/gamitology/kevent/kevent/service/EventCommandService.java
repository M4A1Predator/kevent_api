package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.entity.EventImageFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface EventCommandService {

    EventImageFile uploadZoneImage(Integer eventId, MultipartFile file) throws IOException;

}
