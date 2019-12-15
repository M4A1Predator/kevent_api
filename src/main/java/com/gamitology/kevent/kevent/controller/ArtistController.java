package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateArtistRequest;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.service.ArtistCommandService;
import com.gamitology.kevent.kevent.service.ArtistService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/artists")
@RestController
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @Autowired
    ArtistCommandService artistCommandService;

    @GetMapping
    public List<Artist> getArtists(){
        return artistService.getAllArtists();
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<Artist> getArtistById(@PathVariable("artistId") Integer artistId) {
        Artist artist = artistService.getArtistById(artistId);
        if (artist == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(artist);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Artist> addArtist(@Valid @RequestBody ArtistDto artistDto) {
        Artist savedArtist = artistService.addArtist(artistDto);
        return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
    }

    @PutMapping("/{artistId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Artist> updateArtist(@PathVariable("artistId") Integer artistId, @Valid @RequestBody UpdateArtistRequest updateArtistRequest) {
        Artist updatedArtist = artistService.updateArtist(artistId, updateArtistRequest);
        if (updateArtistRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedArtist);
    }

    @PutMapping("/{artistId}/images/cover")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity uploadArtistCover(@PathVariable("artistId") int artistId, @RequestParam("cover") MultipartFile file) throws IOException {
        Optional<Artist> artist = artistCommandService.uploadArtistCover(artistId, file);
        if (!artist.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{artistId}/images/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getArtistCover(@PathVariable("artistId") int artistId) throws IOException {
        FileInputStream fis = artistService.getArtistCover(artistId);
        if (fis == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] img = IOUtils.toByteArray(fis);
        fis.close();
        return ResponseEntity.ok(img);
    }

    @DeleteMapping("/{artistId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity deleteArtist(@PathVariable("artistId") int artistId) {
        artistCommandService.deleteArtist(artistId);
        return ResponseEntity.ok().build();
    }

}
