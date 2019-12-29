package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.entity.dto.CreateAdventureDTO;
import com.luoyifan.voyage.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping
    public String admin() {
        return "admin/admin";
    }

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/adventure")
    public String adventure(Model model) {
        model.addAttribute("adventureList", adminService.listAdventure());
        return "admin/adventure";
    }

    @RequestMapping("/adventure/view")
    public String viewAdventure(Model model, Long id) {
        if (id != null) {
            model.addAttribute("adventure", adminService.getAdventure(id));
        }
        return "admin/viewAdventure";
    }

    @RequestMapping("/adventure/del")
    public String delAdventure(Long id) {
        if (id != null) {
            adminService.deleteAdventure(id);
        }
        return "redirect:/admin/adventure";
    }

    @RequestMapping("/adventure/create")
    public String createAdventure(Model model, CreateAdventureDTO createAdventureDTO) {
        if (StringUtils.isNotBlank(createAdventureDTO.getName())) {
            adminService.createAdventure(createAdventureDTO);
        }
        model.addAttribute("areaList", adminService.listArea());
        model.addAttribute("portList", adminService.listPort());
        model.addAttribute("itemList", adminService.listItem());
        model.addAttribute("pointList", PointEnum.values());
        return "admin/createAdventure";
    }

}
