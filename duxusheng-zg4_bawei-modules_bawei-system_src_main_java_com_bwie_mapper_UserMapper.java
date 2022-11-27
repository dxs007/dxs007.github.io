package com.bwie.mapper;

import com.bwie.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : dxs
 * @date : Created in 2022/10/27 14:48
 */
@Mapper
public interface UserMapper {
    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUserName(String username);
}
