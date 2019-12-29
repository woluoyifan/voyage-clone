package com.luoyifan.voyage.entity.dto;

import com.luoyifan.voyage.constants.PointEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EvanLuo
  */
@Data
public class CreateAdventureDTO {
    private String name;
    private String description;
    private Integer price;
    private Long itemId;
    private List<Long> areaIdList = new ArrayList<>();
    private List<Long> portIdList = new ArrayList<>();
    private List<Long> detailAreaIdList = new ArrayList<>();
    private List<Long> detailPortIdList = new ArrayList<>();
    private List<PointEnum> detailPointList = new ArrayList<>();
    private List<String> detailDescriptionList = new ArrayList<>();
}
