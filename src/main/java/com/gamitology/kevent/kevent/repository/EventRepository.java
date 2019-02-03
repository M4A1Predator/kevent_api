package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
    List<Event> findAll();
}