package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.EventArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventArtistRepository extends JpaRepository<EventArtist, Long> {
}
