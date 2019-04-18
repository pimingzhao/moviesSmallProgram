package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.bean.domain.MoviesCommentsBean;
import com.smallprogram.moviesinterface.service.ICancelCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Service("cancelCollectionImpl")
public class CancelCollectionImpl implements ICancelCollection {

    private static final Logger logger = Logger.getLogger("CancelCollectionImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DetailResultBean cancelCollection(int id,String uid){
        DetailResultBean detailResultBean = new DetailResultBean();
        String querySql = "SELECT * FROM movies_comments WHERE uid='"+uid+"' AND id="+id+" AND collection=1";
        List comments=jdbcTemplate.query(querySql, new RowMapper<MoviesCommentsBean>(){
            @Override
            public MoviesCommentsBean mapRow(ResultSet resultSet, int i) throws SQLException {
                MoviesCommentsBean moviesCommentsBean = new MoviesCommentsBean();
                moviesCommentsBean.setContent(resultSet.getString("content"));
                return moviesCommentsBean;
            }
        });
        int size=comments.size();
        if(size>0){
            String cancelSql="DELETE FROM movies_comments WHERE uid='"+uid+"' AND id="+id+" AND collection=1";
            int temp = jdbcTemplate.update(cancelSql);
            if(temp>0){
                detailResultBean.setException_msg("移除收藏成功");
                detailResultBean.setFlag("1");
            }else{
                detailResultBean.setException_msg(logger.toString());
                detailResultBean.setFlag("3");
            }
        }else{
            detailResultBean.setData(size);
            detailResultBean.setFlag("2");
            detailResultBean.setException_msg("已移除收藏，无需重复移除");
        }

        detailResultBean.setData(size);
        return detailResultBean;
    }

}
