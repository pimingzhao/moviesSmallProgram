package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.BaseRowMapperBean;
import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.bean.domain.QueryCollectionBean;
import com.smallprogram.moviesinterface.bean.domain.QueryUserBean;
import com.smallprogram.moviesinterface.service.IQueryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Service("queryUserImpl")
public class QueryUserImpl implements IQueryUser {

    private static final Logger logger = Logger.getLogger("QueryUserImpl");

    @Autowired
    JdbcTemplate jdbcTemplate;
//查询用户的收藏信息
    @Override
    public DetailResultBean queryUser(String uid){
        DetailResultBean detailResultBean = new DetailResultBean();
        String sql="SELECT * FROM movies_user WHERE uid='"+uid+"'";
        String collectSql="SELECT * FROM movies_comments WHERE uid='"+uid+"' AND collection=1";
//        String commentSql="SELECT * FROM movies_comments WHERE uid='"+uid+"' AND collection=0";
        List collections=jdbcTemplate.query(collectSql,new BaseRowMapperBean(QueryCollectionBean.class));
        List data=jdbcTemplate.query(sql, new RowMapper<QueryUserBean>(){
            @Override
            public QueryUserBean mapRow(ResultSet resultSet, int i) throws SQLException {
                QueryUserBean queryUserBean = new QueryUserBean();
                queryUserBean.setAvatar(resultSet.getString("avatar"));
                queryUserBean.setName(resultSet.getString("name"));
                queryUserBean.setCollection(collections);
                return queryUserBean;
            }
        });
        detailResultBean.setData(data);
        if(data==null){
            detailResultBean.setFlag("3");
            detailResultBean.setException_msg(logger.toString());
        }else{
            int size = data.size();
            if(size == 0)
                detailResultBean.setFlag("2");
            else
                detailResultBean.setFlag("1");
        }
        return detailResultBean;
    }
}
