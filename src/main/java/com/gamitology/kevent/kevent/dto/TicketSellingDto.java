package com.gamitology.kevent.kevent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import lombok.Data;

import java.util.Date;

@Data
public class TicketSellingDto {

    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;

    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;

    private String approach;

    private String note;
}
