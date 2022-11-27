package com.bwie.controller;

import com.alibaba.fastjson.JSONObject;
import com.bwie.domain.User;
import com.bwie.domain.response.JwtResponse;
import com.bwie.feign.UserFeign;
import com.bwie.result.PageResult;
import com.bwie.result.Result;
import com.bwie.utils.JwtUtils;
import com.bwie.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : dxs
 * @date : Created in 2022/10/28 15:52
 */
@RestController
@Log4j2
public class AuthController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<JwtResponse> login(@RequestBody User user){
        log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(user));
        // 1 . 非空验证
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            Result<JwtResponse> result = Result.error("用户名或密码不能为空");
            log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                    request.getMethod(), JSONObject.toJSONString(result));
            return result;
        }

        // 2 . 验证用户名
        Result<User> byUsername = userFeign.findByUsername(user.getUsername());
        User loginUser = byUsername.getData();
        if (loginUser == null){
            Result<JwtResponse> result = Result.error("用户名不存在");
            log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                    request.getMethod(), JSONObject.toJSONString(result));
            return result;
        }

        // 3 . 验证密码
        if (!user.getPassword().equals(loginUser.getPassword())){
            Result<JwtResponse> result = Result.error("密码错误");
            log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                    request.getMethod(), JSONObject.toJSONString(result));
            return result;
        }
//        if (!bCryptPasswordEncoder.matches(user.getPassword(),loginUser.getPassword())){
//            Result<JwtResponse> result = Result.error("密码错误");
//            log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
//                   request.getMethod(), JSONObject.toJSONString(result));
//           return result;
//        }

        // 4 . 登陆成功  生成 token jwt
        String userkey = UUID.randomUUID().toString().replaceAll("-", "");
        HashMap<String, Object> jwtMap = new HashMap<String, Object>();
        jwtMap.put(JwtUtils.DETAILS_USER_ID,loginUser.getId());

        //相当于 redis的key
        jwtMap.put(JwtUtils.USER_KEY ,userkey);
        String token = JwtUtils.createToken(jwtMap);

        //需要将用户信息存入redis
        redisTemplate.opsForValue().set(JwtUtils.LOGIN_TOKEN_KEY + userkey,JSONObject.toJSONString(loginUser),5, TimeUnit.HOURS);

        //构建JwtResponse
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setExpireTime("5HOUS");
        Result<JwtResponse> result = Result.success(jwtResponse);
        log.info("功能名称：鉴权登录，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        String token = request.getHeader(JwtUtils.TOKEN);
        //解析token
        String userKey = JwtUtils.getUserKey(token);
        String userJson = redisTemplate.opsForValue().get(JwtUtils.LOGIN_TOKEN_KEY + userKey);
        //反序列化成user
        User user = JSONObject.parseObject(userJson, User.class);
        Result<User> result = Result.success(user);
        return result;
    }
}
