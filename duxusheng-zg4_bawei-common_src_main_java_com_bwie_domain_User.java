package com.bwie.domain;

import lombok.Data;

/**
 * @author : dxs
 * @date : Created in 2022/10/25 9:08
 */
@Data
public class User {
    /**
     * Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
