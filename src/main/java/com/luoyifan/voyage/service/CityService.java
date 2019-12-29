package com.luoyifan.voyage.service;

import com.luoyifan.voyage.constants.CityOperationEnum;
import com.luoyifan.voyage.entity.po.Ship;
import com.luoyifan.voyage.entity.po.UserPortCityGoods;
import com.luoyifan.voyage.entity.po.UserPortCityItem;
import com.luoyifan.voyage.entity.po.UserPortCityShip;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface CityService {
    int getPriceOfCreateCity();

    List<CityOperationEnum> listCityOperation();

    void createCity(String name);

    void storeGoodsToCity(Long userGoodsId, Integer quantity, Integer price);

    void storeShipToCity(Long userShipId, Integer price);

    void storeItemToCity(Long userItemId, Integer price);

    void updateCityName(String name);

    void updateCityDescription(String description);

    void updateCityTaxRate(Integer rate);

    int getRepairCityCost();

    int getCityMaxHp();

    void repairCity(Integer hp);

    void withdrawalCityMoney(Integer money);

    void sellCity(Integer price);

    List<UserPortCityGoods> listPurchasableGoods();

    List<UserPortCityShip> listPurchasableShip();

    void purchaseShip(Long userPortCityShipId);

    List<Ship> listPurchasableSecretShip();

    void purchaseSecrtShip(Long shipId);

    List<UserPortCityItem> listPurchasableItem();

    void purchaseItem(Long userPortCityItemId);

    void purchaseGoods(Long userPortCityGoodsId, int quantity);

    int getCurrentPortDepositAmount();

    void deposit(Integer amount);

    void withdraw(Integer amount);

    void armyAttack();

    void moneyAttack();

    int getCityMoneyAttackCost();

    int getCityMoneyAttackDamage();
}
