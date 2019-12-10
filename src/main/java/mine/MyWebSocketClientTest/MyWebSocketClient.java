package mine.MyWebSocketClientTest;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import mine.BaseLib.BaseLib;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Scanner;

import static io.restassured.RestAssured.given;

public  class MyWebSocketClient extends WebSocketClient{



    public   MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }


    public void onOpen(ServerHandshake shake) {

        System.out.println("握手...");
        for(Iterator<String> it=shake.iterateHttpFields();it.hasNext();) {
            String key = it.next();
            System.out.println(key+":"+shake.getFieldValue(key));
        }


    }

    public void wrtothetxt(String txt){

        FileWriter fw = null;
        try {
            fw = new FileWriter(BaseLib.getPropertyString("test.zxingpic.save")+"Res.txt",true);
            //写入换行
            fw.write("\n");//Windows平台下用\r\n，Linux/Unix平台下用\n

            //续写一个hello world!
            fw.write(txt);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public  static String OnMsg="";
    public void onMessage(String paramString) {

              wrtothetxt(BaseLib.MyTimemin()+":"+paramString);

              if(!paramString.contains("Pong"))
        System.out.println(paramString);
     if(paramString.contains("RefreshChannel")){
         Response response = given()
                 .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                 .header("sessionid","d291d3mffd95bc")

         //       .get("https://entry-mch.platformcenter.net/channel/Cbi69ki59040b35/order?parkingId=Pbi69kh54524e67");//A入
              .get("https://entry-mch.platformcenter.net/channel/Cbi69kie03e0aff/order?parkingId=Pbi69kh54524e67");//B入
         System.out.println(response.asString());
     }


    }


    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("关闭...");

    }


    public void onError(Exception e) {
        System.out.println("异常"+e);

    }
        public static MyWebSocketClient WebSocketClientStart(String url) throws InterruptedException, URISyntaxException {
            MyWebSocketClient client = new MyWebSocketClient(url);
            client.connect();
            int conncount=0;

            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                conncount++;
                System.out.println("尝试打开连接中...");
                Thread.sleep(1000);
                if(conncount>10){
                    System.out.println("建立websocket超时，请检查服务端是否正常");
                    break;
                }
            }

        return client;
        }
    public static void  WebSocketClientSend(MyWebSocketClient client, String msg){
        client.send(msg);
    }

    public static void  WebSocketClientClose(MyWebSocketClient client){

        client.close();


    }

    public static void Run() throws URISyntaxException, InterruptedException {
        MyWebSocketClient client = new MyWebSocketClient("ws://172.18.2.92:8082/deviceWs");
        client.connect();
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("尝试打开连接中...");
            Thread.sleep(1000);
        }
        System.out.println("建立websocket连接成功");
        client.send("连接后第一次测试");
        while (true) {
            String str1 = "";
            Scanner scan = new Scanner(System.in);
            do {
                String string = scan.nextLine();
                if (string.equals("")) {
                    break;
                }
                str1=str1+string;
            } while (true);
            System.out.println("输入的数据为：" + str1);
            client.send(str1);


        }
    }


    public static MyWebSocketClient RunStart(String ws) throws URISyntaxException, InterruptedException {
        MyWebSocketClient client = new MyWebSocketClient(ws);//ws://172.18.2.92:8082/deviceWs
        client.connect();
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("尝试打开连接中...");
            Thread.sleep(1000);
        }
        System.out.println("建立websocket连接成功");

        return  client;

    }





//
    public static void main(String[] args) throws Exception {
        MyWebSocketClient client;
        client=conn();

       while (true){
           client.send("{\n" +
                   "          \"eventType\":\"Ping\"\n" +
                   "      }");
           Thread.sleep(5000);
           //System.out.println( BaseLib.MyTimemin()+"  连接状态："+client.isClosed());
           if(client.isClosed()){
               System.out.println("发现连接断开。。。。。。");
               client=conn();

           }
       }


//        while (true) {
//            String str1 = "";
//            Scanner scan = new Scanner(System.in);
//            do {
//                String string = scan.nextLine();
//                if (string.equals("")) {
//                    break;
//                }
//                str1=str1+string;
//            } while (true);
//          log.info("输入的数据为：" + str1);
//            client.send(str1);
//
//        }

//       MyWebSocketClient client= RunStart("ws://172.18.2.92:8082/deviceWs");
//                while (true) {
//            String str1 = "";
//            Scanner scan = new Scanner(System.in);
//            do {
//                String string = scan.nextLine();
//                if (string.equals("")) {
//                    break;
//                }
//                str1=str1+string;
//            } while (true);
//          log.info("输入的数据为：" + str1);
//            client.send(str1);
//
//        }


    }


    public static MyWebSocketClient  conn() throws URISyntaxException, InterruptedException {
        MyWebSocketClient client = new MyWebSocketClient("wss://entry-web-notify.platformcenter.net/webWs");
        client.connect();
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("尝试打开连接中...");
            Thread.sleep(1000);
        }
        System.out.println("建立websocket连接成功");
        client.send("{\n" +
                "          \"eventType\":\"Subscribe\",\n" +
                "          \"evts\":[\"RefreshChannel\",\"InParkingPending\",\"OutParkingPending\",\"Area2OtherPending\",\"Ready2Pay\",\"PayFinished\",\"ParkingOrderFinish\",\"ParkingOrderCreated\"],\n" +
                "          \"sessionId\":\"d291d3mffd95bc\",\n" +
                "          \"parkingId\": \"Pbi69kh54524e67\"\n" +
                "      }");
        client.send("{\n" +
                "          \"eventType\":\"Ping\"\n" +
                "      }");

        return client;
    }

}