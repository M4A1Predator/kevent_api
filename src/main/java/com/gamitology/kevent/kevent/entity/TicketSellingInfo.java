package com.gamitology.kevent.kevent.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class TicketSellingInfo extends BaseEntity {

    private Integer eventId;

    private String approach;

    private Date startDatetime;

    private Date endDatetime;

    private String note;

}
