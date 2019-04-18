package com.smallprogram.moviesinterface.service;



import com.smallprogram.moviesinterface.bean.DetailResultBean;

import java.text.ParseException;

public interface IAddMovies {

    public DetailResultBean addMovies(String title, float average, String image, String pubdate, String year, String directors, String summary, String type, String actors) throws ParseException;
}
