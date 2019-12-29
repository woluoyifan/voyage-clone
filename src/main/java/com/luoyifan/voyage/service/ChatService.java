package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.po.ChatRecord;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface ChatService {
    List<ChatRecord> list();

    void create(String content);
}
