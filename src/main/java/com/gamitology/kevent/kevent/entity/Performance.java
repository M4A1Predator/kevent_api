package com.gamitology.kevent.kevent.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Performance extends BaseEntity {

    private Date performTime;

    private String note;

    @Column(name = "event_id")
    private Integer eventId;
}
