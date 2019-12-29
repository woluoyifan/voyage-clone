package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Port extends BaseData {
    @JoinColumn(name = "area_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;
    private String name;
    private Integer x;
    private Integer y;
    private BigDecimal priceIndex;
    @Column(name = "is_birthplace")
    private Boolean birthplace;
    private String code;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "port", orphanRemoval = true)
    private List<PortGoods> portGoodsList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "port", orphanRemoval = true)
    private List<PortShip> portShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "port", orphanRemoval = true)
    private List<PortAdventure> portAdventureList;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "port", orphanRemoval = true)
    private UserPortCity userPortCity;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "port")
    private List<User> userList;

}
