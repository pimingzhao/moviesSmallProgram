package com.smallprogram.moviesinterface.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaUtil {

    public static String toString(Object o){
        if(o ==null || "null".equals(o)){
            return "";
        }else
            return o.toString();
    }
    /**
     *
     * @param str  要转换的字符串
     * @param memo  转换的字符串说明备注
     * @return
     */
    public static Integer converStrToInterger(String str,String memo){
        int strInt = 1;
        try{
            if(str == null)
                throw new Exception(memo+"字符为Null!!");
            else
                strInt = Integer.parseInt(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return strInt;
    }
    public static String StringToDate(String str) {
        String[] newStr = str.split("/");
        String yr = newStr[0];
        String y = newStr[1];
        String r = newStr[2].split(" ")[0];
        DateFormat year = new SimpleDateFormat("yyyy");
        DateFormat yue = new SimpleDateFormat("MM");
        DateFormat day = new SimpleDateFormat("dd");
        String Y = year.format(System.currentTimeMillis());
        String D = day.format(System.currentTimeMillis());
        String Yue = yue.format(System.currentTimeMillis());
        if(yr.equals(Y)){
            if(y.equals(Yue)){
                switch (Integer.valueOf(D)-Integer.valueOf(r)){
                    case 0:
                        str = "今天"+newStr[2].split(" ")[1];
                        break;
                    case 1:
                        str = "昨天"+newStr[2].split(" ")[1];
                        break;
                    case 2:
                        str = "两天前"+newStr[2].split(" ")[1];
                        break;
                    case 3:
                        str = "三天前"+newStr[2].split(" ")[1];
                        break;
                    case 4:
                        str = "四天前"+newStr[2].split(" ")[1];
                        break;
                    case 5:
                        str = "五天前"+newStr[2].split(" ")[1];
                        break;
                    default:
                        str = Integer.valueOf(y) + "/" + newStr[2];
                        break;
                }
            } else {
                str = Integer.valueOf(y) + "/" + newStr[2];
            }
        }
        return str;
    }
}
