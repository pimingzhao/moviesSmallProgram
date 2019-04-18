package com.smallprogram.moviesinterface.service;

import com.smallprogram.moviesinterface.bean.QueryResultBean;

public interface IQueryMovies {
    public QueryResultBean qeuryMovies(String title, int start, int length);
}
