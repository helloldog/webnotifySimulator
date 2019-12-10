package mine.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by weijl on 2019-09-06.
 */
public class commutils {

    public static String NowTime() throws Exception {
        String VI = null;
        try{
            Date date =new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sf.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(nowDate));
            // cal.add(Calendar.DAY_OF_YEAR,-70);//HOUR_OF_DAY加减小时，DAY_OF_YEAR加减天，MINUTE加减分钟，SECOND加减秒，+、-决定时间的加减
            VI = sf.format(cal.getTime());
            //   vars.put("key",orderDate);//输出到jmeter变量
        }
        catch(Exception e){

        }
        return  VI;
    }

    public static boolean isJson(String content) {
        try {
            JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
