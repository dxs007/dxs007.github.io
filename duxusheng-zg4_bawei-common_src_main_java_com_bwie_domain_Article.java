package com.bwie.domain;

import lombok.Data;

import java.util.Date;

/**
 *
 * 文章实体类
 *
 * @author : dxs
 * @date : Created in 2022/10/25 8:43
 */
@Data
public class Article {
    /**
     * 主键
     */
    private String id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     *  地址
     */
    private String address;
    /**
     * 文章类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
}
