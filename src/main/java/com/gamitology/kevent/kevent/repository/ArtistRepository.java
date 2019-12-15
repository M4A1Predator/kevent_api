package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findByName(String name);

    List<Artist> findByActive(boolean isActive);
}
