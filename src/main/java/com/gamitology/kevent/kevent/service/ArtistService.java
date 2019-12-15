package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateArtistRequest;
import com.gamitology.kevent.kevent.entity.Artist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ArtistService {
    Optional<Artist> getArtistByName(String name);
    Artist addArtist(ArtistDto artistDto);
    Artist updateArtist(Integer id, UpdateArtistRequest updateArtistRequest);
    List<Artist> getAllArtists();
    Artist getArtistById(Integer id);

    FileInputStream getArtistCover(int artistId) throws FileNotFoundException;
}
