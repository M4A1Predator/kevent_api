package com.gamitology.kevent.kevent.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateEventRequest {
    private String name;
    private String description;
    private String location;
    private Date performTime;
    private Date ticketStartTime;
    private Date ticketEndTime;
}
