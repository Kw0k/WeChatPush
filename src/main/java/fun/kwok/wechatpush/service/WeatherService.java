package fun.kwok.wechatpush.service;


import com.alibaba.fastjson.JSONObject;
import fun.kwok.wechatpush.entity.Weather;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {


    @Value("${Weather.appid}")
    private String appid; //天气api的appid
    @Value("${Weather.appsecret}")
    private String appsecret; //天气api的appsecret
    @Value("${Weather.city}")
    private String city;
    public Weather getTodayWeather()  {
        Weather weather=new Weather();
        OkHttpClient client=new OkHttpClient().newBuilder().build();
        Request request=new Request.Builder()
                .url("https://v0.yiketianqi.com/api?unescape=1&version=v61&" +
                        "appid="+appid +
                        "&appsecret=" + appsecret+
                        "&city="+city).method("GET",null).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            weather= JSONObject.parseObject(response.body().string(), Weather.class);
        } catch (IOException e) {
            System.out.println(e);
        }
        return weather;
    }
}
