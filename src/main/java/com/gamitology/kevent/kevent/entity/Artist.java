package com.gamitology.kevent.kevent.entity;

import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Artist extends BaseEntity {

    private String name;

    @Nullable
    private String detail;

    @Column(name = "is_active")
    private boolean active;

    private String coverPath;
}
