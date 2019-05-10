import com.zq.learn.stream.StreamDemoApplication;
import com.zq.learn.stream.sender.SinkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019/4/29下午3:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamDemoApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloApplicationTests {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void testSender(){
        sinkSender.output().send(MessageBuilder.withPayload("Hello Spring Cloud").build());
    }
}
