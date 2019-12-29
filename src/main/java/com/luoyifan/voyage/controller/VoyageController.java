package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.constants.TacticEnum;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.service.BarService;
import com.luoyifan.voyage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/voyage")
@Controller
public class VoyageController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private BarService barService;

    @RequestMapping("/view")
    public String voyageView(Model model) {
        barService.triggerAdventure();
        model.addAttribute("user", userService.getCurrent());
        model.addAttribute("tacticList", userService.listTactic());
        model.addAttribute("pointList", userService.listPoint());
        model.addAttribute("msg", UserDetails.getMsg());
        model.addAttribute("moveWidth", 400);
        return "voyage/voyage";
    }

    @GetMapping
    public String voyage(Model model) {
        Long userId = UserDetails.getCurrentIdOrThrow();
        User user = userService.getById(userId);
        if (user.isMoving()) {
            return forward(VOYAGE_VIEW);
        }
        //TODO 在港口才可以进入的选项设定
        switch (user.getPoint()) {
            case PORT:
                return forward("port");
            case YARD:
                return forward("yard");
            case TRADE:
                return forward("trade");
            case BAR:
                return forward("bar");
            case CITY:
                return forward("city");
            case BATTLE:
                return forward("battle");
            default:
                return forward(VOYAGE_VIEW);
        }
    }

    @PostMapping
    public String voyage(Model model,PointEnum point, TacticEnum tactics) {
        if (point != null) {
            userService.changePoint(point);
        }
        if (tactics != null) {
            userService.changeTactic(tactics);
        }
        return voyage(model);
    }
}
