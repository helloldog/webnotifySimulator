package mine.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mine.BaseLib.BaseLib;
import org.java_websocket.client.WebSocketClient;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;


import static mine.BaseLib.AES.VI;
import static mine.BaseLib.AES.encryptAES7;
import static mine.MyAsync.AsyncTaskService.WebSockectAsyncTask;
import static mine.MyWebSocketClientTest.MyWebSocketClient.RunStart;


@RestController
@Slf4j
public class TestController {




    @RequestMapping(value = "/LoginWebnotify",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String LoginWebnotify(@RequestBody String request) throws Exception {
        JSONObject result = new JSONObject();
        result = result.parseObject(request);
        String url=  result.get("url").toString();
        String sessionId= result.get("sessionId").toString();
        String parkingId= result.get("parkingId").toString();
        WebSockectAsyncTask(url,sessionId,parkingId);
           return  "成功";
    }

    @RequestMapping(value = "/LogoutWs",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public  String LogoutWs(@RequestBody String request) throws Exception {

       return "";
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




}