package com.buddy.chat.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class AbstractEntity {

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modifiedAt;


}