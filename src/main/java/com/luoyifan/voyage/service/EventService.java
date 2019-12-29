package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.po.EventRecord;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface EventService {
    List<EventRecord> list();

    void create(String msg);
}
