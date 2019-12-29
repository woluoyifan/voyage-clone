package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.BarOperationEnum;
import com.luoyifan.voyage.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/bar")
@Controller
public class BarController extends BaseController {
    @Autowired
    private BarService barService;

    @GetMapping
    public String bar(Model model) {
        barService.friend();
        model.addAttribute("employPrice", barService.getEmployPrice());
        model.addAttribute("foodPrice", barService.getFoodPrice());
        model.addAttribute("userList", barService.listBarGuest());
        return forwardToVoyage();
    }

    @PostMapping
    public String bar(Model model, BarOperationEnum barOperation) {
        if (barOperation == null) {
            return bar(model);
        }
        switch (barOperation) {
            case EMPLOY:
                return forward("/bar/employ");
            case FIRE:
                return forward("/bar/fire");
            case PURCHASE_FOOD:
                return forward("/bar/purchaseFood");
            case THROW_FOOD:
                return forward("/bar/throwFood");
            case ADVENTURE:
                return forward("/bar/adventure");
            default:
        }
        return bar(model);
    }

    @PostMapping("/employ")
    public String employ(Model model, Integer quantity) {
        barService.employ(quantity);
        return bar(model);
    }

    @PostMapping("/fire")
    public String fire(Model model, Integer quantity) {
        barService.fire(quantity);
        return bar(model);
    }

    @PostMapping("/purchaseFood")
    public String purchaseFood(Model model, Integer quantity) {
        barService.purchaseFood(quantity);
        return bar(model);
    }

    @PostMapping("/throwFood")
    public String throwFood(Model model, Integer quantity) {
        barService.throwFood(quantity);
        return bar(model);
    }

    @PostMapping("/adventure")
    public String adventure(Model model, Long adventureId) {
        if (adventureId == null) {
            model.addAttribute("adventureList", barService.listAdventure());
            model.addAttribute("barOperation", BarOperationEnum.ADVENTURE);
            return bar(model);
        }
        barService.startAdventure(adventureId);
        return bar(model);
    }
}
