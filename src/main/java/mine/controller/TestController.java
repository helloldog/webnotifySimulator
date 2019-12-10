package mine.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mine.BaseLib.BaseLib;
import mine.Data.AllData;
import mine.utils.AES;
import org.java_websocket.client.WebSocketClient;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;

import static mine.Application.*;
import static mine.MyWebSocketClientTest.MyWebSocketClient.RunStart;
import static mine.utils.AES.VI;
import static mine.utils.AES.encryptAES7;

@RestController
@Slf4j
public class TestController {
   public static AllData.devSN  MySn= new AllData.devSN();


    @RequestMapping(value = "/SendToDev",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String getShopInJSON(@RequestBody String request) throws Exception {

        JSONObject result = new JSONObject();
        result = result.parseObject(request);

        String carno = result.get("carno").toString();
        String time = result.get("time").toString();
        String confid =result.get("confid").toString();
        String carNOC=result.get("carNOC").toString();
        String  picN=result.get("picN").toString();
        String  Sn=result.get("Sn").toString();
        String iv=VI();
        String data="{\"carNO\":\""+carno+"\",\"carNOC\":\""+carNOC+"\",\"confid\":"+confid+",\"memo\":\"\",\"picN\":\""+picN+"\",\"triggT\":\""+time+"\"}";
        System.out.println(data);
        String reqid=String.valueOf(new Date().getTime());
        String afterencryptdata=encryptAES7(data,"F7A0B971B199FD2A",iv);
String sendmsg="{\"command\":\"Result\",\"data\":\""+afterencryptdata+"\",\"deviceSn\":\""+Sn+"\",\"requestId\":\""+reqid+"\",\"time\":\""+ BaseLib.MyTime()+"\"}";
        if(Sn.equals(MySn.getSN1())){
            clientSn1.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN2())){
            clientSn2.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN3())){
            clientSn3.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN4())){
            clientSn4.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN5())){
            clientSn5.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN6())){
            clientSn6.send(sendmsg);
        }

        else {
            return "填入的Sn与登录/LoginWs时的不一致";
        }


        System.out.println(sendmsg);

       log.info("向服务发送res结果时间:"+BaseLib.MyTimemin());

          String    myrespone = "发送成功！";
        return  myrespone;
    }

    @RequestMapping(value = "/LoginWs",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String LoginWs(@RequestBody String request) throws Exception {

        JSONObject result = new JSONObject();
        result = result.parseObject(request);

        String Sn1 =  result.get("Sn1").toString();
        String Sn2 =  result.get("Sn2").toString();
        String Address=result.get("Address").toString();
        MySn.setSN1(Sn1);
        MySn.setSN2(Sn2);
        MySn.setAddress(Address);

        clientSn1=RunStart(Address);
        Conn(clientSn1,Sn1);

        clientSn2=RunStart(Address);
        Conn(clientSn2,Sn2);

        if(request.contains("Sn3")) {
            String Sn3 = result.get("Sn3").toString();
            MySn.setSN3(Sn3);
            clientSn3=RunStart(Address);
            Conn(clientSn3,Sn3);
        }
        if(request.contains("Sn4")) {

            String Sn4 = result.get("Sn4").toString();
            MySn.setSN4(Sn4);
            clientSn4=RunStart(Address);
            Conn(clientSn3,Sn4);
        }
        if(request.contains("Sn5")) {
            String Sn5 = result.get("Sn5").toString();
            MySn.setSN5(Sn5);
            clientSn5=RunStart(Address);
            Conn(clientSn3,Sn5);
        }
        if(request.contains("Sn6")) {
            String Sn6 = result.get("Sn6").toString();
            MySn.setSN6(Sn6);
            clientSn6=RunStart(Address);
            Conn(clientSn3,Sn6);
        }




        String    myrespone = "登录成功！SN1="+MySn.getSN1()+"SN2="+MySn.getSN2();
        return  myrespone;
    }

    @RequestMapping(value = "/LogoutWs",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String LogoutWs(@RequestBody String request) throws Exception {

        String Sn1=MySn.getSN1();
        String Sn2=MySn.getSN2();
        String Sn3=MySn.getSN3();
        String Sn4=MySn.getSN4();
        String Sn5=MySn.getSN5();
        String Sn6=MySn.getSN6();


        if(Sn1.length()>0)
            clientSn1.close();

        if(Sn2.length()>0)
            clientSn2.close();

        if(Sn3.length()>0)
            clientSn3.close();

        if(Sn4.length()>0)
            clientSn4.close();

        if(Sn5.length()>0)
            clientSn5.close();

        if(Sn6.length()>0)
            clientSn6.close();


        MySn.setSN1("");
        MySn.setSN2("");
        MySn.setSN3("");
        MySn.setSN4("");
        MySn.setSN5("");
        MySn.setSN6("");



        String    myrespone = "关闭所有连接成功！";
        return  myrespone;
    }

    @RequestMapping(value = "/ClearRes",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String ClearRes(@RequestBody String request) throws Exception {


        File file = new File(BaseLib.getPropertyString("test.zxingpic.save")+"Res.txt");
        if (file.exists()) {
            file.delete();
            return "文件已经被成功删除";
        }
        else{
            return "请重试";
        }

    }






    public  void Conn(WebSocketClient client,String sn ) throws Exception {
        String data ="{\"devInfo\":\"devIP~172.18.24.9,devSubnet~255.255.0.0,devGateway~172.18.9.28,devMask~0C:F4:05:F0:72:B7,appVer~172.18.24.9,ubootVer~172.18.24.9,algorithmVer~ALG[REV:1875 2019/06/28 16:54:29],linuxVer~172.18.24.9,firmwareVer~172.18.24.9\"}";

        String conn1="{\"command\":\"Conn\",\"data\":\""+AES.encryptAES7(data,"F7A0B971B199FD2A",VI())+"\",\"deviceSn\":\""+sn+"\",\"requestId\":\""+ new Date().getTime()+"\",\"time\":\""+BaseLib.MyTime()+"\"}";
        System.out.println("TST:"+conn1);
        client.send(conn1);


    }










    @PostMapping("/Decrypt")
    @ResponseBody
    public  String decryptAES7(@RequestBody String request) throws Exception {
             String  AfterdecryptAES7 = AES.decryptAES7(request,"F7A0B971B199FD2A",VI());
        System.out.println(AfterdecryptAES7);
        return  AfterdecryptAES7;
    }

    @GetMapping("/RemCtrl/{sn}")
    public void business(@PathVariable("sn") String Sn) {
        String requestId=String.valueOf(new Date().getTime());
        String sendmsg="{\n" +
                "\"command\":\"RemCtrl\",\n" +
                "\"requestId\":\"" + requestId + "\",\n" +
                "\"deviceSn\":\"" + Sn + "\",\n" +
                "\"time\":\"" + BaseLib.MyTime() + "\",\n" +
                "\"data\":\"\"\n" +
                "}";
        if(Sn.equals(MySn.getSN1())){
            clientSn1.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN2())){
            clientSn2.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN3())){
            clientSn3.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN4())){
            clientSn4.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN5())){
            clientSn5.send(sendmsg);
        }
        else if(Sn.equals(MySn.getSN6())){
            clientSn6.send(sendmsg);
        }
    }


}