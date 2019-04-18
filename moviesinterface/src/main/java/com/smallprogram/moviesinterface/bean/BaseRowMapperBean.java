package com.smallprogram.moviesinterface.bean;

import com.smallprogram.moviesinterface.utils.JavaUtil;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseRowMapperBean<T> implements RowMapper<T> {

    private Class<?> targeClazz;

    public BaseRowMapperBean(){}

    public BaseRowMapperBean(Class<?> targeClazz){
        this.targeClazz = targeClazz;
    }

    @Override
    public T mapRow(ResultSet ret, int index) throws SQLException{
        Object returnObj = null;
        try{
            returnObj = this.targeClazz.newInstance();
            Field[] fields = this.targeClazz.getDeclaredFields();
            if(fields == null){
                return null;
            }
            for(int i=0;i<fields.length;i++){
                String fieldName = fields[i].getName();
                Class fieldClass = fields[i].getType();
                String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);//setName
                Method method = this.targeClazz.getDeclaredMethod(methodName,new Class[]{fieldClass});//获取类的所有方法
                Object fieldValue = ret.getObject(fieldName);//获取数据库中的数据名的对应数据值
                if(fieldValue != null){
                    method.invoke(returnObj,ret.getObject(fieldName));//
                }else{
                    method.invoke(returnObj, JavaUtil.toString(fieldValue));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T)returnObj;
    }
}