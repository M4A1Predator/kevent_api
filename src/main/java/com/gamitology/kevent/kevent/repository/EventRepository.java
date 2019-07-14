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
public interface EventRepository extends JpaRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {
    Optional<Event> findById(Integer id);
    Optional<Event> findByIdAndEnabledTrue(Integer id);
    List<Event> findAll();
    List<Event> findByEnabled(boolean enabled);
    Page<Event> findByEnabledAndNameContaining(boolean enabled, String name, Pageable page);
}
