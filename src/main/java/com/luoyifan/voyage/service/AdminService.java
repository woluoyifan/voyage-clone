package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.dto.CreateAdventureDTO;
import com.luoyifan.voyage.entity.po.Adventure;
import com.luoyifan.voyage.entity.po.Area;
import com.luoyifan.voyage.entity.po.Item;
import com.luoyifan.voyage.entity.po.Port;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface AdminService {
    List<Item> listItem();

    List<Port> listPort();

    List<Area> listArea();

    List<Adventure> listAdventure();

    Adventure getAdventure(Long adventureId);

    void createAdventure(CreateAdventureDTO createAdventureDTO);

    void deleteAdventure(Long adventureId);
}
