package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.ShipTypeEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.entity.po.AreaShip;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.PortShip;
import com.luoyifan.voyage.entity.po.Ship;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserShip;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.service.YardService;
import com.luoyifan.voyage.util.AssertUtils;
import com.luoyifan.voyage.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class YardServiceImpl implements YardService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;

    @Override
    public List<Ship> listPurchasableShip() {
        User user = userService.getCurrent();
        Port port = user.getPort();
        if (port == null) {
            return new ArrayList<>();
        }
        return Stream.concat(user.getArea()
                        .getAreaShipList()
                        .stream()
                        .map(AreaShip::getShip),
                port.getPortShipList()
                        .stream()
                        .map(PortShip::getShip))
                .peek(ship -> {
                    //强化价格随能力提升而提升
                    if (ShipTypeEnum.SHIP.equals(ship.getType())) {
                        ship.setPurchasePrice(ship.getPrice());
                    } else {
                        ship.setPurchasePrice(CalculateUtils.abilityPriceAdjustment(ship.getPrice(), user.getAttack(), user.getCommand(), user.getNavigation()));
                    }
                })
                .sorted(Comparator.comparing(Ship::getType).thenComparing(Ship::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public void purchaseShip( Long shipId) {
        listPurchasableShip()
                .stream()
                .filter(ship -> ship.getId().equals(shipId))
                .findFirst()
                .ifPresent(ship -> {
                    User user = userService.getCurrent();
                    ShipTypeEnum shipType = ship.getType();
                    Integer balance = user.getMoney() - ship.getPurchasePrice();
                    AssertUtils.isTrue(balance >= 0, "余额不足");
                    //校验具体实现
                    String msg = "以 " + ship.getPurchasePrice() + "G ";
                    switch (shipType) {
                        case SHIP:
                            AssertUtils.isTrue(user.getUserShipList().size() < voyageProperty.getShipNumLimit(), "船只数量已满，无法再购买");
                            UserShip userShip = new UserShip();
                            userShip.setShip(ship);
                            userShip.setVolume(ship.getVolume());
                            userShip.setHp(ship.getHp());
                            userShip.setSpeed(ship.getSpeed());
                            user.addUserShip(userShip);
                            msg += "买入 " + ship.getName();
                            break;
                        case ATTACK:
                            AssertUtils.isTrue(user.getAttack() < voyageProperty.getAttackLimit(), "已至极限，无法再强化");
                            user.setAttack(user.getAttack() + ship.getVolume());
                            msg += "武装强化了 " + ship.getVolume();
                            break;
                        case COMMAND:
                            AssertUtils.isTrue(user.getCommand() < voyageProperty.getCommandLimit(), "已至极限，无法再强化");
                            user.setCommand(user.getCommand() + ship.getVolume());
                            msg += "指挥力提高了 " + ship.getVolume();
                            break;
                        case NAVIGATION:
                            AssertUtils.isTrue(user.getNavigation() < voyageProperty.getNavigationLimit(), "已至极限，无法再强化");
                            user.setNavigation(user.getNavigation() + ship.getVolume());
                            msg += "航海力提高了 " + ship.getVolume();
                            break;
                        default:
                    }
                    user.setMoney(balance);
                    userService.save(user);
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    @Override
    public List<UserShip> listSaleableShip() {
        User user = userService.getCurrent();
        if(user.getPort() == null){
            return  new ArrayList<>();
        }
        List<UserShip> userShipList = user.getUserShipList();
        userShipList.forEach(userShip -> {
            Ship ship = userShip.getShip();
            ship.setSellPrice(CalculateUtils.sellShipPrice(ship.getPrice()));
        });
        return userShipList;
    }

    @Override
    public void sellShip( Long userShipId) {
        listSaleableShip()
                .stream()
                .filter(userShip -> userShip.getId().equals(userShipId))
                .findFirst()
                .ifPresent(userShip -> {
                    User user = userShip.getUser();
                    Ship ship = userShip.getShip();
                    AssertUtils.isTrue(user.getRemainVolume() >= ship.getVolume(), "剩余容积不足");

                    user.setMoney(user.getMoney() + ship.getSellPrice());
                    user.getUserShipList().remove(userShip);
                    userService.save(user);

                    String msg = "以 " + ship.getSellPrice() + "G 卖出 " + ship.getName();
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    @Override
    public List<UserShip> listRepairableShip() {
        User user = userService.getCurrent();
        if (user.getPort() == null) {
            return new ArrayList<>();
        }
        List<UserShip> userShipList = user.getUserShipList();
        return userShipList.stream()
                .filter(userShip -> !Objects.equals(userShip.getHp(), userShip.getRepairedHp()))
                .collect(Collectors.toList());
    }

    @Override
    public void repairShip( Long userShipId) {
        listRepairableShip()
                .stream()
                .filter(userShip -> userShip.getId().equals(userShipId))
                .findFirst()
                .ifPresent(userShip -> {
                    User user = userShip.getUser();
                    Ship ship = userShip.getShip();
                    if (userShip.getHp() >= ship.getHp()) {
                        return;
                    }
                    int price = userShip.getRepairPrice();
                    int balance = user.getMoney() - price;
                    AssertUtils.isTrue(balance >= 0, "资金不足");

                    userShip.setHp(userShip.getRepairedHp());
                    userShip.setVolume(userShip.getRepairedVolume());
                    user.setMoney(balance);
                    userService.save(user);

                    String msg = "花费 " + price + "G 修理 " + ship.getName();
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });

    }
}
