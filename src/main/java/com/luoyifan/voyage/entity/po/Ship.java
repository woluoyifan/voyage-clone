package com.luoyifan.voyage.entity.po;

import com.luoyifan.voyage.constants.ShipTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ship extends BaseData {
    @Enumerated(EnumType.STRING)
    private ShipTypeEnum type;
    private String name;
    private Integer volume;
    private Integer hp;
    private BigDecimal speed;
    private Integer price;
    private String imgPath;
    @Column(name = "is_secret")
    private Boolean secret;
    private Integer secretMoney;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "ship", orphanRemoval = true)
    private List<UserShip> userShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "ship", orphanRemoval = true)
    private List<AreaShip> areaShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "ship", orphanRemoval = true)
    private List<PortShip> portShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "ship", orphanRemoval = true)
    private List<UserPortCityShip> userPortCityShipList;

    @Transient
    private Integer purchasePrice;
    @Transient
    private Integer sellPrice;

}
