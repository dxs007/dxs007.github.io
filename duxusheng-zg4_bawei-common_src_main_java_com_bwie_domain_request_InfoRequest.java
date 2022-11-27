package com.bwie.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author : dxs
 * @date : Created in 2022/10/25 9:14
 */
@Data
public class InfoRequest {
    /**
     * 姓名名称
     */
    private String name;

    /**
     * 部门
     */
    private Integer deptId;

    /**
     * 分页
     */
    private Integer pageNum = 1;

    /**
     * 记录数
     */
    private Integer pageSize = 2;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
