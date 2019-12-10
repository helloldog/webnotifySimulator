package mine.BaseLib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by weijl on 2019-7-18.
 */
public class RandomCases {

    public static String plateNumber() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randdata = new Random();
        int data;
        sb.append("桂");
        for (int i = 0; i < 6; i++) {
            int index;
            if (i == 0) {
                index = 1;
            } else {
                index = rand.nextInt(2);
            }

            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        String result = sb.toString();

        return result;
    }

    public static int RandomNumber(int start,int end) {

        Random rand = new Random();
       int  res = rand.nextInt(end)+start;
       return  res;
    }


    public static String  RandomTime(int hour,int min,int sec) {
        Random rand = new Random();
        String orderDate = "";
        try {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sf.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(nowDate));
            if(hour==99)
                cal.add(Calendar.HOUR_OF_DAY,-rand.nextInt(99));
            else
                cal.add(Calendar.HOUR_OF_DAY,hour);
            if(min==99)
                cal.add(Calendar.MINUTE,-rand.nextInt(59));
            else
                cal.add(Calendar.MINUTE,min);
            if(sec==99)
                cal.add(Calendar.SECOND,-rand.nextInt(59));
            else
                cal.add(Calendar.SECOND,sec);

            //HOUR_OF_DAY加减小时，DAY_OF_YEAR加减天，MINUTE加减分钟，SECOND加减秒，+、-决定时间的加减
            orderDate = sf.format(cal.getTime());

        } catch (Exception e) {
        }
        return orderDate;
    }



}
