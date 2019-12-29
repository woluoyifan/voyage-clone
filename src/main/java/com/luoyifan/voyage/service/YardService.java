package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.po.Ship;
import com.luoyifan.voyage.entity.po.UserShip;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface YardService {
    List<Ship> listPurchasableShip();

    void purchaseShip(Long shipId);

    List<UserShip> listSaleableShip();

    void sellShip(Long userShipId);

    List<UserShip> listRepairableShip();

    void repairShip(Long userShipId);
}
