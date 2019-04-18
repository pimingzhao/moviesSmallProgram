package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.DetailResultBean;

import com.smallprogram.moviesinterface.bean.domain.*;
import com.smallprogram.moviesinterface.service.IDetailMovies;
import com.smallprogram.moviesinterface.utils.JavaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Service("detailMoviesImpl")
public class DetailMoviesImpl implements IDetailMovies {

    private static Logger logger = Logger.getLogger("DetailMoviesImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DetailResultBean query(int id){
        DetailResultBean detailResultBean = new DetailResultBean();
//        演员数据SQL
        String actorSql="SELECT * FROM movies_actors WHERE id="+id;
        //总数据sql
        String sql="SELECT * FROM movies_detail WHERE id="+id;
        // 评论数据sql
        String commentsSql="SELECT * FROM movies_comments WHERE collection=0 AND id="+id;
        // 收藏数据sql
        String collectSql="SELECT COUNT(*) FROM movies_comments WHERE collection=1 AND id="+id;
        List actors=jdbcTemplate.query(actorSql,new BaseRowMapperBean(MoviesActorsBean.class));
        int collect_count = jdbcTemplate.queryForObject(collectSql,Integer.class);

        // 遍历数据库中的comments，获取其中的uid用于查找对应的用户
        List comments=jdbcTemplate.query(commentsSql, new RowMapper<MoviesCommentsBean>() {
            @Override
            public MoviesCommentsBean mapRow(ResultSet resultSet, int i) throws SQLException {
                MoviesCommentsBean moviesCommentsBean = new MoviesCommentsBean();
                moviesCommentsBean.setContent(resultSet.getString("content"));
                moviesCommentsBean.setDate(JavaUtil.StringToDate(resultSet.getString("dates")));
                String uid=resultSet.getString("uid");
                String userSql="SELECT * FROM movies_user WHERE uid='"+uid+"'";
                jdbcTemplate.query(userSql, new RowMapper<MoviesUserBean>() {
                    @Override
                    public MoviesUserBean mapRow(ResultSet resultSet, int i) throws SQLException {
                        moviesCommentsBean.setName(resultSet.getString("name"));
                        moviesCommentsBean.setAvatar(resultSet.getString("avatar"));
                        return null;
                    }
                });
                return moviesCommentsBean;
            }
        });
        //从数据库中查找有关电影的所有信息，并赋值一些需要更改的信息
        List data=jdbcTemplate.query(sql, new RowMapper<MovieDetailBean>() {
            @Override
            public MovieDetailBean mapRow(ResultSet resultSet, int i) throws SQLException {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setActors(actors);
                movieDetailBean.setComments(comments);
                movieDetailBean.setId(resultSet.getInt("id"));
                movieDetailBean.setAverage(resultSet.getFloat("average"));
                movieDetailBean.setImage(resultSet.getString("image"));
                movieDetailBean.setTitle(resultSet.getString("title"));
                movieDetailBean.setImage(resultSet.getString("image"));
                movieDetailBean.setPubdate(resultSet.getDate("pubdate"));
                movieDetailBean.setYear(resultSet.getString("year"));
                movieDetailBean.setDirectors(resultSet.getString("directors"));
                movieDetailBean.setSummary(resultSet.getString("summary"));
                movieDetailBean.setCollect_count(collect_count);
                movieDetailBean.setMovies_type(resultSet.getString("movies_type"));
                movieDetailBean.setLanguage(resultSet.getString("language"));
                movieDetailBean.setType(resultSet.getString("type"));
                return movieDetailBean;
            }
        });
        detailResultBean.setData(data);
        if(data!=null){
            int size = data.size();
            if(size==0){
                detailResultBean.setFlag("2");
            }else
                detailResultBean.setFlag("1");
        }else {
            detailResultBean.setFlag("3");
            detailResultBean.setException_msg(JavaUtil.toString(logger));
        }
        return detailResultBean;
    }
}
