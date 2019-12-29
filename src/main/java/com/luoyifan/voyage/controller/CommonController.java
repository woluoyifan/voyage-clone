package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.exception.BizException;
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
@Controller
public class CommonController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("rank",userService.getRank());
        return "index";
    }

    @RequestMapping("/description")
    public String description(){
        return "description";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("birthplaceList",userService.listBirthplace());
        return "register";
    }

    @PostMapping("/register")
    public String register(String username,
                           String password,
                           String email,
                           Long portId,
                           Integer attack,
                           Integer command,
                           Integer navigation,
                           Model model) {
        try {
            userService.register(username, password, email, portId, attack, command, navigation);
            return "redirect:/";
        } catch (BizException e) {
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("portId", portId);
            model.addAttribute("attack", attack);
            model.addAttribute("command", command);
            model.addAttribute("navigation", navigation);
            model.addAttribute("errMsg", e.getMessage());
            return register(model);
        }

    }
}
