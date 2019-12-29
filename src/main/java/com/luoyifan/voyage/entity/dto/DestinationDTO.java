package com.luoyifan.voyage.entity.dto;

import lombok.Data;

/**
 * @author EvanLuo
  */
@Data
public class DestinationDTO<T> {
    private T target;
    private Integer totalSecond;
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer food;

}
