package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.dao.AreaRepository;
import com.luoyifan.voyage.dao.PortRepository;
import com.luoyifan.voyage.entity.dto.DestinationDTO;
import com.luoyifan.voyage.entity.po.Area;
import com.luoyifan.voyage.entity.po.Item;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserItem;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.PortService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.util.AssertUtils;
import com.luoyifan.voyage.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class PortServiceImpl implements PortService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;
    @Autowired
    private PortRepository portRepository;
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<DestinationDTO<Port>> listDestinationPort() {
        User user = userService.getCurrent();
        Area currArea = user.getArea();
        Port currCity = user.getPort();
        return currArea.getPortList()
                .stream()
                .filter(port -> currCity == null || !currCity.getId().equals(port.getId()))
                .map(port -> toDestinationDTO(user, port.getX(), port.getY(), port))
                .collect(Collectors.toList());
    }

    @Override
    public List<DestinationDTO<Area>> listDestinationArea() {
        User user = userService.getCurrent();
        Area currArea = user.getArea();
        Port currCity = user.getPort();
        return areaRepository.findAll()
                .stream()
                .filter(area -> currCity != null || !currArea.getId().equals(area.getId()))
                .map(area -> toDestinationDTO(user, area.getX(), area.getY(), area))
                .collect(Collectors.toList());
    }

    @Override
    public void moveToPort( Long portId) {
        User user = userService.getCurrent();
        AssertUtils.isTrue(user.getUserShipList().size() > 0, "没有船不能出航");
        if(user.isMoving()){
            return;
        }
        listDestinationPort()
                .stream()
                .filter(destinationDTO -> destinationDTO.getTarget().getId().equals(portId))
                .findFirst()
                .ifPresent(destinationDTO -> {
                    Integer remainFood = user.getFood() - destinationDTO.getFood();
                    AssertUtils.isTrue(remainFood >= 0, "食物不足，还需要 "+(destinationDTO.getFood()-user.getFood())+" 单位");
                    user.setFood(remainFood);
                    user.setMoveTime(LocalDateTime.now().plusSeconds(destinationDTO.getTotalSecond()));
                    Port port = destinationDTO.getTarget();
                    //城市价格随人员移动而趋于平稳
                    port.setPriceIndex(new BigDecimal(CalculateUtils.convergePriceIndex(port.getPriceIndex().doubleValue())));
                    portRepository.save(port);
                    user.setPort(port);
                    userService.save(user);
                    userService.saveCurrentUserOperation(UserOperationEnum.MOVE, "移动至 " + port.getName());
                });
    }

    @Override
    public void moveToArea( Long areaId) {
        User user = userService.getCurrent();
        AssertUtils.isTrue(user.getUserShipList().size() > 0, "没有船不能出航");
        if(user.isMoving()){
            return;
        }
        listDestinationArea()
                .stream()
                .filter(destinationDTO -> destinationDTO.getTarget().getId().equals(areaId))
                .findFirst()
                .ifPresent(destinationDTO -> {
                    Integer remainFood = user.getFood() - destinationDTO.getFood();
                    AssertUtils.isTrue(remainFood >= 0, "食物不足");
                    user.setFood(remainFood);
                    user.setMoveTime(LocalDateTime.now().plusSeconds(destinationDTO.getTotalSecond()));
                    user.setArea(destinationDTO.getTarget());
                    user.setPort(null);
                    userService.save(user);
                    userService.saveCurrentUserOperation(UserOperationEnum.MOVE, "移动至 " + destinationDTO.getTarget().getName());
                });
    }

    private <T> DestinationDTO<T> toDestinationDTO(User user, Integer x, Integer y, T target) {
        Integer totalSecond;
        Port currCity = user.getPort();
        if (currCity == null) {
            Area currArea = user.getArea();
            totalSecond = CalculateUtils.moveTime(currArea.getX(), currArea.getY(), x, y, user.getVector(), voyageProperty.getTimeScale());
        } else {
            totalSecond = CalculateUtils.moveTime(currCity.getX(), currCity.getY(), x, y, user.getVector(), voyageProperty.getTimeScale());
        }
        DestinationDTO<T> destinationDTO = new DestinationDTO<>();
        destinationDTO.setTarget(target);
        destinationDTO.setTotalSecond(totalSecond);
        LocalTime targetTime = LocalTime.ofSecondOfDay(totalSecond);
        destinationDTO.setHour(targetTime.getHour());
        destinationDTO.setMinute(targetTime.getMinute());
        destinationDTO.setSecond(targetTime.getSecond());
        int food = CalculateUtils.food(totalSecond, user.getSailor(), voyageProperty.getSailorWaste());
        boolean saveFood = user.getUserItemList().stream().map(UserItem::getItem).anyMatch(Item::getSaveFood);
        if (saveFood) {
            food = (int) (food * voyageProperty.getSaveFoodRate());
        }
        destinationDTO.setFood(food);
        return destinationDTO;
    }
}
