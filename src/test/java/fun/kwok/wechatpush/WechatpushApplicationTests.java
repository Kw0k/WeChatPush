package fun.kwok.wechatpush;

import com.sun.tools.javadoc.Main;
import fun.kwok.wechatpush.service.MainService;
import fun.kwok.wechatpush.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WechatpushApplicationTests {

    @Autowired
    MainService mainService;

    @Test
    void contextLoads() {
        mainService.SendTemplateMsg();
    }
}
