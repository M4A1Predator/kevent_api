package com.gamitology.kevent.kevent.dto;

import java.util.Date;
import java.util.List;

public class EventDto {
    private String name;
    private String description;
    private String location;
    private String onlineDetail;
    private Date performTime;
    private Date ticketStartTime;
    private Date ticketEndTime;

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
}
