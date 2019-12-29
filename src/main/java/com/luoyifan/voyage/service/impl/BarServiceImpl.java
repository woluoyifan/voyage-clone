package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.entity.po.Adventure;
import com.luoyifan.voyage.entity.po.AdventureDetail;
import com.luoyifan.voyage.entity.po.AreaAdventure;
import com.luoyifan.voyage.entity.po.BaseData;
import com.luoyifan.voyage.entity.po.Item;
import com.luoyifan.voyage.entity.po.PortAdventure;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserAdventure;
import com.luoyifan.voyage.entity.po.UserFriendship;
import com.luoyifan.voyage.entity.po.UserItem;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.service.BarService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.util.AssertUtils;
import com.luoyifan.voyage.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class BarServiceImpl implements BarService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;

    @Override
    public void friend() {
        User user = userService.getCurrent();
        List<UserFriendship> userFriendshipList = user.getUserFriendshipList();
        List<Long> friendIdList = userFriendshipList.stream()
                .map(UserFriendship::getFriend)
                .map(BaseData::getId)
                .collect(Collectors.toList());

        listBarGuest()
                .stream()
                .filter(barUser -> !friendIdList.contains(barUser.getId()))
                .forEach(barGuest -> {
                    UserFriendship userFriendship = new UserFriendship();
                    userFriendship.setUser(user);
                    userFriendship.setFriend(barGuest);
                    userFriendshipList.add(userFriendship);
                });
        userService.save(user);
    }

    @Override
    public Integer getEmployPrice() {
        User user = userService.getCurrent();
        return CalculateUtils.employPrice(voyageProperty.getEmployPrice(), user.getBattleLevel());
    }

    @Override
    public void employ(Integer quantity) {
        User user = userService.getCurrent();
        Integer totalPrice = getEmployPrice() * quantity;
        Integer balance = user.getMoney() - totalPrice;
        AssertUtils.isTrue(balance >= 0, "余额不足");
        AssertUtils.isTrue(user.getRemainVolume() >= quantity, "剩余空间不足");
        user.setSailor(user.getSailor() + quantity);
        user.setMoney(balance);
        userService.save(user);
        String msg = "花费 " + totalPrice + "G 雇佣" + quantity + "名水手";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.SAILOR, msg);
    }

    @Override
    public void fire(Integer quantity) {
        User user = userService.getCurrent();
        int realQuantity = quantity;
        if (user.getSailor() < quantity) {
            realQuantity = user.getSailor();
        }
        user.setSailor(user.getSailor() - realQuantity);
        userService.save(user);
        String msg = "解雇" + realQuantity + "名水手";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.SAILOR, msg);
    }

    @Override
    public int getFoodPrice() {
        return voyageProperty.getFoodPrice();
    }

    @Override
    public void purchaseFood(Integer quantity) {
        User user = userService.getCurrent();
        Integer totalPrice = getFoodPrice() * quantity;
        Integer balance = user.getMoney() - totalPrice;
        AssertUtils.isTrue(balance >= 0, "余额不足");
        AssertUtils.isTrue(user.getRemainVolume() >= quantity, "剩余空间不足");
        user.setFood(user.getFood() + quantity);
        user.setMoney(balance);
        userService.save(user);
        String msg = "花费 " + totalPrice + "G 购入" + quantity + "单位食物";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.SAILOR, msg);
    }

    @Override
    public void throwFood(Integer quantity) {
        User user = userService.getCurrent();
        if (user.getFood() <= quantity) {
            user.setFood(0);
        } else {
            user.setFood(user.getFood() - quantity);
        }
        userService.save(user);
        String msg = "丢弃" + quantity + "单位食物";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.SAILOR, msg);
    }

    @Override
    public List<User> listBarGuest() {
        User user = userService.getCurrent();
        return user.getPort()
                .getUserList()
                .stream()
                .filter(u -> u.getPoint().equals(PointEnum.BAR))
                .filter(portUser -> !portUser.getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Adventure> listAdventure() {
        User user = userService.getCurrent();
        return Stream.concat(user.getArea()
                        .getAreaAdventureList()
                        .stream()
                        .map(AreaAdventure::getAdventure),
                user.getPort()
                        .getPortAdventureList()
                        .stream()
                        .map(PortAdventure::getAdventure))
                .collect(Collectors.toList());
    }

    @Override
    public void triggerAdventure() {
        User user = userService.getCurrent();
        user.getUserAdventureList()
                .stream()
                .filter(UserAdventure::getActive)
                .findFirst()
                .ifPresent(userAdventure -> {
                    Integer nextSeq = userAdventure.getSeq() + 1;
                    Adventure adventure = userAdventure.getAdventure();
                    List<AdventureDetail> adventureDetailList = adventure.getAdventureDetailList();
                    adventureDetailList.stream()
                            //顺序、港内位置、港口、海域吻合
                            .filter(adventureDetail -> adventureDetail.getSeq().equals(nextSeq))
                            .filter(adventureDetail -> adventureDetail.getPoint().equals(user.getPoint()))
                            .filter(adventureDetail -> adventureDetail.getPort() != null ?
                                    adventureDetail.getPort().getId().equals(user.getPort().getId()) : adventureDetail.getArea().getId().equals(user.getArea().getId()))
                            .findFirst()
                            .ifPresent(adventureDetail -> {
                                userAdventure.setSeq(nextSeq);
                                if (adventureDetail.getSeq() < adventureDetailList.size()) {
                                    continueAdventure(user, adventureDetail);
                                } else {
                                    finishAdventure(user, userAdventure);
                                }
                            });
                });
    }

    private void continueAdventure(User user, AdventureDetail adventureDetail) {
        //进行下一步
        UserDetails.setMsg(adventureDetail.getDescription());
        userService.saveCurrentUserOperation(UserOperationEnum.ADVENTURE, adventureDetail.getDescription());
        userService.save(user);
    }

    private void finishAdventure(User user, UserAdventure userAdventure) {
        Adventure adventure = userAdventure.getAdventure();
        List<AdventureDetail> adventureDetailList = adventure.getAdventureDetailList();
        //完成任务
        String msg = adventureDetailList.get(adventureDetailList.size() - 1).getDescription() + "\n完成探索！";
        //获取物品
        Item adventureItem = adventure.getItem();
        if (adventureItem != null) {
            //不能重复获取物品
            boolean isNotExistItem = user.getUserItemList()
                    .stream()
                    .map(UserItem::getItem)
                    .noneMatch(item -> item.getId().equals(adventureItem.getId()));
            if (isNotExistItem) {
                UserItem userItem = new UserItem();
                userItem.setUser(user);
                userItem.setItem(adventureItem);
                user.getUserItemList().add(userItem);
                msg += "\n获得了 " + adventureItem.getName() + " ！";
            }
        }
        userAdventure.setActive(false);
        userAdventure.setCompletionTimes(userAdventure.getCompletionTimes() + 1);
        int exp = CalculateUtils.adventureExp(adventureDetailList.size());
        user.setAdventureExp(user.getAdventureExp() + exp);
        user.setAdventure(user.getAdventure() + exp);

        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.ADVENTURE, msg);
        userService.save(user);
    }

    @Override
    public void startAdventure(Long adventureId) {
        User user = userService.getCurrent();
        listAdventure().stream()
                .filter(adventure -> adventure.getId().equals(adventureId))
                .findFirst()
                .ifPresent(adventure -> {
                    List<UserAdventure> userAdventureList = user.getUserAdventureList();
                    //原有的冒险任务进度失效
                    userAdventureList.stream()
                            .filter(userAdventure -> !userAdventure.getActive())
                            .forEach(userAdventure -> userAdventure.setActive(false));
                    UserAdventure targetUserAdventure = userAdventureList.stream()
                            .filter(userAdventure -> userAdventure.getAdventure().getId().equals(adventureId))
                            .findFirst()
                            .orElseGet(() -> {
                                UserAdventure userAdventure = new UserAdventure();
                                userAdventure.setUser(user);
                                userAdventure.setAdventure(adventure);
                                userAdventureList.add(userAdventure);
                                return userAdventure;
                            });
                    targetUserAdventure.setActive(true);
                    targetUserAdventure.setSeq(0);
                    if (targetUserAdventure.getCompletionTimes() == null) {
                        targetUserAdventure.setCompletionTimes(0);
                    }
                    UserDetails.setMsg(adventure.getDescription());
                    userService.saveCurrentUserOperation(UserOperationEnum.ADVENTURE, adventure.getDescription());
                    userService.save(user);
                });

    }

}
