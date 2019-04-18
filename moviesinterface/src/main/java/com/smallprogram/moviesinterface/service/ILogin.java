package com.smallprogram.moviesinterface.service;

import com.smallprogram.moviesinterface.bean.DetailResultBean;

public interface ILogin {

    public DetailResultBean addUser(String uid,String name,String avatar);
}
