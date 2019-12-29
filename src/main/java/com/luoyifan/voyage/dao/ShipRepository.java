package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface ShipRepository extends JpaRepository<Ship,Long> {
}
