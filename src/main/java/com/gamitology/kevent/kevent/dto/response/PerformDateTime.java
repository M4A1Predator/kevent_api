package com.gamitology.kevent.kevent.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import lombok.Data;

import java.util.Date;

@Data
public class PerformDateTime {

    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date datetime;
    private String note;

}
