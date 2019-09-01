package com.gamitology.kevent.kevent.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class EventImageFile extends BaseEntity {

    private String fileName;
    private String fileExt;
    @Lob
    private byte[] fileByte;
    private String filePath;
    private String fileType;
    private String description;
    private boolean isDeleted = false;

    @ManyToOne
    private Event event;
}
