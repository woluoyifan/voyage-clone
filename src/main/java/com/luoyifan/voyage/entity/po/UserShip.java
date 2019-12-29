package com.luoyifan.voyage.entity.po;

import com.luoyifan.voyage.util.CalculateUtils;
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
public class UserShip extends BaseData {
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "ship_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ship ship;
    private Integer volume;
    private Integer hp;
    private BigDecimal speed;

    public int getRepairedVolume() {
        return CalculateUtils.volumeAfterShipRepair(getVolume());
    }

    public int getRepairedHp() {
        Ship ship = getShip();
        return ship == null ? CalculateUtils.shipVolumeToHp(getRepairedVolume()) : ship.getHp();
    }

    public int getRepairPrice() {
        Ship ship = getShip();
        int repairPrice = ship == null ? CalculateUtils.sellShipPrice(getRepairedVolume()) : ship.getPrice();
        return CalculateUtils.repairPrice(repairPrice, getHp(), getRepairedHp());
    }
}
