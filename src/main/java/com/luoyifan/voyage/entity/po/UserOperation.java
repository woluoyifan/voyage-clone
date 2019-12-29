package com.luoyifan.voyage.entity.po;

import com.luoyifan.voyage.constants.BrowserEnum;
import com.luoyifan.voyage.constants.PlatformEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserOperation extends BaseData {
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private UserOperationEnum type;
    private String description;
    private String ip;
    private String userAgent;
    @Enumerated(EnumType.STRING)
    private PlatformEnum platformType;
    private String platformSeries;
    private String platformVersion;
    @Enumerated(EnumType.STRING)
    private BrowserEnum browserType;
    private String browserVersion;
}
