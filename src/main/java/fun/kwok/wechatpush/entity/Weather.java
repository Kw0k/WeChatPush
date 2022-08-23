package fun.kwok.wechatpush.entity;

import lombok.Data;

@Data
public class Weather {
    private String date;
    private String week;
    private String city;
    private String wea;
    private String tem1;
    private String tem2;
    private String win;
    private String win_speed;
    private String air_tips;
    private String air_level;
}
