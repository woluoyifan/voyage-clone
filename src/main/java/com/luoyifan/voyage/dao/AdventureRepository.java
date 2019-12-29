package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface AdventureRepository extends JpaRepository<Adventure,Long> {
}
