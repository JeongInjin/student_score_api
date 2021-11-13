package com.freewheelin.student.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private String adminId = "ADMIN"; // 임시로 ADMIN 지정.

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedDate
    private LocalDateTime modifiedDate;
}
