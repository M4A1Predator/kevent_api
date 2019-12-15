package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.repository.ArtistRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Component
public class ArtistCommandServiceImpl implements ArtistCommandService {

    @Value("${upload_path}")
    private String uploadPath;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public Optional<Artist> uploadArtistCover(int artistId, MultipartFile file) throws IOException {
        // get artist
        Artist artist = artistRepository.findById(artistId).get();
        if (artist == null) {
            return Optional.empty();
        }

        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = "artist-" + artist.getId() + "." + fileExt;
        String parentPath = "covers";

        InputStream is = file.getInputStream();
        String folderPath = uploadPath + "/" + parentPath;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        // save file
        Files.copy(is, Paths.get(folderPath + "/" + fileName),
                StandardCopyOption.REPLACE_EXISTING);
        is.close();

        // file info
        String path = parentPath + "/" + fileName;
        artist.setCoverPath(path);
        artist = artistRepository.save(artist);
        return Optional.of(artist);
    }

    @Override
    public void deleteArtist(int artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (!artist.isPresent()) {
            return;
        }

        artist.get().setActive(false);
        artistRepository.save(artist.get());
    }
}
