package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface GoodsRepository extends JpaRepository<Goods,Long> {
}
