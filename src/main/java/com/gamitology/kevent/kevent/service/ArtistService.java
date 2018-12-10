package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.entity.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    Optional<Artist> getArtistByName(String name);
    Artist addArtist(ArtistDto artistDto);
    List<Artist> getAllArtists();
}
