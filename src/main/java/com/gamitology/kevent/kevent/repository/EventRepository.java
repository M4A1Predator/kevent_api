package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
    Optional<Event> findById(long id);
    List<Event> findAll();
    List<Event> findByEnabled(boolean enabled);
    Page<Event> findByEnabled(boolean enabled, Pageable page);
}
