package com.smallprogram.moviesinterface.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "返回响应数据")
public class ResultBean<T> implements Serializable {

    public ResultBean(){}

    public ResultBean(String flag,T data,Integer total,String type){
        this.flag=flag;
        this.data=data;
        this.total=total;
        this.type=type;
    }

    @ApiModelProperty(value = "异常信息")
    private String exception_msg;

    @ApiModelProperty(value = "是否成功")
    private String flag;  //1：查询成功。2：查询成功，无数据。3：查询异常

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "返回数据总数")
    private Integer total;

    @ApiModelProperty(value = "电影类型")
    private String type;

    @ApiModelProperty(value = "请求数据的起始值")
    private Integer start;

    @ApiModelProperty(value = "一次请求的数据量")
    private Integer count;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
