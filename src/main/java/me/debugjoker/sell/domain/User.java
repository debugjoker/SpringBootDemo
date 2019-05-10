package me.debugjoker.sell.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-10 11:02
 **/
@Data
public class User {

    private Integer id;

    private String username;

    private String password;
}