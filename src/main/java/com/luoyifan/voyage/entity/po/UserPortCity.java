package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserPortCity extends BaseData {
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "port_id", nullable = false, updatable = false)
    @OneToOne
    private Port port;
    private String name;
    private String description;
    private Integer hp;
    private Integer money;
    private Integer taxRate;
    private Integer price;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "userPortCity", orphanRemoval = true)
    private List<UserPortCityGoods> userPortCityGoodsList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "userPortCity", orphanRemoval = true)
    private List<UserPortCityItem> userPortCityItemList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "userPortCity", orphanRemoval = true)
    private List<UserPortCityShip> userPortCityShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "userPortCity", orphanRemoval = true)
    private List<UserPortCityDeposit> userPortCityDepositList;
}
