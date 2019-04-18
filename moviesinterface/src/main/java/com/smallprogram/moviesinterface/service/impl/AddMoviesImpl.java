package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.service.IAddMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

@Service("addMoviesImpl")
public class AddMoviesImpl implements IAddMovies {

    private static final Logger logger = Logger.getLogger("AddMoviesImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DetailResultBean addMovies(String title,float average,String image,String pubdate,String year,String directors,String summary,String type,String actors) throws ParseException {
        DetailResultBean detailResultBean = new DetailResultBean();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "INSERT INTO movies_detail(title,average,image,pubdate,year,directors,summary,type,actors)values(?,?,?,?,?,?,?,?,?)";

        int temp = jdbcTemplate.update(sql,new Object[]{title,average,image,sdf.parse(pubdate),year,directors,summary,type,actors});
        if(temp>0){
            detailResultBean.setException_msg("添加数据成功");
            detailResultBean.setFlag("1");
            detailResultBean.setData(temp);
        }else{
            detailResultBean.setException_msg(logger.toString());
            detailResultBean.setFlag("3");
            detailResultBean.setData(temp);
        }
        return  detailResultBean;
    }
}
