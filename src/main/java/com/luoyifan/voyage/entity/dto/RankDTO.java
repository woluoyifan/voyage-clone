package com.luoyifan.voyage.entity.dto;

import com.luoyifan.voyage.entity.po.User;
import lombok.Data;

import java.util.List;

/**
 * @author EvanLuo
  */
@Data
public class RankDTO {
    private List<User> adventureList;
    private List<User> tradeList;
    private List<User> battleList;
    private List<User> moneyList;
    private User adventureRookie;
    private User tradeRookie;
    private User battleRookie;
}
