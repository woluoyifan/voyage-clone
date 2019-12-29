package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.UserSortEnum;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.service.EventService;
import com.luoyifan.voyage.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@Controller
public class ExtraController {
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    @RequestMapping("/operations")
    public String operations(Model model) {
        model.addAttribute("operationList", userService.listOperation());
        return "extra/operations";
    }

    @RequestMapping("/intro")
    public String intro(Model model, String description) {
        if (StringUtils.isNotBlank(description)) {
            userService.updateDescription(description);
        }
        model.addAttribute("user", userService.getCurrent());
        return "extra/intro";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Long id) {
        if (id != null) {
            User user = userService.getById(id);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("errMsg", "用户不存在");
        return "extra/profile";
    }

    @RequestMapping("/userlist")
    public String userlist(Model model, Integer page, UserSortEnum sort) {
        model.addAttribute("sort", sort);
        model.addAttribute("sortList", UserSortEnum.values());
        Page<User> userPage = userService.listPage(page, sort);
        model.addAttribute("userPage", userPage);
        return "extra/userlist";
    }

    @RequestMapping("/events")
    public String event(Model model) {
        model.addAttribute("eventList", eventService.list());
        return "extra/events";
    }

    @RequestMapping("/mail")
    public String mail(Model model, Long uid, String message, Integer money) {
        if (uid != null) {
            userService.sendMail(uid, message, money);
        }
        User user = userService.getCurrent();
        model.addAttribute("friendshipList", user.getUserFriendshipList());
        return "extra/mail";
    }
}
