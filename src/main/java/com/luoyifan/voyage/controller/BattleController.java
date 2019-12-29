package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/battle")
@Controller
public class BattleController extends BaseController {
    @Autowired
    private BattleService battleService;

    @GetMapping
    public String battle(Model model) {
        model.addAttribute("opponentList",battleService.listOpponent());
        return forward(VOYAGE_VIEW);
    }
    @PostMapping
    public String battle(Model model,Long opponentId) {
        if(opponentId!=null) {
            battleService.battle(opponentId);
        }
        return battle(model);
    }
}
