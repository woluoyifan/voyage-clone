package com.luoyifan.voyage.entity;


import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.exception.BizException;
import com.luoyifan.voyage.toolkit.UserAgentAnalyzer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;

/**
 * @author EvanLuo
  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserDetails extends org.springframework.security.core.userdetails.User {

    private static final ThreadLocal<String> USER_AGENT_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<String> IP_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<String> MSG_CONTEXT = new ThreadLocal<>();

    private final Long id;

    public UserDetails(User player) {
        super(player.getUsername(), "{noop}" + player.getPassword(),
                true, true, true, true,
                new HashSet<>());
        this.id = player.getId();
    }

    public static Optional<UserDetails> getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //anonymousUser
        return authentication != null && authentication.getPrincipal() instanceof UserDetails ?
                Optional.of((UserDetails) authentication.getPrincipal()) : Optional.empty();
    }

    public static Optional<Long> getCurrentId() {
        return getCurrent().map(UserDetails::getId);
    }

    public static Long getCurrentIdOrThrow() {
        return getCurrentId().orElseThrow(() -> new BizException("未登陆"));
    }

    public static void setUserAgent(String userAgent) {
        USER_AGENT_CONTEXT.set(userAgent);
    }

    public static UserAgentAnalyzer getUserAgentAnalyzer() {
        return new UserAgentAnalyzer(USER_AGENT_CONTEXT.get());
    }

    public static void setIp(String ip){
        IP_CONTEXT.set(ip);
    }

    public static String getIp(){
        return IP_CONTEXT.get();
    }

    public static void setMsg(String msg) {
        MSG_CONTEXT.set(msg);
    }

    public static String getMsg() {
        return MSG_CONTEXT.get();
    }

    public static void clear(){
        USER_AGENT_CONTEXT.remove();
        IP_CONTEXT.remove();
        MSG_CONTEXT.remove();
    }
}
