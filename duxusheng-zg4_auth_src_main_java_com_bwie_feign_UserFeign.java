package com.bwie.feign;

import com.bwie.domain.User;
import com.bwie.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : dxs
 * @date : Created in 2022/10/28 15:54
 */
@FeignClient(name = "system",path = "/user")
public interface UserFeign {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/findByUsername/{username}")
    public Result<User> findByUsername(@PathVariable("username") String username);
}
