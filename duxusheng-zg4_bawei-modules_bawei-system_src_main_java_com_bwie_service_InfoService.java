package com.bwie.service;

import com.bwie.domain.Department;
import com.bwie.domain.Info;
import com.bwie.domain.request.InfoRequest;
import com.bwie.domain.response.InfoResponse;
import com.bwie.result.PageResult;
import com.bwie.result.Result;

import java.util.List;

/**
 * @author : dxs
 * @date : Created in 2022/11/1 15:29
 */
public interface InfoService {
    Result add(Info info);

    List<InfoResponse> list(InfoRequest infoRequest);

    List<Department>  findAllDept();

    Info findOneById(Integer id);
}
