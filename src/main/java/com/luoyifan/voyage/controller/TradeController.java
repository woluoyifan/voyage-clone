package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/trade")
@Controller
public class TradeController extends BaseController {
    @Autowired
    private TradeService tradeService;

    @GetMapping
    public String trade(Model model) {
        model.addAttribute("purchasableGoodsList", tradeService.listPurchasableGoods());
        model.addAttribute("saleableGoodsList", tradeService.listSaleableGoods());
        return forwardToVoyage();
    }

    @PostMapping
    public String trade(Model model,
                        Integer quantity,
                        Long goodsId,
                        Long userGoodsId) {
        if (quantity != null) {
            if (goodsId != null) {
                tradeService.purchaseGoods(goodsId, quantity);
            } else if (userGoodsId != null) {
                tradeService.sellGoods(userGoodsId, quantity);
            }
        }
        return trade(model);
    }
}
