package mine.BaseLib;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by weijl on 2019-7-17.
 */
public class BaseLib {
    public static String getPropertyString (String key){//读取配置文件
        String propertyFileName = System.getProperty("user.dir")+"/src/main/resources/application.properties";
        ///src/main/resources

        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(propertyFileName));
        }catch (IOException e1){
            e1.printStackTrace();
        }
        if(key == null || key.equals("")|| key.equals("null")){
            return "";
        }
        String result = "";
        try {
            result = properties.getProperty(key);
        }catch (MissingResourceException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        BaseLib.readToBuffer(sb, filePath);
        return sb.toString();
    }

    public  static  String lenthTime(int initNum){
        int num;
        int hour = 0;
        int minute = 0;
        int second = 0;
        num = initNum;

        second = num % 60;
        num -= second;
        if(num > 0) {
            num /= 60;
            minute = num % 60;
            num -= minute;
            if(num > 0) {
                hour = num / 60;
            }
        }
         String lengthTime= String.valueOf(hour)+"小时"+String.valueOf(minute)+"分钟"+String.valueOf(second)+"秒";

        return lengthTime;
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
                String s = encode;
                return s; //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public  static  String  MyTime(){

        String requestId = UUID.randomUUID().toString();
        String orderDate="";

        try{
            Date date =new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sf.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(nowDate));

            orderDate = sf.format(cal.getTime());

        }
        catch(Exception e){

        }
        return  orderDate;
    }
    public  static  String  MyTimemin(){

        String requestId = UUID.randomUUID().toString();
        String orderDate="";

        try{
            Date date =new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            String nowDate = sf.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(nowDate));

            orderDate = sf.format(cal.getTime());

        }
        catch(Exception e){

        }
        return  orderDate;
    }




}
