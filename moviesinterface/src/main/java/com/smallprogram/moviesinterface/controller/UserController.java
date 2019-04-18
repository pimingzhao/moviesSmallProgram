package com.smallprogram.moviesinterface.controller;

import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.service.ICollection;
import com.smallprogram.moviesinterface.service.IComments;
import com.smallprogram.moviesinterface.service.ILogin;
import com.smallprogram.moviesinterface.service.IQueryUser;
import com.smallprogram.moviesinterface.service.impl.CancelCollectionImpl;
import com.smallprogram.moviesinterface.service.impl.QueryUserImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"用户接口"})
@CrossOrigin // 允许所有访问源和所有访问方法
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private ILogin login;

    @ApiOperation(value = "用户登陆/注册接口",notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id",required = true,paramType = "form"),
            @ApiImplicitParam(name = "name",value = "用户名",required = true,paramType = "form"),
            @ApiImplicitParam(name = "avatar",value = "用户图像",paramType = "form"),
    })

    @PostMapping("/login")
    public DetailResultBean login(String uid, String name, String avatar){
        return login.addUser(uid,name,avatar);
    }

    @Autowired
    private IComments comments;

    @ApiOperation(value = "添加评论接口",notes = "add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "电影id",required = true,paramType = "form"),
            @ApiImplicitParam(name = "uid",value = "用户id",required = true,paramType = "form"),
            @ApiImplicitParam(name = "content",value = "评论内容",required = true,paramType = "form"),
    })
    @PostMapping("/addcomments")
    public DetailResultBean comments(int id,String uid,String content,String dates){
        return comments.addComments(id,uid,content,dates);
    }

    @Autowired
    private ICollection icollection;

    @ApiOperation(value = "添加收藏接口",notes = "add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "电影id",required = true,paramType = "form"),
            @ApiImplicitParam(name = "uid",value = "用户id",required = true,paramType = "form"),
    })
    @PostMapping("/addcollect")
    public DetailResultBean addCollection(int id,String uid){
        return icollection.addCollection(id,uid);
    }

    @Autowired
    private CancelCollectionImpl cancelCollection;

    @ApiOperation(value = "移除收藏接口",notes = "delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "电影id",required = true,paramType = "form"),
            @ApiImplicitParam(name = "uid",value = "用户id",required = true,paramType = "form"),
    })
    @PostMapping("/cancelcollect")
    public DetailResultBean cancelCollection(int id,String uid){
        return cancelCollection.cancelCollection(id,uid);
    }

    @Autowired
    private QueryUserImpl user;

    @ApiOperation(value = "查询用户收藏接口",notes = "delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id",required = true,paramType = "form"),
    })
    @GetMapping("/query")
    public DetailResultBean user(String uid){
        return user.queryUser(uid);
    }
}
