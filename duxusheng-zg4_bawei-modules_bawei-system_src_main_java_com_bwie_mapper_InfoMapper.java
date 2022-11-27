package com.bwie.mapper;

import com.bwie.domain.Department;
import com.bwie.domain.Info;
import com.bwie.domain.request.InfoRequest;
import com.bwie.domain.response.InfoResponse;
import com.bwie.result.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : dxs
 * @date : Created in 2022/11/1 15:30
 */
@Mapper
public interface InfoMapper {
    /**
     * 添加信息
     * @param info
     */
    void add(Info info);

    /**
     * 根据信息名称查询
     * @param name
     * @return
     */
    int findByName(String name);

    /**
     * 根据手机号查询
     * @param tel
     * @return
     */
    int findByTel(String tel);

    /**
     * 信息列表
     * @param infoRequest
     * @return
     */
    List<InfoResponse> list(InfoRequest infoRequest);

    /**
     * 查询部门
     * @return
     */
    List<Department> findAllDept();

    Info findOneById(Integer id);
}
