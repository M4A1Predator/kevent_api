package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.EventArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventArtistRepository extends JpaRepository<EventArtist, Integer> {

    List<EventArtist> findByEventId(Integer eventId);

    @Query("delete from EventArtist ea where id = :id")
    void deleteById(@Param("id") Integer id);

}
