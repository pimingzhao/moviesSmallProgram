package com.smallprogram.moviesinterface.bean.domain;

import java.util.List;

public class QueryUserBean {

    private String avatar;

    private String name;

    private List collection;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getCollection() {
        return collection;
    }

    public void setCollection(List collection) {
        this.collection = collection;
    }
}
