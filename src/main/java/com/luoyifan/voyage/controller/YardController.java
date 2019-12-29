package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.YardOperationEnum;
import com.luoyifan.voyage.service.YardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/yard")
@Controller
public class YardController extends BaseController {
    @Autowired
    private YardService yardService;

    @GetMapping
    public String yard(Model model) {
        model.addAttribute("yardOperationList", YardOperationEnum.values());
        return forwardToVoyage();
    }

    @PostMapping
    public String yard(Model model, YardOperationEnum yardOperation) {
        if (yardOperation == null) {
            return yard(model);
        }
        model.addAttribute("yardOperation", yardOperation);
        switch (yardOperation) {
            case PURCHASE:
                return forward("/yard/purchase");
            case SELL:
                return forward("/yard/sell");
            case REPAIR:
                return forward("/yard/repair");
            default:
                return yard(model);
        }
    }

    @PostMapping("/purchase")
    public String purchase(Model model, Long shipId) {
        if (shipId != null) {
            yardService.purchaseShip(shipId);
        }
        model.addAttribute("shipList", yardService.listPurchasableShip());
        return yard(model);
    }

    @PostMapping("/sell")
    public String sell(Model model, Long userShipId) {
        if (userShipId != null) {
            yardService.sellShip(userShipId);
        }
        model.addAttribute("userShipList", yardService.listSaleableShip());
        return yard(model);
    }

    @PostMapping("/repair")
    public String repair(Model model, Long userShipId) {
        if (userShipId != null) {
            yardService.repairShip(userShipId);
        }
        model.addAttribute("userShipList", yardService.listRepairableShip());
        return yard(model);
    }
}
