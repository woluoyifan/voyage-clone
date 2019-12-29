package com.luoyifan.voyage.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.dao.PortRepository;
import com.luoyifan.voyage.entity.dto.GoodsDTO;
import com.luoyifan.voyage.entity.po.Goods;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.PortGoods;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserGoods;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.service.TradeService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.util.AssertUtils;
import com.luoyifan.voyage.util.CalculateUtils;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;
    @Autowired
    private PortRepository portRepository;

    private LoadingCache<String, Integer> trCache = Caffeine.newBuilder()
            .expireAfter(new Expiry<String, Integer>() {
                @Override
                public long expireAfterCreate(@NonNull String key, @NonNull Integer value, long currentTime) {
                    return expireAfterToday(currentTime);
                }

                @Override
                public long expireAfterUpdate(@NonNull String key, @NonNull Integer value, long currentTime, @NonNegative long currentDuration) {
                    return expireAfterToday(currentTime);
                }

                @Override
                public long expireAfterRead(@NonNull String key, @NonNull Integer value, long currentTime, @NonNegative long currentDuration) {
                    return Long.MAX_VALUE;
                }

                private long expireAfterToday(long currentTime) {
                    return LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli() - currentTime;
                }
            })
            .build(key -> CalculateUtils.tr(LocalDate.now(), voyageProperty.getPriceChangeCycle(), Integer.parseInt(key)));

    @Override
    public List<GoodsDTO> listPurchasableGoods() {
        User user = userService.getCurrent();
        Port port = user.getPort();
        if (port == null) {
            return new ArrayList<>();
        }
        return port.getPortGoodsList()
                .stream()
                .map(portGoods -> {
                    Goods goods = portGoods.getGoods();
                    GoodsDTO goodsDTO = new GoodsDTO();
                    goodsDTO.setId(goods.getId());
                    goodsDTO.setName(goods.getName());
                    goodsDTO.setPrice(CalculateUtils.purchasePrice(goods.getName(),
                            goods.getPrice(),
                            port.getPriceIndex().doubleValue(),
                            voyageProperty.getGoodsPriceChangeModule(),
                            trCache.get(user.getArea().getCode())));
                    return goodsDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void purchaseGoods(Long goodsId, Integer quantity) {
        User user = userService.getCurrent();
        Port port = user.getPort();
        if (port == null) {
            return;
        }
        port.getPortGoodsList()
                .stream()
                .map(PortGoods::getGoods)
                .filter(goods -> goods.getId().equals(goodsId))
                .findFirst()
                .ifPresent(goods -> {
                    int price = CalculateUtils.purchasePrice(goods.getName(),
                            goods.getPrice(),
                            port.getPriceIndex().doubleValue(),
                            voyageProperty.getGoodsPriceChangeModule(),
                            trCache.get(user.getArea().getCode()));
                    String msg = "";
                    if (RandomUtils.nextInt(0, 100) < user.getTradeLevel()) {
                        price = (int) (price * 0.95);
                        msg += "经过交涉，";
                    }
                    int totalPrice = price * quantity;
                    int balance = user.getMoney() - totalPrice;
                    AssertUtils.isTrue(balance >= 0, "余额不足");
                    AssertUtils.isTrue(user.getRemainVolume() >= quantity, "剩余空间不足");
                    UserGoods userGoods = user.getUserGoodsList()
                            .stream()
                            .filter(entity -> entity.getGoods().getId().equals(goodsId))
                            .findFirst()
                            .orElseGet(() -> {
                                UserGoods entity = new UserGoods();
                                entity.setUser(user);
                                entity.setGoods(goods);
                                entity.setQuantity(0);
                                user.getUserGoodsList().add(entity);
                                return entity;
                            });
                    userGoods.setQuantity(userGoods.getQuantity() + quantity);

                    user.setTrade(user.getTrade() + CalculateUtils.tradeChange(quantity, price, voyageProperty.getTradeChangeRate()));
                    user.setMoney(balance);
                    userService.save(user);

                    changePriceIndex(port, quantity, price, true);

                    msg += "以单价" + price + "G 买入" + goods.getName() + quantity + "个";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.GOODS, msg);
                });
    }

    @Override
    public List<GoodsDTO> listSaleableGoods() {
        User user = userService.getCurrent();
        Port port = user.getPort();
        if (port == null) {
            return new ArrayList<>();
        }
        return user.getUserGoodsList()
                .stream()
                .map(userGoods -> {
                    Goods goods = port.getPortGoodsList()
                            .stream()
                            .map(PortGoods::getGoods)
                            .filter(g -> g.getId().equals(userGoods.getGoods().getId()))
                            .findFirst()
                            .orElse(userGoods.getGoods());

                    GoodsDTO goodsDTO = new GoodsDTO();
                    goodsDTO.setId(userGoods.getId());
                    goodsDTO.setName(goods.getName());
                    goodsDTO.setPrice(CalculateUtils.sellPrice(goods.getName(),
                            goods.getPrice(),
                            port.getPriceIndex().doubleValue(),
                            voyageProperty.getGoodsPriceChangeModule(),
                            trCache.get(user.getArea().getCode()),
                            voyageProperty.getSellRate()));
                    return goodsDTO;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void sellGoods(Long userGoodsId, Integer quantity) {
        User user = userService.getCurrent();
        Port port = user.getPort();
        if (port == null) {
            return;
        }
        user.getUserGoodsList()
                .stream()
                .filter(userGoods -> userGoods.getId().equals(userGoodsId))
                .findFirst()
                .ifPresent(userGoods -> {
                    Goods goods = userGoods.getGoods();
                    int price = CalculateUtils.sellPrice(goods.getName(),
                            goods.getPrice(),
                            port.getPriceIndex().doubleValue(),
                            voyageProperty.getGoodsPriceChangeModule(),
                            trCache.get(user.getArea().getCode()),
                            voyageProperty.getSellRate());
                    int realQuantity;
                    if (userGoods.getQuantity() > quantity) {
                        realQuantity = quantity;
                        userGoods.setQuantity(userGoods.getQuantity() - quantity);
                    } else {
                        realQuantity = userGoods.getQuantity();
                        user.getUserGoodsList().remove(userGoods);
                    }
                    user.setTrade(user.getTrade() + CalculateUtils.tradeChange(realQuantity, price, voyageProperty.getTradeChangeRate()));
                    user.setMoney(user.getMoney() + price * realQuantity);
                    userService.save(user);

                    changePriceIndex(port, quantity, price, false);

                    String msg = "以 " + price + "G 卖出 " + goods.getName() + " " + quantity + "个";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.GOODS, msg);
                });
    }

    private void changePriceIndex(Port port, Integer quantity, Integer price, boolean up) {
        double priceIndexChange = CalculateUtils.priceIndexChange(quantity, price, voyageProperty.getOneDollarInfluence(), voyageProperty.getPriceIndexChangeLimit());
        if (up) {
            BigDecimal targetPriceIndex = port.getPriceIndex().add(new BigDecimal(priceIndexChange));
            BigDecimal maxPriceIndex = new BigDecimal(voyageProperty.getMaxPriceIndex());
            port.setPriceIndex(targetPriceIndex.compareTo(maxPriceIndex) > 0 ? maxPriceIndex : targetPriceIndex);
        } else {
            BigDecimal targetPriceIndex = port.getPriceIndex().subtract(new BigDecimal(priceIndexChange));
            BigDecimal minPriceIndex = new BigDecimal(voyageProperty.getMinPriceIndex());
            port.setPriceIndex(targetPriceIndex.compareTo(minPriceIndex) < 0 ? minPriceIndex : targetPriceIndex);
        }
        portRepository.save(port);
    }
}
