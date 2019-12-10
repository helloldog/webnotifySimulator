package mine.timer;

import lombok.extern.slf4j.Slf4j;
import mine.BaseLib.BaseLib;
import org.java_websocket.client.WebSocketClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static mine.Application.*;
import static mine.controller.TestController.MySn;


@Slf4j
@Component
public class TimerTest {


      @Scheduled(fixedRate = 5000)
    public  void  timer1()  {

          try {
              String Sn1=MySn.getSN1();
              String Sn2=MySn.getSN2();
              String Sn3=MySn.getSN3();
              String Sn4=MySn.getSN4();
              String Sn5=MySn.getSN5();
              String Sn6=MySn.getSN6();


              if(Sn1.length()>0)
                  HB(clientSn1,MySn.getSN1(),"01");

              if(Sn2.length()>0)
                  HB(clientSn2,MySn.getSN2(),"02");

              if(Sn3.length()>0)
                  HB(clientSn3,MySn.getSN3(),"03");

              if(Sn4.length()>0)
                  HB(clientSn4,MySn.getSN4(),"04");

              if(Sn5.length()>0)
                  HB(clientSn5,MySn.getSN5(),"05");

              if(Sn6.length()>0)
                  HB(clientSn6,MySn.getSN6(),"06");



          }catch(Exception e) {
             // e.printStackTrace();

          }
      }

      public  void HB(WebSocketClient client,String Sn,String Num){

          String requestId=String.valueOf(new Date().getTime())+Num;
          String HB = "{\n" +
                  "\"command\":\"HB\",\n" +
                  "\"requestId\":\"" + requestId + "\",\n" +
                  "\"deviceSn\":\"" + Sn + "\",\n" +
                  "\"time\":\"" + BaseLib.MyTime() + "\",\n" +
                  "\"data\":\"\"\n" +
                  "}";
          client.send(HB);

      }


}
