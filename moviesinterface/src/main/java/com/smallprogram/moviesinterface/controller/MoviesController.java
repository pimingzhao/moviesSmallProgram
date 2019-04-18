package com.smallprogram.moviesinterface.controller;

import com.smallprogram.moviesinterface.bean.ResultBean;
import com.smallprogram.moviesinterface.service.IMovies;
import com.smallprogram.moviesinterface.utils.JavaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"电影类型接口"})
@CrossOrigin // 允许所有访问源和所有访问方法
@RequestMapping("/movies")
@RestController
public class MoviesController {

    @Autowired
    private IMovies movies;

    @ApiOperation(value = "即将上映电影查询接口",notes = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "分页起始页",paramType = "form"),
            @ApiImplicitParam(name = "length",value = "每页数据量",paramType = "form"),
    })

    @GetMapping("/comingSoon/query")
    public ResultBean queryComingSoon(String start, String length){
        if(start==null)
            start="1";
        if(length==null)
            length="10";
        return movies.query(JavaUtil.converStrToInterger(start,"起始页"),JavaUtil.converStrToInterger(length,"每页数据量"),"coming_soon");
    }

    @ApiOperation(value = "正在热映电影查询接口",notes = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "分页起始页",paramType = "form"),
            @ApiImplicitParam(name = "length",value = "每页数据量",paramType = "form"),
    })

    @GetMapping("/inTheaters/query")
    public ResultBean queryinTheaters(String start, String length){
        if(start==null)
            start="1";
        if(length==null)
            length="10";
        return movies.query(JavaUtil.converStrToInterger(start,"起始页"),JavaUtil.converStrToInterger(length,"每页数据量"),"in_theaters");
    }

    @ApiOperation(value = "top250电影查询接口",notes = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "分页起始页",paramType = "form"),
            @ApiImplicitParam(name = "length",value = "每页数据量",paramType = "form"),
    })

    @GetMapping("/top250/query")
    public ResultBean querytop250(String start, String length){
        if(start==null)
            start="1";
        if(length==null)
            length="10";
        return movies.query(JavaUtil.converStrToInterger(start,"起始页"),JavaUtil.converStrToInterger(length,"每页数据量"),"top250");
    }
}
