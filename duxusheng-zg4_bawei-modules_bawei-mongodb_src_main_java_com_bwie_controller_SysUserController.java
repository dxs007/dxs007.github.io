package com.bwie.controller;

import com.alibaba.fastjson.JSONObject;
import com.bwie.domain.SysUser;
import com.bwie.result.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统用户控制层
 *
 * @author : dxs
 * @date : Created in 2022/10/25 15:03
 */
@RestController
@RequestMapping("/sys/user")
@Log4j2
public class SysUserController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     * 添加
     */
    @PostMapping
    public Result add(@RequestBody SysUser sysUser){
        log.info("功能名称：添加系统用户，请求路径【{}】，请求方式【{}】，请求参数【{}】",
                request.getRequestURI(),request.getMethod(), JSONObject.toJSONString(sysUser));
        //添加数据到mongodb
        // 设置 创建时间
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.HOUR_OF_DAY,8);
        sysUser.setCreateTime(now.getTime());
        mongoTemplate.insert(sysUser);
        Result<Object> result = Result.success();
        log.info("功能名称：添加系统用户，请求路径【{}】，请求方式【{}】，响应结果【{}】",
                request.getRequestURI(),request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }
}
