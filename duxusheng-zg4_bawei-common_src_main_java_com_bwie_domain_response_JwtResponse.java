package com.bwie.domain.response;

import lombok.Data;

/**
 * @author : dxs
 * @date : Created in 2022/10/25 9:16
 */
@Data
public class JwtResponse {
    /**
     * token 令牌
     */
    private String token;

    /**
     * 过期时间
     */
    private String expireTime;
}
