package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.UserBattleLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface UserBattleLimitRepository extends JpaRepository<UserBattleLimit,Long> {
    List<UserBattleLimit> findByAttackerUserIdEquals(Long attackerUserId);
}
