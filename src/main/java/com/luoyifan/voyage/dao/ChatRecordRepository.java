package com.luoyifan.voyage.dao;

import com.luoyifan.voyage.entity.po.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author EvanLuo
  */
@Repository
public interface ChatRecordRepository extends JpaRepository<ChatRecord,Long> {
}
