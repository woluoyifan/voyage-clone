package com.luoyifan.voyage.controller.handler;

import com.luoyifan.voyage.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author EvanLuo
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handleBizException(BizException e, Model model) {
        model.addAttribute("errMsg", e.getMessage());
        return "voyage/error";
    }

    @ExceptionHandler
    public String handleSystemException(Exception e, Model model) {
        log.error("系统错误", e);
        model.addAttribute("errMsg", "系统错误");
        return "voyage/error";
    }
}
