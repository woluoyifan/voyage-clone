package com.luoyifan.voyage.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
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
public class UserItem extends BaseData {
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "item_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
