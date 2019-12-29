package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.dto.DestinationDTO;
import com.luoyifan.voyage.entity.po.Area;
import com.luoyifan.voyage.entity.po.Port;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface PortService {
    List<DestinationDTO<Port>> listDestinationPort();

    List<DestinationDTO<Area>> listDestinationArea();

    void moveToPort(Long portId);

    void moveToArea(Long areaId);
}
