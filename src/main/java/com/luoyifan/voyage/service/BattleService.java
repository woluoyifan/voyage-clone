package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.po.User;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface BattleService {
    List<User> listOpponent();

    void battle(Long opponentId);
}
