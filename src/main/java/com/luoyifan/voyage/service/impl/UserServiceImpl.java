package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.constants.TacticEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.constants.UserSortEnum;
import com.luoyifan.voyage.dao.PortRepository;
import com.luoyifan.voyage.dao.UserOperationRepository;
import com.luoyifan.voyage.dao.UserRepository;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.entity.dto.RankDTO;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserOperation;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.toolkit.UserAgentAnalyzer;
import com.luoyifan.voyage.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOperationRepository userOperationRepository;
    @Autowired
    private PortRepository portRepository;

    /**
     * 获取出生点
     *
     * @return
     */
    @Override
    public List<Port> listBirthplace() {
        return portRepository.findAll()
                .stream()
                .filter(port -> Boolean.TRUE.equals(port.getBirthplace()))
                .collect(Collectors.toList());
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param email
     * @param portId
     * @param attack
     * @param command
     * @param navigation
     */
    @Override
    public void register(String username,
                         String password,
                         String email,
                         Long portId,
                         Integer attack,
                         Integer command,
                         Integer navigation) {
        AssertUtils.nonBlank(username, "用户名不能为空");
        AssertUtils.isTrue(username.length() <= voyageProperty.getUsernameLengthLimit(), "用户名不能超过" + voyageProperty.getUsernameLengthLimit() + "字");
        AssertUtils.isTrue(username.matches("[A-Za-z][A-Za-z0-9]+"), "用户名必须由英文字母和数字组成,且首字为英文");
        AssertUtils.nonBlank(password, "密码不能为空");
        AssertUtils.nonNull(attack, "战斗力不能为空");
        AssertUtils.isTrue(attack > 0, "战斗力必须大于0");
        AssertUtils.nonNull(command, "指挥力不能为空");
        AssertUtils.isTrue(command > 0, "指挥力必须大于0");
        AssertUtils.nonNull(navigation, "航海力不能为空");
        AssertUtils.isTrue(navigation > 0, "航海力必须大于0");
        if ((attack + command + navigation) != voyageProperty.getUserInitCapacity()) {
            AssertUtils.fail("能力点数分配合计必须为" + voyageProperty.getUserInitCapacity());
        }
        if (userRepository.countByUsername(username) > 0) {
            AssertUtils.fail("用户名已被占用");
        }
        AssertUtils.nonNull(portId, "初始城市不能为空");
        Port port = portRepository.getOne(portId);
        AssertUtils.nonNull(port, "初始城市不能为空");

        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDescription("");
        if (email == null) {
            email = "";
        }
        user.setEmail(email);
        user.setMoney(voyageProperty.getUserInitMoney());
        user.setFood(0);
        user.setTactic(TacticEnum.WARLIKE);
        user.setSailor(0);
        user.setAttack(attack);
        user.setCommand(command);
        user.setNavigation(navigation);
        user.setTrade(0);
        user.setAdventure(0);
        user.setBattle(0);
        user.setTradeExp(0);
        user.setAdventureExp(0);
        user.setBattleExp(0);
        user.setLastAccessTime(now);
        user.setAction("");
        user.setArea(port.getArea());
        user.setPort(port);
        user.setPoint(PointEnum.PORT);
        user.setMoveTime(now.minusDays(1));
        user.setBattleTime(now.minusDays(1));
        userRepository.save(user);
    }

    /**
     * 获取
     */
    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getCurrent() {
        return getById(UserDetails.getCurrentIdOrThrow());
    }

    /**
     * 获取
     */
    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 获取可操作的港内地点
     */
    @Override
    public List<PointEnum> listPoint() {
        User user = getCurrent();
        if (user.isMoving()) {
            return new ArrayList<>();
        }
        if (user.getPort() == null) {
            return Arrays.asList(PointEnum.PORT, PointEnum.BATTLE);
        }
        return Arrays.asList(PointEnum.values());
    }

    /**
     * 更新描述
     */
    @Override
    public void updateDescription(String description) {
        User user = getCurrent();
        user.setDescription(description);
        save(user);
    }

    /**
     * 改变选择的界面
     */
    @Override
    public void changePoint(PointEnum point) {
        User user = getCurrent();
        if (user.getPoint().equals(point)) {
            return;
        }
        user.setPoint(point);
        save(user);
    }

    /**
     * 获取可用策略
     */
    @Override
    public List<TacticEnum> listTactic() {
        User user = getCurrent();
        return Arrays.stream(TacticEnum.values())
                .filter(tactics -> {
                    //船只大于1条不能投降
                    if (TacticEnum.SURRENDER.equals(tactics) && user.getUserShipList().size() > 1) {
                        return false;
                    }
                    //在公海上或在移动中时不能靠港
                    if (TacticEnum.MOORING.equals(tactics)) {
                        return user.getPort() != null && !user.isMoving();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * 改变策略
     */
    @Override
    public void changeTactic(TacticEnum tactic) {
        if (listTactic().contains(tactic)) {
            User user = getCurrent();
            user.setTactic(tactic);
            if (TacticEnum.MOORING.equals(tactic)) {
                user.setMoveTime(LocalDateTime.now().plusSeconds(voyageProperty.getMooringTime()));
            }
            if (TacticEnum.SURRENDER.equals(tactic)) {
                user.setAdventure(0);
                user.setTrade(0);
                user.setBattle(0);
            }
            save(user);
        }
    }

    /**
     * 保存
     *
     * @param user
     */
    @Override
    public void save(User user) {
        user.setLastAccessTime(LocalDateTime.now());
        userRepository.saveAndFlush(user);
    }

    /**
     * 保存操作日志
     */
    @Override
    public void saveOperation(Long userId, UserOperationEnum type, String description, UserAgentAnalyzer userAgentAnalyzer, String ip) {
        UserOperation operationLog = new UserOperation();
        operationLog.setUser(getById(userId));
        operationLog.setDescription(description);
        operationLog.setType(type);
        if (userAgentAnalyzer != null) {
            operationLog.setUserAgent(userAgentAnalyzer.getUserAgent());
            operationLog.setPlatformType(userAgentAnalyzer.getPlatformType());
            operationLog.setPlatformSeries(userAgentAnalyzer.getPlatformSeries());
            operationLog.setPlatformVersion(userAgentAnalyzer.getPlatformVersion());
            operationLog.setBrowserType(userAgentAnalyzer.getBrowserType());
            operationLog.setBrowserVersion(userAgentAnalyzer.getBrowserVersion());
        }
        operationLog.setIp(ip);
        userOperationRepository.save(operationLog);
    }

    /**
     * 保存操作日志,从当前访问中获取
     */
    @Override
    public void saveCurrentUserOperation(UserOperationEnum type, String description) {
        UserAgentAnalyzer userAgentAnalyzer = UserDetails.getUserAgentAnalyzer();
        AssertUtils.nonNull(userAgentAnalyzer, "未绑定网络访问信息");
        saveOperation(UserDetails.getCurrentIdOrThrow(), type, description, userAgentAnalyzer, UserDetails.getIp());
    }

    /**
     * 获取操作日志
     */
    @Override
    public List<UserOperation> listOperation() {
        return userOperationRepository.findByUserIdOrderByCreateTimeDesc(PageRequest.of(0, voyageProperty.getUserOperationDisplayLimit()), UserDetails.getCurrentIdOrThrow())
                .getContent();
    }

    @Override
    public Page<User> listPage(Integer page, UserSortEnum sort) {
        if (page == null) {
            page = 1;
        }
        Integer userListDisplayLimit = voyageProperty.getUserListDisplayLimit();
        if (sort == null) {
            return userRepository.findAll(PageRequest.of(page - 1, userListDisplayLimit, Sort.Direction.DESC, "createTime"));
        }
        if (UserSortEnum.SHIP.equals(sort)) {
            return userRepository.findByOrderByShipNum(PageRequest.of(page - 1, userListDisplayLimit));
        }
        String properties = "createTime";
        switch (sort) {
            case NAME:
                properties = "username";
                break;
            case MONEY:
                properties = "money";
                break;
            case ADVENTURE:
                properties = "adventure";
                break;
            case BATTLE:
                properties = "battle";
                break;
            case TRADE:
                properties = "trade";
                break;
            case AREA:
                properties = "area";
                break;
            default:
        }
        return userRepository.findAll(PageRequest.of(page - 1, userListDisplayLimit, Sort.Direction.DESC, properties));
    }

    @Override
    public void sendMail(Long uid, String message, Integer money) {
        User sender = getCurrent();
        User receiver = getById(uid);
        if (StringUtils.isNotBlank(message)) {
            saveCurrentUserOperation(UserOperationEnum.MAIL, "发送消息给 " + receiver.getUsername() + "：" + message);
            saveOperation(uid, UserOperationEnum.MAIL, "收到 " + sender.getUsername() + " 的消息：" + message, null, null);
        }
        if (money != null) {
            AssertUtils.isTrue(sender.getMoney() >= money, "金钱不足");
            sender.setMoney(sender.getMoney() - money);
            receiver.setMoney(receiver.getMoney() + money);
            save(sender);
            saveCurrentUserOperation(UserOperationEnum.MAIL, "援助 " + receiver.getUsername() + " " + money + " G");
            save(receiver);
            saveOperation(uid, UserOperationEnum.MAIL, "收到 " + sender.getUsername() + " 援助的 " + money + " G", null, null);
        }

    }

    @Override
    public RankDTO getRank() {
        RankDTO rankDTO = new RankDTO();
        List<User> adventureList = userRepository.findAll(PageRequest.of(0, 3, Sort.Direction.DESC, "adventure")).getContent();
        List<User> tradeList = userRepository.findAll(PageRequest.of(0, 3, Sort.Direction.DESC, "trade")).getContent();
        List<User> battleList = userRepository.findAll(PageRequest.of(0, 3, Sort.Direction.DESC, "battle")).getContent();
        List<User> moneyList = userRepository.findAll(PageRequest.of(0, 3, Sort.Direction.DESC, "money")).getContent();
        Integer rookie = voyageProperty.getRookie();
        User adventureRookie = userRepository.findAdventureRookie(rookie);
        User tradeRookie = userRepository.findByTradeRookie(rookie);
        User battleRookie = userRepository.findByBattleRookie(rookie);
        rankDTO.setAdventureList(adventureList);
        rankDTO.setTradeList(tradeList);
        rankDTO.setBattleList(battleList);
        rankDTO.setMoneyList(moneyList);
        rankDTO.setAdventureRookie(adventureRookie);
        rankDTO.setTradeRookie(tradeRookie);
        rankDTO.setBattleRookie(battleRookie);
        return rankDTO;
    }
}
