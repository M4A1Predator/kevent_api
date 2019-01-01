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
    public Artist updateArtist(long id, ArtistDto artistDto) {
        Artist artist = artistRepository.findById(id).get();
        if (artist == null) {
            return null;
        }

        artist.setName(artistDto.getName());
        artist.setName(artistDto.getDetail());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getArtistById(long id) {
        return artistRepository.findById(id).get();
    }
}
