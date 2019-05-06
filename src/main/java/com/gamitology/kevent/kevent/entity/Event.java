package com.gamitology.kevent.kevent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Event extends BaseEntity {

    private String name;
    private String description;
    private String location;
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date performTime;
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketStartTime;
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date ticketEndTime;

    @OneToMany(
            cascade = CascadeType.DETACH
//            orphanRemoval = true
    )
    @JoinTable(name = "event_artist", inverseJoinColumns=@JoinColumn(name = "id"))
    @Column(insertable = false, updatable = false)
    private List<EventArtist> eventArtists;

    private String coverPath;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "BIT(1) default 1")
    private boolean enabled;

}
