package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.dao.UserBattleLimitRepository;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserBattleLimit;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.BattleService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.toolkit.BattleContext;
import com.luoyifan.voyage.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class BattleServiceImpl implements BattleService {

    @Autowired
    private UserService userService;
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserBattleLimitRepository userBattleLimitRepository;

    /**
     * 查询可发动战斗的用户,即当前海域或当前港口的用户
     */
    @Override
    public List<User> listOpponent() {
        User user = userService.getCurrent();
        if (user.isMoving()) {
            return new ArrayList<>();
        }
        Predicate<User> opponentFilter = battleUser -> !battleUser.getId().equals(user.getId());
        if (user.getPort() == null) {
            return user.getArea()
                    .getUserList()
                    .stream()
                    .filter(opponentFilter)
                    .collect(Collectors.toList());
        }
        return user.getPort()
                .getUserList()
                .stream()
                .filter(opponentFilter)
                .collect(Collectors.toList());
    }

    @Override
    public void battle(Long opponentId) {
        listOpponent()
                .stream()
                .filter(opponent -> opponent.getId().equals(opponentId))
                .findFirst()
                .ifPresent(opponent -> {
                    User me = userService.getCurrent();

                    checkBattleLimit(me, opponent);

                    BattleContext battleExecutor = new BattleContext(me, opponent, voyageProperty.getShipNumLimit());
                    battleExecutor.battle();
                    String msg = battleExecutor.getMsg();

                    UserBattleLimit userBattleLimit = new UserBattleLimit();
                    userBattleLimit.setAttackerUserId(me.getId());
                    userBattleLimit.setDefenderUserId(opponent.getId());
                    userBattleLimitRepository.save(userBattleLimit);

                    me.setBattleTime(LocalDateTime.now());

                    UserDetails.setMsg(msg);
                    userService.save(me);
                    userService.saveCurrentUserOperation(UserOperationEnum.BATTLE, msg);
                    userService.save(opponent);
                    userService.saveOperation(opponentId, UserOperationEnum.BATTLE, msg, null, null);
                });
    }

    private void checkBattleLimit(User me, User opponent) {
        LocalDateTime now = LocalDateTime.now();
        Integer sameOpponentLimit = voyageProperty.getSameOpponentLimit();
        Long opponentId = opponent.getId();
        List<UserBattleLimit> userBattleLimitList = userBattleLimitRepository.findByAttackerUserIdEquals(me.getId());
        boolean sameOpponentValidPass = userBattleLimitList.stream()
                .filter(userBattleLimit -> {
                    //删除超过同一对手攻击间隔的记录
                    if (userBattleLimit.getCreateTime().until(now, ChronoUnit.SECONDS) > sameOpponentLimit) {
                        userBattleLimitRepository.deleteById(userBattleLimit.getId());
                        return false;
                    }
                    return true;
                })
                .noneMatch(userBattleLimit -> userBattleLimit.getDefenderUserId().equals(opponentId));
        AssertUtils.isTrue(sameOpponentValidPass, "短时间内不能再对 " + opponent.getUsername() + " 发动攻击！");

        long combatSeconds = me.getBattleTime().until(now, ChronoUnit.SECONDS);
        Integer combatLimit = voyageProperty.getCombatLimit();
        AssertUtils.isTrue(combatSeconds > combatLimit, "战斗间隔必须大于" + combatLimit / 60 + "分钟");
    }


}
