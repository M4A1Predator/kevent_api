package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateArtistRequest;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Value("${upload_path}")
    private String uploadPath;

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
        artist.setActive(true);
        Artist savedArtist = artistRepository.save(artist);
        return savedArtist;
    }

    @Override
    public Artist updateArtist(Integer id, UpdateArtistRequest updateArtistRequest) {
        Artist artist = artistRepository.findById(id).get();
        if (artist == null) {
            return null;
        }

        artist.setName(updateArtistRequest.getName());
        artist.setDetail(updateArtistRequest.getDetail());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findByActive(true);
    }

    @Override
    public Artist getArtistById(Integer id) {
        return artistRepository.findById(id).get();
    }

    @Override
    public FileInputStream getArtistCover(int artistId) throws FileNotFoundException {
        Artist artist = getArtistById(artistId);
        if (artist == null) {
            return null;
        }

        // must has cover
        if (artist.getCoverPath() == null) {
            return null;
        }

        File file = new File(uploadPath + "/" + artist.getCoverPath());
        return new FileInputStream(file);
    }
}
