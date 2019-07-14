package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {

    List<Performance> removeByEventId(Integer eventId);

}
