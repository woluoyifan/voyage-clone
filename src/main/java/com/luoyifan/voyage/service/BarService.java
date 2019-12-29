package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.po.Adventure;
import com.luoyifan.voyage.entity.po.User;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface BarService {
    void friend();

    Integer getEmployPrice();

    void employ(Integer quantity);

    void fire(Integer quantity);

    int getFoodPrice();

    void purchaseFood(Integer quantity);

    void throwFood(Integer quantity);

    List<User> listBarGuest();

    List<Adventure> listAdventure();

    void triggerAdventure();

    void startAdventure(Long adventureId);
}
