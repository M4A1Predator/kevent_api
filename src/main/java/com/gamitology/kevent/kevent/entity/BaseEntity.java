package com.gamitology.kevent.kevent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = "GMT+07:00")
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = "GMT+07:00")
    @JsonFormat(pattern = DateFormatConstant.ISOString)
    private Date modifiedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
