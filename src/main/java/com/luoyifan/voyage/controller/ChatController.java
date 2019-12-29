package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @RequestMapping
    public String chat(Model model,String content){
        if(StringUtils.isNotBlank(content)) {
            chatService.create(content);
        }
        model.addAttribute("chatRecordList",chatService.list());
        return "chat/chatDetail";
    }
}
