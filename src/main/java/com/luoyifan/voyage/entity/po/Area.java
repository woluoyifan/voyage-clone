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
public class Area extends BaseData {
    private String name;
    private Integer x;
    private Integer y;
    private String code;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "area", orphanRemoval = true)
    private List<Port> portList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "area", orphanRemoval = true)
    private List<AreaShip> areaShipList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "area", orphanRemoval = true)
    private List<AreaAdventure> areaAdventureList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    private List<User> userList;
}
