package com.gamitology.kevent.kevent.entity;

import io.micrometer.core.lang.Nullable;

import javax.persistence.Entity;

@Entity
public class Artist extends BaseEntity {

    private String name;

    @Nullable
    private String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
