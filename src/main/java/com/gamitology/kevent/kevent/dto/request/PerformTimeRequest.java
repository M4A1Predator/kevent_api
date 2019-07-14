package com.gamitology.kevent.kevent.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class PerformTimeRequest {
    private Date datetime;
    private String note;
}
