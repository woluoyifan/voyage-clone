package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserPortCityShip extends BaseData{
    @JoinColumn(name = "user_port_city_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPortCity userPortCity;
    @JoinColumn(name = "ship_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ship ship;
    private Integer volume;
    private Integer hp;
    private BigDecimal speed;
    private Integer price;
}
