package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface EventRepository extends JpaRepository<EventRecord,Long> {
}
