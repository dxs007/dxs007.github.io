package com.bwie.controller;

import com.alibaba.fastjson.JSONObject;
import com.bwie.domain.Department;
import com.bwie.domain.Info;
import com.bwie.domain.InfoMongo;
import com.bwie.domain.request.InfoRequest;
import com.bwie.domain.response.InfoResponse;
import com.bwie.result.PageResult;
import com.bwie.result.Result;
import com.bwie.service.InfoService;
import com.bwie.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : dxs
 * @date : Created in 2022/11/1 15:24
 */
@RestController
@RequestMapping("/info")
@Log4j2
public class InfoController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private InfoService infoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加信息
     * @param info
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Info info){
        log.info("功能名称：添加信息，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(info));

        //添加信息
        Result result = infoService.add(info);

        log.info("功能名称：添加信息，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 查询部门
     * @return
     */
    @GetMapping("/findAllDept")
    public Result findAllDept(){
        log.info("功能名称：查询部门，请求路径【{}】，请求方式【{}】，请求参数【{无}】",request.getRequestURI(),
                request.getMethod());
        //查询部门
        List<Department> depts = infoService.findAllDept();
        Result<List<Department>> result = Result.success(depts);
        log.info("功能名称：查询部门，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 分页查询 MySQL
     * @param infoRequest
     * @return
     */
    @PostMapping("/list")
    public Result<PageResult<InfoResponse>> list(@RequestBody InfoRequest infoRequest){
        log.info("功能名称：查询信息列表，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(infoRequest));
        //分页条件查询
        PageHelper.startPage(infoRequest.getPageNum(), infoRequest.getPageSize());

        //调用service查询
        List<InfoResponse> list = infoService.list(infoRequest);

        //构建pageinfo
        PageInfo<InfoResponse> pageInfo = new PageInfo<>(list);
        Result<PageResult<InfoResponse>> result = PageResult.toResult(pageInfo.getTotal(), list);
        log.info("功能名称：查询信息列表，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 查询mongo列表
     * @param infoRequest
     * @return
     */
    @PostMapping("/mongoList")
    public Result<PageResult<InfoMongo>> mongoList(@RequestBody InfoRequest infoRequest){
        log.info("功能名称：查询mongo信息列表，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(infoRequest));
        //创建查询条件拼接对象
        Criteria criteria = new Criteria();
        if (StringUtils.isNotEmpty(infoRequest.getName())){
            //姓名模糊查询
            Pattern compile = Pattern.compile("^.*" + infoRequest.getName() + ".*$");
            criteria.and("name").regex(compile);
        }

        //部门精确查询
        if (infoRequest.getDeptId() != null){
            criteria.and("deptId").is(infoRequest.getDeptId());
        }

        //根据创建时间区间查询
        if (infoRequest.getStartTime() != null && infoRequest.getEndTime() != null){
            criteria.and("createTime").gte(infoRequest.getStartTime()).lte(infoRequest.getEndTime());
        } else if (infoRequest.getStartTime() != null){
            criteria.and("createTime").gte(infoRequest.getStartTime());
        } else if (infoRequest.getEndTime() != null){
            criteria.and("createTime").lte(infoRequest.getEndTime());
        }

        //查询消息总记录数
        Query query = new Query();
        //嵌入查询拼接对象
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, InfoMongo.class);
        //分页
        //skip  跳过几条数据
        query.skip((infoRequest.getPageNum() - 1) * (infoRequest.getPageSize()));
        //limit 设置每页展示多少条数据
        query.limit(infoRequest.getPageSize());
        //排序
        query.with(Sort.by(Sort.Order.desc("createTime")));
        //结果集
        List<InfoMongo> list = mongoTemplate.find(query, InfoMongo.class);
        Result<PageResult<InfoMongo>> result = PageResult.toResult(count, list);
        log.info("功能名称：查询mongo信息列表，请求路径【{}】，响应结果【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(), JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 修改回显
     * @param id
     * @return
     */
    @GetMapping("/findOneById/{id}")
    public Result findOneById(@PathVariable("id") Integer id){
        log.info("功能名称：修改回显，请求路径【{}】，请求方式【{}】，请求参数【{}】",request.getRequestURI(),
                request.getMethod(),JSONObject.toJSONString(id));
        Info info = infoService.findOneById(id);
        Result<Info> result = Result.success(info);
        log.info("功能名称：修改回显，请求路径【{}】，请求方式【{}】，响应结果【{}】",request.getRequestURI(),
                request.getMethod(),JSONObject.toJSONString(result));
        return result;
    }
}
