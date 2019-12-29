package com.luoyifan.voyage.service;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.constants.TacticEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.constants.UserSortEnum;
import com.luoyifan.voyage.entity.dto.RankDTO;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserOperation;
import com.luoyifan.voyage.toolkit.UserAgentAnalyzer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author EvanLuo
  */
public interface UserService {

    List<Port> listBirthplace();

    void register(String username,
                  String password,
                  String email,
                  Long portId,
                  Integer attack,
                  Integer command,
                  Integer navigation);

    User getById(Long id);

    User getCurrent();

    Optional<User> getByUsername(String username);

    List<PointEnum> listPoint();

    void updateDescription( String description);

    void changePoint( PointEnum point);

    List<TacticEnum> listTactic();

    void changeTactic( TacticEnum tactic);

    void save(User user);

    void saveOperation(Long userId, UserOperationEnum type, String description, UserAgentAnalyzer userAgentAnalyzer, String ip);

    void saveCurrentUserOperation(UserOperationEnum type, String description);

    List<UserOperation> listOperation();

    Page<User> listPage(Integer page, UserSortEnum sort);

    void sendMail(Long uid, String message, Integer money);

    RankDTO getRank();
}
