package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return artistRepository.findByName(name);
    }

    @Override
    public Artist addArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        Artist savedArtist = artistRepository.save(artist);
        return savedArtist;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
}
