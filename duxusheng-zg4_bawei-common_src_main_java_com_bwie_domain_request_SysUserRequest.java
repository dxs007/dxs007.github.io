package com.bwie.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *
 * 系统用户查询条件实体类
 *
 * @author : dxs
 * @date : Created in 2022/10/25 9:14
 */
@Data
public class SysUserRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     *  性别精确查询
     */
    private String sex;

    /**
     * 创建时间 - 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 创建时间 - 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页每天展示记录数
     */
    private Integer pageSize = 2;
}
