package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.EventImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventImageFileRepository extends JpaRepository<EventImageFile, Integer> {

    Optional<EventImageFile> findByEventIdAndFileType(Integer eventId, String fileType);

}
