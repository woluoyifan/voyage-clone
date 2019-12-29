package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
}
