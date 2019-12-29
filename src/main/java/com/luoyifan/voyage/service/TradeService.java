package com.luoyifan.voyage.service;

import com.luoyifan.voyage.entity.dto.GoodsDTO;

import java.util.List;

/**
 * @author EvanLuo
  */
public interface TradeService {
    List<GoodsDTO> listPurchasableGoods();

    void purchaseGoods(Long goodsId, Integer quantity);

    List<GoodsDTO> listSaleableGoods();

    void sellGoods(Long userGoodsId, Integer quantity);
}
