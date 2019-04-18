package com.smallprogram.moviesinterface.controller;

import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.service.IDetailMovies;
import com.smallprogram.moviesinterface.service.impl.AddMoviesImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Api(tags = {"电影详情接口"})
@CrossOrigin // 允许所有访问源和所有访问方法
@RequestMapping("/movies")
@RestController
public class DetailController {

    @Autowired
    private IDetailMovies detailMovies;

    @ApiOperation(value = "电影详情查询接口",notes = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "电影的唯一标识id",required = true,paramType = "form")
    })

    @GetMapping("/detail")
    public DetailResultBean queryDetail(int id) {
        return detailMovies.query(id);
    }

    @Autowired
    private AddMoviesImpl addMovies;

    @ApiOperation(value = "添加电影接口",notes = "add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "电影的名字",required = true,paramType = "form"),
            @ApiImplicitParam(name = "average",value = "电影的评分，来自豆瓣评分",required = true,paramType = "form"),
            @ApiImplicitParam(name = "image",value = "电影的宣传图",required = true,paramType = "form"),
            @ApiImplicitParam(name = "pubdate",value = "电影的上映日期",required = true,paramType = "form"),
            @ApiImplicitParam(name = "year",value = "电影的拍摄年份",required = true,paramType = "form"),
            @ApiImplicitParam(name = "directors",value = "电影的导演",required = true,paramType = "form"),
            @ApiImplicitParam(name = "actors",value = "电影的演员",required = true,paramType = "form"),
            @ApiImplicitParam(name = "summary",value = "电影的简介",required = true,paramType = "form"),
            @ApiImplicitParam(name = "type",value = "电影的类型 正在热映 | 即将上映 | top250",required = true,paramType = "form")
    })

    @PostMapping("/add")
    public DetailResultBean addMoviesBean(String title,float average,String image,String pubdate,String year,String directors,String summary,String type,String actors) throws ParseException {
        return addMovies.addMovies(title,average,image,pubdate,year,directors,summary,type,actors);
    }
}
