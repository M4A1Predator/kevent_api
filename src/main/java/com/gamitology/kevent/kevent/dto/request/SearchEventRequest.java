package com.gamitology.kevent.kevent.dto.request;

import lombok.Data;

@Data
public class SearchEventRequest {

    private Integer page = 1;
    private Integer pageSize = 7;

    private String text;
}
