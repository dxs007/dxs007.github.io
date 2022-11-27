package com.bwie.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author : dxs
 * @date : Created in 2022/10/25 9:07
 */
@Data
@Document("sys-user")
public class SysUser {
    /**
     * ID
     */
    @Id
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 创建时间
     */
    private Date createTime;
}
