package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Item extends BaseData{
    private String name;
    @Column(name = "is_save_food")
    private Boolean saveFood;
    @Column(name = "is_attack_up")
    private Boolean attackUp;
    @Column(name = "is_avoid_attack")
    private Boolean avoidAttack;
    @Column(name = "is_shield")
    private Boolean shield;
    @Column(name = "is_item_protect")
    private Boolean itemProtect;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "item", orphanRemoval = true)
    private List<UserItem> userItemList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "item", orphanRemoval = true)
    private List<UserPortCityItem> userPortCityItemList;
}
