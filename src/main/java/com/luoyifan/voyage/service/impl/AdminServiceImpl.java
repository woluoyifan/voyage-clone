package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.dao.AdventureRepository;
import com.luoyifan.voyage.dao.AreaRepository;
import com.luoyifan.voyage.dao.ItemRepository;
import com.luoyifan.voyage.dao.PortRepository;
import com.luoyifan.voyage.entity.dto.CreateAdventureDTO;
import com.luoyifan.voyage.entity.po.Adventure;
import com.luoyifan.voyage.entity.po.AdventureDetail;
import com.luoyifan.voyage.entity.po.Area;
import com.luoyifan.voyage.entity.po.AreaAdventure;
import com.luoyifan.voyage.entity.po.Item;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.PortAdventure;
import com.luoyifan.voyage.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private PortRepository portRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AdventureRepository adventureRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> listItem() {
        return itemRepository.findAll();
    }

    @Override
    public List<Port> listPort() {
        return portRepository.findAll();
    }

    @Override
    public List<Area> listArea() {
        return areaRepository.findAll();
    }

    @Override
    public List<Adventure> listAdventure() {
        return adventureRepository.findAll();
    }

    @Override
    public Adventure getAdventure(Long adventureId){
        return adventureRepository.getOne(adventureId);
    }

    @Override
    public void createAdventure(CreateAdventureDTO createAdventureDTO) {
        String name = createAdventureDTO.getName();
        String description = createAdventureDTO.getDescription();
        Integer price = createAdventureDTO.getPrice();
        Long itemId = createAdventureDTO.getItemId();
        List<Long> areaIdList = createAdventureDTO.getAreaIdList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> portIdList = createAdventureDTO.getPortIdList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> detailAreaIdList = createAdventureDTO.getDetailAreaIdList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> detailPortIdList = createAdventureDTO.getDetailPortIdList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<PointEnum> detailPointList = createAdventureDTO.getDetailPointList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> detailDescriptionList = createAdventureDTO.getDetailDescriptionList().stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (StringUtils.isBlank(name) || price == null || price < 0) {
            return;
        }
        List<Adventure> adventureList = listAdventure();
        if (adventureList.stream()
                .anyMatch(adventure -> adventure.getName().equals(name))) {
            return;
        }
        Adventure adventure = new Adventure();
        List<AdventureDetail> detailList = IntStream.range(0, detailDescriptionList.size())
                .filter(i -> detailAreaIdList.get(i) > 0 || detailPortIdList.get(i) > 0)
                .mapToObj(i -> {
                    AdventureDetail detail = new AdventureDetail();
                    detail.setAdventure(adventure);
                    if (detailAreaIdList.get(i) >0 ) {
                        areaRepository.findById(detailAreaIdList.get(i))
                                .ifPresent(detail::setArea);
                    }
                    if (detailPortIdList.get(i) >0 ) {
                        portRepository.findById(detailPortIdList.get(i))
                                .ifPresent(detail::setPort);
                    }
                    detail.setPoint(detailPointList.get(i));
                    detail.setSeq(i + 1);
                    detail.setDescription(detailDescriptionList.get(i));
                    return detail;
                })
                .collect(Collectors.toList());
        List<AreaAdventure> areaAdventureList = areaIdList.stream()
                .map(id -> areaRepository.getOne(id))
                .filter(Objects::nonNull)
                .map(area -> {
                    AreaAdventure areaAdventure = new AreaAdventure();
                    areaAdventure.setArea(area);
                    areaAdventure.setAdventure(adventure);
                    return areaAdventure;
                })
                .collect(Collectors.toList());
        List<PortAdventure> portAdventureList = portIdList.stream()
                .map(id -> portRepository.getOne(id))
                .filter(Objects::nonNull)
                .map(port -> {
                    PortAdventure portAdventure = new PortAdventure();
                    portAdventure.setPort(port);
                    portAdventure.setAdventure(adventure);
                    return portAdventure;
                })
                .collect(Collectors.toList());
        adventure.setName(name);
        adventure.setDescription(description);
        adventure.setPrice(price);
        adventure.setAdventureDetailList(detailList);
        if (itemId != null) {
            itemRepository.findById(itemId)
                    .ifPresent(adventure::setItem);
        }
        adventure.setPortAdventureList(portAdventureList);
        adventure.setAreaAdventureList(areaAdventureList);
        adventureRepository.save(adventure);
    }

    @Override
    public void deleteAdventure(Long adventureId) {
        //TODO 关联删除
        adventureRepository.deleteById(adventureId);
    }
}
