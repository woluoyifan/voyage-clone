package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

/**
 * @author EvanLuo
 */
@Slf4j
public class BaseController {
    protected static final String VOYAGE_VIEW = "/voyage/view";

    protected String forward(String target) {
        return "forward:" + target;
    }

    protected String forwardToVoyage(){
        return forward(VOYAGE_VIEW);
    }

    protected String redirectVoyage() {
        return "redirect:/voyage";
    }

    protected void handleError(Exception e,Model model){
        String errMsg = "系统错误";
        if(e instanceof BizException){
            errMsg = e.getMessage();
        }else{
            log.error(errMsg,e);
        }
        model.addAttribute("errMsg",errMsg);
    }
}
