package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.entity.Artist;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface ArtistCommandService {

    Optional<Artist> uploadArtistCover(int artistId, MultipartFile file) throws IOException;

    void deleteArtist(int artistId);
}
