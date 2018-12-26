package com.gamitology.kevent.kevent.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({"event"})
public class EventArtist extends BaseEntity {
    @ManyToOne
    private Event event;

    @ManyToOne
    private Artist artist;

    private String note;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
