package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
