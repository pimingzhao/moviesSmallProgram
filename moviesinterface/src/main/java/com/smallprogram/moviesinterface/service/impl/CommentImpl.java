package com.smallprogram.moviesinterface.service.impl;

import com.smallprogram.moviesinterface.bean.DetailResultBean;
import com.smallprogram.moviesinterface.service.IComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("commentImpl")
public class CommentImpl implements IComments {

    private static final Logger logger = Logger.getLogger("CommentImpl.class");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DetailResultBean addComments(int id,String uid,String content,String dates){
        DetailResultBean detailResultBean = new DetailResultBean();
        String sql = "INSERT INTO movies_comments(id,uid,content,dates)values(?,?,?,?)";
        int temp = jdbcTemplate.update(sql,new Object[]{id,uid,content,dates});
        if(temp>0){
            detailResultBean.setException_msg("用户评论成功");
            detailResultBean.setFlag("1");
        }else{
            detailResultBean.setException_msg(logger.toString());
            detailResultBean.setFlag("3");
        }
        detailResultBean.setData(temp);
        return detailResultBean;
    }
}
