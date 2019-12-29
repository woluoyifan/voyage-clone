package com.luoyifan.voyage.entity.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author EvanLuo
  */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseData {
    @Id
    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "com.luoyifan.voyage.toolkit.JPAIdGenerator")
    private Long id;
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime modifiedTime;
    @CreatedDate
    private LocalDateTime createTime;

}
