package com.bwie.service;

import com.bwie.domain.User;
import com.bwie.result.Result;

/**
 * @author : dxs
 * @date : Created in 2022/10/27 14:47
 */
public interface UserService {
    Result login(User user);

    User findByUsername(String username);
}
