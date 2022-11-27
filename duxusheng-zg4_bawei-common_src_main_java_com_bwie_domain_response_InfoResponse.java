package com.bwie.domain.response;

import com.bwie.domain.Info;
import lombok.Data;

/**
 * 信息列表查询响应实体类
 *
 * @author : dxs
 * @date : Created in 2022/10/25 9:15
 */
@Data
public class InfoResponse extends Info {
    /**
     * 部门名称
     */
    private String deptName;
}
