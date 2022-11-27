package com.bwie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bwie.domain.User;
import com.bwie.mapper.UserMapper;
import com.bwie.result.Result;
import com.bwie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : dxs
 * @date : Created in 2022/10/27 14:47
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(User user) {
        //根据用户名查询用户信息
        User loginUser = userMapper.findByUserName(user.getUsername());
        if (loginUser == null){
            return Result.error("用户名不存在");
        }

        //对比密码是否正确
        if (!loginUser.getPassword().equals(user.getPassword())){
            return Result.error("密码错误");
        }

        //登陆成功
        //将用户信息存入 redis 5小时、
        redisTemplate.opsForValue().set("user_" + user.getId(), JSONObject.toJSONString(loginUser) , 5 , TimeUnit.HOURS);
        return Result.success("登陆成功");
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUserName(username);
    }
}
