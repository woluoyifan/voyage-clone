package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Goods extends BaseData {
    private String name;
    private Integer price;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "goods", orphanRemoval = true)
    private List<PortGoods> portGoodsList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "goods", orphanRemoval = true)
    private List<UserGoods> userGoodsList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "goods", orphanRemoval = true)
    private List<UserPortCityGoods> userPortCityGoodsList;
}
