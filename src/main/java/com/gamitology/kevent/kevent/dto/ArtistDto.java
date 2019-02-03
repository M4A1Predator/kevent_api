package com.gamitology.kevent.kevent.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArtistDto {
    @NotNull
    private String name;
    private String detail;
}
