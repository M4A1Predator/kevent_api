package com.gamitology.kevent.kevent.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.config.CustomDateSerializer;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UpdateEventRequest {
    private String name;
    private String description;
    private String location;

//    @JsonFormat(pattern = DateFormatConstant.ISOString)
//    @JsonSerialize(using = CustomDateSerializer.class)
    private Date performTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;
}
