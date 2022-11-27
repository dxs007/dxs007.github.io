package com.bwie.controller;

import com.alibaba.fastjson.JSONObject;
import com.bwie.domain.User;
import com.bwie.result.Result;
import com.bwie.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : dxs
 * @date : Created in 2022/10/27 14:44
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("功能名称：登录，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(user));
        Result result = userService.login(user);
        log.info("功能名称：登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 根据用户查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/findByUsername/{username}")
    public Result<User> findByUsername(@PathVariable("username") String username){
        log.info("功能名称：根据用户查询用户信息，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(username));
        User user = userService.findByUsername(username);
        Result<User> result = Result.success(user);
        log.info("功能名称：登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }
}
