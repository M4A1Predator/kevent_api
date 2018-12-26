package com.gamitology.kevent.kevent.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Event extends BaseEntity {
    private String name;
    private String description;
    private String location;
    private Date performTime;
    private Date ticketStartTime;
    private Date ticketEndTime;

    @OneToMany(
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    @JoinTable(name = "event_artist", inverseJoinColumns=@JoinColumn(name = "id"))
    private List<EventArtist> eventArtists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPerformTime() {
        return performTime;
    }

    public void setPerformTime(Date performTime) {
        this.performTime = performTime;
    }

    public Date getTicketStartTime() {
        return ticketStartTime;
    }

    public void setTicketStartTime(Date ticketStartTime) {
        this.ticketStartTime = ticketStartTime;
    }

    public Date getTicketEndTime() {
        return ticketEndTime;
    }

    public void setTicketEndTime(Date ticketEndTime) {
        this.ticketEndTime = ticketEndTime;
    }

    public List<EventArtist> getEventArtists() {
        return eventArtists;
    }

    public void setEventArtists(List<EventArtist> eventArtists) {
        this.eventArtists = eventArtists;
    }
}
