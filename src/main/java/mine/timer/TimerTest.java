package mine.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TimerTest {


      @Scheduled(fixedRate = 5000)
    public  void  timer1()  {



      }

}
