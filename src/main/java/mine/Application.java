package mine;

import mine.BaseLib.BaseLib;
import org.java_websocket.client.WebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling


public class Application {


    /**
     * 资源映射路径
     */
    @Configuration
    public class MyWebAppConfigurer implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations(BaseLib.getPropertyString("test.zxingpic.save.locations"));
        }
    }




    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        SpringApplication.run(Application.class,args);
    }
}
