package com.luoyifan.voyage.entity.po;

import com.luoyifan.voyage.constants.PointEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdventureDetail extends BaseData {
    @JoinColumn(name = "adventure_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Adventure adventure;
    @JoinColumn(name = "port_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Port port;
    @JoinColumn(name = "area_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;
    @Enumerated(EnumType.STRING)
    private PointEnum point;
    @Min(1)
    private Integer seq;
    private String description;
}
