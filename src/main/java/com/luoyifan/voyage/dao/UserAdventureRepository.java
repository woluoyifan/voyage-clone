package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.UserAdventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author EvanLuo
  */
@Repository
public interface UserAdventureRepository extends JpaRepository<UserAdventure,Long> {
    Optional<UserAdventure> findOneByUserIdAndAdventureIdAndActiveTrue(Long userId, Long adventureId);
}
