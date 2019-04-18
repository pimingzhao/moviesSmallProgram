package com.smallprogram.moviesinterface.service;

import com.smallprogram.moviesinterface.bean.DetailResultBean;

public interface IComments {

    public DetailResultBean addComments(int id,String uid,String content,String dates);
}
