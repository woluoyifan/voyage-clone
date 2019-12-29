package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author EvanLuo
  */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    long countByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM user AS u ORDER BY (SELECT count(1) FROM user_ship AS us WHERE us.user_id = u.id) DESC",nativeQuery = true)
    Page<User> findByOrderByShipNum(Pageable page);

    @Query(value = "SELECT * FROM (SELECT * FROM user ORDER BY create_time DESC LIMIT :rookie ) AS u ORDER BY adventure DESC LIMIT 1",nativeQuery = true)
    User findAdventureRookie(@Param("rookie") Integer rookie);

    @Query(value = "SELECT * FROM (SELECT * FROM user ORDER BY create_time DESC LIMIT :rookie ) AS u ORDER BY trade DESC LIMIT 1",nativeQuery = true)
    User findByTradeRookie(@Param("rookie")Integer rookie);

    @Query(value = "SELECT * FROM (SELECT * FROM user ORDER BY create_time DESC LIMIT :rookie ) AS u ORDER BY battle DESC LIMIT 1",nativeQuery = true)
    User findByBattleRookie(@Param("rookie")Integer rookie);
}
