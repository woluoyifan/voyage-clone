package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
public class PortAdventure extends BaseData{
    @JoinColumn(name = "port_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Port port;
    @JoinColumn(name = "adventure_id",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Adventure adventure;
}
