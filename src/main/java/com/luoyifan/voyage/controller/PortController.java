package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/port")
@Controller
public class PortController extends BaseController {
    @Autowired
    private PortService moveService;

    @GetMapping
    public String port(Model model) {
        model.addAttribute("portList", moveService.listDestinationPort());
        model.addAttribute("areaList", moveService.listDestinationArea());
        return forward(VOYAGE_VIEW);
    }

    @PostMapping
    public String port(Model model,
                       Long portId,
                       Long areaId) {
        if (portId != null) {
            moveService.moveToPort(portId);
        } else if (areaId != null) {
            moveService.moveToArea(areaId);
        }
        return port(model);
    }
}
