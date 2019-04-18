package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.ResultBean;
import com.smallprogram.moviesinterface.bean.domain.MoviesCommonBean;
import com.smallprogram.moviesinterface.service.IMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("moviesImpl")
public class MoviesImpl implements IMovies {
    private static final Logger logger = Logger.getLogger("MoviesImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public ResultBean query(int start,int length,String type){
        ResultBean resultBean = new ResultBean();
        String sql = "SELECT * FROM movies_detail WHERE type='"+type+"' ORDER BY id LIMIT "+(start-1)+","+length;
        String countSql = "SELECT COUNT(*) FROM movies_detail WHERE type='"+type+"'";
        List data = jdbcTemplate.query(sql,new BaseRowMapperBean(MoviesCommonBean.class));
        int count = jdbcTemplate.queryForObject(countSql,Integer.class);
        if(data!=null){
            int size = data.size();
            if(size == 0)
                resultBean.setFlag("2");
            else
                resultBean.setFlag("1");
        }else{
            resultBean.setFlag("3");
            resultBean.setException_msg(logger.toString());
        }
        resultBean.setTotal(count);
        resultBean.setData(data);
        resultBean.setType(type);
        resultBean.setStart(start);
        resultBean.setCount(length);
        return resultBean;
    }
}
