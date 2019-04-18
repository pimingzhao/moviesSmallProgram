package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.QueryResultBean;
import com.smallprogram.moviesinterface.bean.domain.MoviesCommonBean;
import com.smallprogram.moviesinterface.service.IQueryMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("queryMovisImpl")
public class QueryMovisImpl implements IQueryMovies {

    private static final Logger logger = Logger.getLogger("QueryMovisImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static String sqlencode(String title) {
        title=title.replace("[","");
        title=title.replace("_","");
        title=title.replace("%","");
        return title;
    }

    @Override
    public QueryResultBean qeuryMovies(String title, int start, int length){
        QueryResultBean resultBean = new QueryResultBean();
        String newTitle=sqlencode(title);
        String sql="SELECT * FROM movies_detail WHERE title LIKE '%"+newTitle+"%' LIMIT "+(start-1)+","+length;
        String countSql="SELECT COUNT(*) FROM movies_detail WHERE title LIKE '%"+newTitle+"%'";
        int total=jdbcTemplate.queryForObject(countSql,Integer.class);
        List data=jdbcTemplate.query(sql,new BaseRowMapperBean(MoviesCommonBean.class));

        resultBean.setData(data);
        resultBean.setStart(start);
        resultBean.setCount(length);
        resultBean.setTotal(total);
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
        return resultBean;
    }
}
