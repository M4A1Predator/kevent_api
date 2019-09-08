package com.gamitology.kevent.kevent.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public interface EventQueryService {

    FileInputStream getZoneImage(Integer eventId) throws FileNotFoundException;

}
