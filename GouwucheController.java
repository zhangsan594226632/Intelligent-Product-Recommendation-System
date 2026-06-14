package com.spboot.tx.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spboot.tx.config.Configure;
import com.spboot.tx.mapper.*;
import com.spboot.tx.pojo.*;
import com.spboot.tx.service.*;
import com.spboot.tx.utils.*;
import io.swagger.annotations.*;
import java.io.*;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "购物车控制器" })
@RestController
@RequestMapping("/api/gouwuche")
public class GouwucheController {

    @Autowired
    public GouwucheService gouwucheService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @ApiOperation(value = "获取全部购物车", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public ResponseData<List<Gouwuche>> selectAll() {
        return gouwucheService.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取购物车列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public ResponseData selectPages(@RequestBody Map<String, Object> req) {
        return gouwucheService.selectPages(req);
    }

    @ApiOperation(value = "根据条件筛选获取购物车列表，并分页，且只出现某用户列表信息", httpMethod = "POST")
    @RequestMapping("/selectPagesGoumairen")
    public ResponseData selectPagesGoumairen(@RequestBody Map<String, Object> req) {
        req.put("session_name", "goumairen");
        return gouwucheService.selectPages(req);
    }

    @ApiOperation(value = "根据过滤信息获取相关数据", httpMethod = "POST")
    @RequestMapping("/filter")
    public ResponseData<List<Gouwuche>> filter(@RequestBody Map<String, Object> req) {
        return gouwucheService.filter(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "购物车对应的id", dataType = "Integer")
    public ResponseData findById(@RequestParam Integer id) {
        return gouwucheService.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Gouwuche.class, paramType = "body")
    public ResponseData update(@RequestBody Map data) {
        Gouwuche post = BeanUtil.mapToBean(data, Gouwuche.class, true);
        return gouwucheService.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的购物车", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Gouwuche.class, paramType = "body")
    public ResponseData insert(@RequestBody Map data) {
        Gouwuche post = BeanUtil.mapToBean(data, Gouwuche.class, true);
        return gouwucheService.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "购物车对应的id", type = "json", dataTypeClass = List.class)
    public ResponseData delete(@RequestBody List<Integer> id) {
        return gouwucheService.delete(id);
    }
}
