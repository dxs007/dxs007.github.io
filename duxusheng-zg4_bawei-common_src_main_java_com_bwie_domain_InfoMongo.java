package com.bwie.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author : dxs
 * @date : Created in 2022/10/25 9:06
 */
@Data
@Document("info")
public class InfoMongo {
    /**
     * id
     */
    private int id;

    /**
     * name
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 学历
     */
    private String education;

    /**
     * 电话
     */
    private String tel;

    /**
     * 部门
     */
    private Integer deptId;

    /**
     * 创建时间
     */
    private Date createTime;
}
