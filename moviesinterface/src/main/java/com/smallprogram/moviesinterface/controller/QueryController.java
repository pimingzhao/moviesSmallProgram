package com.smallprogram.moviesinterface.controller;


import com.smallprogram.moviesinterface.bean.QueryResultBean;
import com.smallprogram.moviesinterface.service.IQueryMovies;
import com.smallprogram.moviesinterface.utils.JavaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"电影搜索接口"})
@CrossOrigin // 允许所有访问源和所有访问方法
@RequestMapping("/movies")
@RestController
public class QueryController {

    @Autowired
    private IQueryMovies queryMovie;

    @ApiOperation(value = "即将上映电影查询接口",notes = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "电影名称",required = true,paramType = "form"),
            @ApiImplicitParam(name = "start",value = "分页起始页",paramType = "form"),
            @ApiImplicitParam(name = "length",value = "每页数据量",paramType = "form"),
    })

    @GetMapping("/query")
    public QueryResultBean queryM(String title, String start, String length){
        if(start==null)
            start="1";
        if(length==null)
            length="10";
        return queryMovie.qeuryMovies(title, JavaUtil.converStrToInterger(start,"起始页"),JavaUtil.converStrToInterger(length,"每页数据量"));
    }
}
