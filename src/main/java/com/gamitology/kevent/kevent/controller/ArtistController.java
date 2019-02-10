package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.ArtistDto;
import com.gamitology.kevent.kevent.dto.request.UpdateArtistRequest;
import com.gamitology.kevent.kevent.entity.Artist;
import com.gamitology.kevent.kevent.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/artists")
@RestController
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping
    public List<Artist> getArtists(){
        return artistService.getAllArtists();
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<Artist> getArtistById(@PathVariable("artistId") Long artistId) {
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
    public ResponseEntity<Artist> updateArtist(@PathVariable("artistId") Long artistId, @Valid @RequestBody UpdateArtistRequest updateArtistRequest) {
        Artist updatedArtist = artistService.updateArtist(artistId, updateArtistRequest);
        if (updateArtistRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedArtist);
    }
}
