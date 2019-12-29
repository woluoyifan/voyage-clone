package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface PortRepository extends JpaRepository<Port,Long> {
}
