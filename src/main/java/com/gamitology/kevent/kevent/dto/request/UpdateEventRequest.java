package com.gamitology.kevent.kevent.dto.request;

import com.gamitology.kevent.kevent.dto.TicketSellingDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateEventRequest {
    private String name;
    private String description;
    private String location;

//    @JsonFormat(pattern = DateFormatConstant.ISOString)
//    @JsonSerialize(using = CustomDateSerializer.class)
//    private Date performTime;
    private List<PerformTimeRequest> performDateTimeList;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;

    private List<TicketSellingDto> ticketSellingList;
}
