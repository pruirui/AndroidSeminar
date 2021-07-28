package com.xiao.musicplayer.util;

import com.xiao.musicplayer.net.HttpTask;

import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;


/**
 * @author 22790
 *
 */
public class StringUtil {
    /**
     * 将指定的字符串传化为时间格式：
     * 2019-09-01 01:01
     * @param time 1
     * @return 1
     */
    public static String transformToDate(int...time) {
        if (time.length < 5)
            return "";

        String[] s = new String[5];
        for (int i = 0; i <time.length; i++){
            s[i]= String.valueOf(time[i]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length; i++) {
            if (s[i].length() == 1)
                s[i] = "0".concat(s[i]);
        }
        sb.append(s[0].concat("-"));
        sb.append(s[1].concat("-"));
        sb.append(s[2].concat(" "));;
        sb.append(s[3].concat(":"));;
        sb.append(s[4]);

        return sb.toString();
    }

    public static String transformToLogin(String  phone,String password){
        return "phone="+phone+"&"+"password="+password;
    }
    public static String transformToRegister(String  username,String phone,String password){
        return transformToLogin(phone,password)+"&"+"username="+username;
    }
    public static String transformToAlter(String  username,String phone,String password){
        return transformToLogin(phone,password)+"&"+"username="+username;
    }

    public static String transformToPOST(Map<String,String> values){
        StringBuffer stringBuffer = new StringBuffer();

        for (Map.Entry<String,String> _t : values.entrySet()){
            stringBuffer.append("&"+_t.getKey()+"="+_t.getValue());
        }
        return stringBuffer.substring(1);
    }

    public static float[] transformToFloat(String ... strings) {
        float[] f = new float[strings.length];
        for (int i = 0; i < strings.length; i++) {
            try {
                f[i] = Float.parseFloat(strings[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    /**
     * 将指定的字符串转化为符合服务器注册或登录要求的格式
     * @param data 传入登录需要的参数：name、username、password、identity
     * @return f 传出：name_username_password
     */
    public static String transformToAccount(String...data) {
        int identity;
        if (data.length == 4 && data[1].trim().length() == 11) {
            if ("student".equals(data[3]))
                identity = 0;
            else
                identity = 1;
            return String.format(Locale.getDefault(),
                    "{\"name\": \"%s\", \"username\": \"%s\", \"password\": \"%s\", \"identity\": %d}", data[0].trim(), data[1].trim(), data[2].trim(), identity);
        } else if (data.length == 2 && data[0].trim().length() == 11) {
            return String.format(Locale.getDefault(),
                    "{\"username\": \"%s\", \"password\": \"%s\"}", data[0].trim(), data[1]);
        }
        return "Null" + data[2].trim().length();
    }

    /**
     * 判断字符串s是否为电话号码
     * @param s 要判断的字符串
     * @return 若s为空或含有非数字或不为11位则返回false
     */
    public static boolean isPhone(String s) {
        return !(isEmpty(s) || !isNumber(s) || s.length() != 11);
    }

    /**
     * 判断字符串s是否为空（s内容全为空格或者无内容）
     * @param s 待判断的字符串
     * @return 为空返回true，不为空返回false
     */
    public static boolean isEmpty(String s) {
        return (s == null || "".equals(s.trim()));
    }

    /**
     * 将字符串s中汉字部分转换成utf-8编码
     * 4e00-9fef
     * @param s 1
     * @return 返回转换后的字符串
     */
    public static String parseChineseToUtf8(String s) {
        char[] ch = s.toCharArray();
        try {
            for (char c : ch) {
                if (c > 0x4e00 && c < 0x9fef) {
                    String sub = null;
                    sub = URLEncoder.encode(Character.valueOf(c).toString(), "utf-8");

                    s = s.replaceAll(Character.valueOf(c).toString(), sub);
                } else if (c == '+')
                    s = s.replaceAll("\\+", "%2B");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 判断指定字符串是否全为数字
     * @param s 待判断的字符串
     * @return 1
     */
    public static boolean isNumber(String s) {
        int len = s.length();
        char[] ss = s.toCharArray();

        for (int i = 0; i < len; i++)
            if (!Character.isDigit(ss[i]))
                return false;

        return true;
    }

    /**
     * 将String型转换为Int
     * @param s 字符串
     * @return  如果为空或者不是数字就返回Integer.MIN_VALUE
     * 			否则转换为数字
     */
    public static int parseStringToInt(String s) {
        if(isEmpty(s)||!isNumber(s)) {
            return Integer.MIN_VALUE;
        }
        else {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Integer.MIN_VALUE;
            }
        }
    }

    /**
     * 将字符串转换为短整型
     * @param s  字符串
     * @return 如果为空或者不是数字就返回Short.MIN_VALUE
     * 		     否则转换为短整型
     */
    public static short parseStringToShort(String s) {
        if(isEmpty(s)||!isNumber(s)) {
            return Short.MIN_VALUE;
        }else {
            try {
                return Short.parseShort(s);
            }catch(Exception e) {
                e.printStackTrace();
                return Short.MIN_VALUE;
            }
        }
    }
    /**
     * 将字符串转换为double
     * @param s 字符串
     * @return 如果为空或者不是数字就返回Double.MIN_VALUE
     * 		     否则就返回Double
     */
    public static double parseStringToDouble(String s) {
        if(isEmpty(s)) {
            return Double.MIN_VALUE;
        }else {
            try {
                return Double.parseDouble(s);
            }catch(Exception e) {
                e.printStackTrace();
                return Double.MIN_VALUE;
            }
        }
    }

    public static Integer splitResult(String result) {
        int res = -1;
        if (result.contains("true"))
            res = 1;
        else if (result.contains("false"))
            res = 0;
        else if (result.contains(HttpTask.CONNECT_OR_READ_TIMEOUT))
            res = 2;
        return res;
    }
}
