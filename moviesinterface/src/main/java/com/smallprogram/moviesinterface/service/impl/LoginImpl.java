package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.bean.domain.MoviesUserBean;
import com.smallprogram.moviesinterface.service.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("loginImpl")
public class LoginImpl implements ILogin {

    private static final Logger logger = Logger.getLogger("LoginImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DetailResultBean addUser(String uid, String name, String avatar){
        DetailResultBean detailResultBean = new DetailResultBean();
        String userSql = "SELECT * FROM movies_user WHERE uid='"+uid+"'";
        // 一定要用list来查看数据库中是否存在该用户吗
        List user = jdbcTemplate.query(userSql,new BaseRowMapperBean(MoviesUserBean.class));
        int size=user.size();
        // 查询该用户是否已经注册过，注册过无需再次注册，没注册再添加到数据库
        if(size>0){
            String sql = "UPDATE movies_user SET NAME='"+name+"',avatar='"+avatar+"' WHERE uid='"+uid+"'";
            int temp = jdbcTemplate.update(sql);
            if(temp>0){
                detailResultBean.setException_msg("用户登陆成功");
                detailResultBean.setFlag("2");
                detailResultBean.setData(temp);
            }else{
                detailResultBean.setException_msg(logger.toString());
                detailResultBean.setFlag("3");
                detailResultBean.setData(temp);
            }
        }else{
            String sql = "INSERT INTO movies_user(uid,name,avatar)values(?,?,?)";
            int temp = jdbcTemplate.update(sql,new Object[]{uid,name,avatar});
            if(temp>0){
                detailResultBean.setException_msg("用户注册成功");
                detailResultBean.setFlag("1");
                detailResultBean.setData(temp);
            }else{
                detailResultBean.setException_msg(logger.toString());
                detailResultBean.setFlag("3");
                detailResultBean.setData(temp);
            }
        }
        return detailResultBean;
    }
}
