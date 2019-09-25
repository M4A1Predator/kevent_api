package com.gamitology.kevent.kevent.dto.request;

import lombok.Data;

@Data
public class SearchEventRequest {

    private Integer page = 1;
    private Integer perPage = 7;

    private String q = "";
}
