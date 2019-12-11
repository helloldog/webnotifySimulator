package mine.MyAsync;


import com.alipay.api.java_websocket.enums.ReadyState;
import lombok.extern.log4j.Log4j;
import mine.MyWebSocketClientTest.MyWebSocketClient;
import org.java_websocket.WebSocket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Log4j
@Service
// 线程执行任务类
public class AsyncTaskService {


    @Async
    // 表明是异步方法
    public void executeAsyncTask(int msg) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开启新线程执行" + msg);
        while (true) {
            Thread.sleep(1000);
            System.out.println("我是" + Thread.currentThread().getName());
        }

    }



    @Async
    public static void WebSockectAsyncTask(String url,String sessionId, String parkingId) throws InterruptedException, IOException, URISyntaxException {
        System.out.println(Thread.currentThread().getName() + "开启新线程执行" + sessionId);
        MyWebSocketClient client = new MyWebSocketClient(url);
        client.connect();
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            log.info("尝试打开连接中...");
            Thread.sleep(1000);
        }
        log.info("建立websocket连接成功");
        String ConnText="{\n" +
                "          \"eventType\":\"Subscribe\",\n" +
                "          \"evts\":[\"RefreshChannel\",\"InParkingPending\",\"OutParkingPending\",\"Area2OtherPending\",\"Ready2Pay\",\"PayFinished\",\"ParkingOrderFinish\",\"ParkingOrderCreated\"],\n" +
                "          \"sessionId\":\"%s\",\n" +
                "          \"parkingId\": \"%s\"\n" +
                "      }";
        ConnText=String.format(ConnText,sessionId,parkingId);
        System.out.println(ConnText);
        client.send(ConnText);


    }



}