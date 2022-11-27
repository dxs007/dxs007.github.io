package com.bwie.filter;

import com.bwie.utils.GatewayUtils;
import com.bwie.utils.JwtUtils;
import com.bwie.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关过滤器【全局过滤器】
 * 拦截请求 验证 token
 * @author : dxs
 * @date : Created in 2022/10/25 14:49
 */
@Component
@Log4j2
public class AuthFilter implements GlobalFilter, Ordered {
    /**
     * 定义白名单
     */
    public static final List<String> PATHS = new ArrayList<String>(){
        {
            add("/auth/login");
        }
    };

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取请求路径
        String path = request.getURI().getPath();
        //获取请求方式
        HttpMethod method = request.getMethod();
        log.info("功能名称：过滤器，请求路径【{}】，请求方式【{}】",
                path,method);
        //判断当前的请求是否在白名单中
        if (StringUtils.matches(path,PATHS)){
            //放行
            return chain.filter(exchange);
        }
        //获取token
        String token = request.getHeaders().getFirst(JwtUtils.TOKEN);
        if (StringUtils.isEmpty(token)){
            //token为空
            log.error("功能：网关过滤器，请求路径【{}】，请求方式【{}】，错误信息【{}】",
                    path,method,"token不能为空");
            return GatewayUtils.errorResponse(exchange,"token不能为空");
        }
        //判断 token 是否合法  调用 token 解析方法 如果token 不合法 则 抛出异常
        try{
            JwtUtils.parseToken(token);
        }catch (Exception exception){
            log.error("功能：网关过滤器，请求路径【{}】，请求方式【{}】，错误信息【{}】",
                    path,method,"token不合法！");
            return GatewayUtils.errorResponse(exchange,"token不合法!");
        }
        //token 是否过期
        String userKey = JwtUtils.getUserKey(token);
        Boolean bol = redisTemplate.hasKey(JwtUtils.LOGIN_TOKEN_KEY + userKey);
        if (!bol){
            log.error("功能：网关过滤器，请求路径【{}】，请求方式【{}】，错误信息【{}】",
                    path,method,"token过期！");
            return GatewayUtils.errorResponse(exchange,"token过期!");
        }
        //放行
        return chain.filter(exchange);
    }

    /**
     * 用来规定过滤器的执行顺序  值越小  优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
