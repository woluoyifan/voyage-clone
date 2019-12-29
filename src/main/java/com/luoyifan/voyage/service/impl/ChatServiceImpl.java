package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.dao.ChatRecordRepository;
import com.luoyifan.voyage.entity.po.ChatRecord;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.ChatService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EvanLuo
  */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRecordRepository chatRecordRepository;

    @Override
    public List<ChatRecord> list(){
        return chatRecordRepository.findAll(PageRequest.of(0,voyageProperty.getChatRecordDisplayLimit(), Sort.Direction.DESC,"createTime")).getContent();
    }

    @Override
    public void create(String content){
        AssertUtils.nonBlank(content,"发送内容不能为空");
        AssertUtils.isTrue(content.length()<255,"发送内容不能超过255字");
        User user = userService.getCurrent();
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUser(user);
        chatRecord.setContent(content);
        chatRecordRepository.save(chatRecord);
    }
}
