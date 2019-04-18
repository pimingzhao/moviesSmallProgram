package com.smallprogram.moviesinterface.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "返回响应数据")
public class DetailResultBean<T> implements Serializable {

    public DetailResultBean(){}

    public DetailResultBean(String flag,T data){
        this.flag=flag;
        this.data=data;
    }

    @ApiModelProperty(value = "异常信息")
    private String exception_msg;

    @ApiModelProperty(value = "是否成功")
    private String flag;  //1：查询成功。2：查询成功，无数据。3：查询异常

    @ApiModelProperty(value = "返回数据")
    private T data;

    public String getException_msg() {
        return exception_msg;
    }

    public void setException_msg(String exception_msg) {
        this.exception_msg = exception_msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
