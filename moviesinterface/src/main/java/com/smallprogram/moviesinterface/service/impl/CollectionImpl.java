package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.bean.domain.MoviesCommentsBean;
import com.smallprogram.moviesinterface.service.ICollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Service("collectionImpl")
public class CollectionImpl implements ICollection {

    private static final Logger logger = Logger.getLogger("CollectionImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DetailResultBean addCollection(int id, String uid){
        DetailResultBean detailResultBean = new DetailResultBean();
        String querySql = "SELECT * FROM movies_comments WHERE uid='"+uid+"' AND id="+id+" AND collection=1";
        List comment=jdbcTemplate.query(querySql, new RowMapper<MoviesCommentsBean>(){
            @Override
            public MoviesCommentsBean mapRow(ResultSet res, int i) throws SQLException {
                MoviesCommentsBean moviesCommentsBean = new MoviesCommentsBean();
                moviesCommentsBean.setDate(res.getString("dates"));
                return moviesCommentsBean;
            }
        });
        int size=comment.size();
        if(size>0){
            detailResultBean.setFlag("2");
            detailResultBean.setException_msg("已添加收藏，无需重复添加");
            detailResultBean.setData(size);
        }else {
            String sql = "INSERT INTO movies_comments(uid,id,collection)values(?,?,?)";
            int temp = jdbcTemplate.update(sql,new Object[]{uid,id,1});
            if(temp>0){
                detailResultBean.setException_msg("添加收藏成功");
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
