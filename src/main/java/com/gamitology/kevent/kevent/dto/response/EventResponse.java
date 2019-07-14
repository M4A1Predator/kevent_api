package com.gamitology.kevent.kevent.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventResponse {
    private int id;
    private String name;
    private String description;
    private String location;

    List<PerformDateTime> performTimes;

    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;
    private String coverPath;

    private List<EventArtistResponse> eventArtistList;

    private List<PerformDateTime> performDateTimeList;
}
