package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.dao.EventRepository;
import com.luoyifan.voyage.entity.po.EventRecord;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EvanLuo
  */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventRecord> list(){
        return eventRepository.findAll(PageRequest.of(0,voyageProperty.getEventRecordDisplayLimit(), Sort.Direction.DESC,"createTime")).getContent();
    }

    @Override
    public void create(String msg){
        EventRecord event = new EventRecord();
        event.setDescription(msg);
        eventRepository.save(event);
    }
}
