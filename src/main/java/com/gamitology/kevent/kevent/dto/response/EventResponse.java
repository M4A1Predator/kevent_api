package com.gamitology.kevent.kevent.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import com.gamitology.kevent.kevent.dto.TicketSellingDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventResponse {
    private int id;
    private String name;
    private String description;
    private String location;
    private String zoneDetail;
    private String onlineDetail;

    List<PerformDateTime> performTimes;

    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;
    private String coverPath;

    private List<EventArtistResponse> eventArtistList;

    private List<PerformDateTime> performDateTimeList;

    private List<TicketSellingDto> ticketSellingList;
}
