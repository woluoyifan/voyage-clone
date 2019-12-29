package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserAdventure extends BaseData {
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "adventure_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Adventure adventure;
    @Column(name = "is_active")
    private Boolean active;
    private Integer seq;
    private Integer completionTimes;
}
