package com.gamitology.kevent.kevent.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class EventPageResponse {

    private int page;
    private int totalPage;
    private List<EventResponse> data;

}
