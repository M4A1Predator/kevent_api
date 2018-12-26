package com.gamitology.kevent.kevent.dto.request;

public class EventArtistDto {
    private long artistId;
    private String note;

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
