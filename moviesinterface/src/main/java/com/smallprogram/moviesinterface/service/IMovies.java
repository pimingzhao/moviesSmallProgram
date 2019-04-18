package com.smallprogram.moviesinterface.service;

import com.smallprogram.moviesinterface.bean.ResultBean;

public interface IMovies {

    public ResultBean query(int start,int length,String type);
}
