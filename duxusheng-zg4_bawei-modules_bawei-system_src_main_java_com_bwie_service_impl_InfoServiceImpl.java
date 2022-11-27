package com.bwie.service.impl;

import com.bwie.domain.Department;
import com.bwie.domain.Info;
import com.bwie.domain.InfoMongo;
import com.bwie.domain.request.InfoRequest;
import com.bwie.domain.response.InfoResponse;
import com.bwie.mapper.InfoMapper;
import com.bwie.result.PageResult;
import com.bwie.result.Result;
import com.bwie.service.InfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : dxs
 * @date : Created in 2022/11/1 15:29
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result add(Info info) {
        //验证名称是否重复
        int count = infoMapper.findByName(info.getName());
        if (count > 0){
            return Result.error("信息名称已经存在");
        }
        //验证手机号是否重复
        int tel = infoMapper.findByTel(info.getTel());
        if (tel > 0){
            return Result.error("手机号已经存在");
        }
        //添加MySQL
        infoMapper.add(info);
        //添加到mongo
        //属性copy
        InfoMongo infoMongo = new InfoMongo();
        BeanUtils.copyProperties(info, infoMongo);
        //设置创建时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        infoMongo.setCreateTime(calendar.getTime());
        mongoTemplate.insert(infoMongo);
        return Result.success();
    }

    @Override
    public List<InfoResponse> list(InfoRequest infoRequest) {
        return infoMapper.list(infoRequest);
    }

    @Override
    public List<Department>  findAllDept() {
        return infoMapper.findAllDept();
    }

    @Override
    public Info findOneById(Integer id) {
        return infoMapper.findOneById(id);
    }
}
