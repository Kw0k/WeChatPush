package fun.kwok.wechatpush.service;

import fun.kwok.wechatpush.entity.Weather;
import fun.kwok.wechatpush.utils.DateUtil;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class MainService {

    @Autowired
    WeatherService weatherService;
    @Value("${WeChat.appid}")
    private String appid;
    @Value("${WeChat.appsecret}")
    private String appsecret;
    @Value("${WeChat.template_id}")
    private String templateId;
    @Value("${WeChat.openid}")
    private String openid;
    @Value("${Date.anniversary.date}")
    private Date anniversary;
    @Value("${Date.anniversary.isChineseCalendar}")
    private Boolean anniversaryIsChineseCalendar;
    @Value("${Date.boyBirthday.date}")
    private Date boyBirthday;
    @Value("${Date.boyBirthday.name}")
    private String boyName;
    @Value("${Date.boyBirthday.isChineseCalendar}")
    private Boolean boyBirthdayIsChineseCalendar;
    @Value("${Date.girlBirthday.date}")
    private Date girlBirthday;
    @Value("${Date.girlBirthday.name}")
    private String girlName;
    @Value("${Date.girlBirthday.isChineseCalendar}")
    private Boolean girlBirthdayIsChineseCalendar;


    public void SendTemplateMsg(){
        //数据准备
        Weather weather=weatherService.getTodayWeather();
        int boyBirthdayDays=DateUtil.getBirthdayDays(boyBirthday,boyBirthdayIsChineseCalendar);
        int girlBirthdayDays=DateUtil.getBirthdayDays(girlBirthday,girlBirthdayIsChineseCalendar);
        int anniversaryDays=DateUtil.getAnniversaryDays(anniversary,anniversaryIsChineseCalendar);
        Calendar today=Calendar.getInstance();
        //微信推送配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appid);
        wxStorage.setSecret(appsecret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)
                .templateId(templateId)
                .build();
        templateMessage.addData(new WxMpTemplateData("date",weather.getDate()+" "+weather.getWeek()));
        templateMessage.addData(new WxMpTemplateData("weather",weather.getCity()+"，"+weather.getWea()+"，"+weather.getWin()+" "+weather.getWin_speed()+"。"));
        templateMessage.addData(new WxMpTemplateData("maxTem",weather.getTem1(),"#CC3300"));
        templateMessage.addData(new WxMpTemplateData("minTem",weather.getTem2(),"#33CCFF"));
        templateMessage.addData(new WxMpTemplateData("air_level",weather.getAir_level()));
        templateMessage.addData(new WxMpTemplateData("air_tips",weather.getAir_tips(),"#99CC99"));
        templateMessage.addData(new WxMpTemplateData("anniversary",anniversaryDays+"","#FF6699"));
        templateMessage.addData(new WxMpTemplateData("boyName",boyName,"#33CCCC"));
        templateMessage.addData(new WxMpTemplateData("girlName",girlName,"#FF99CC"));
        templateMessage.addData(new WxMpTemplateData("girlBirthday",girlBirthdayDays+"","#FF6699"));
        templateMessage.addData(new WxMpTemplateData("boyBirthday",boyBirthdayDays+"","#FF6699"));

       String tips="";
       if (DateUtil.isAnniversary(anniversary,anniversaryIsChineseCalendar)){
           //周年纪念日
           int count=DateUtil.getAnniversaryDays(anniversary,anniversaryIsChineseCalendar)/365;
           tips+="\n今天是"+count+"周年纪念日～";
       }
       if (boyBirthdayDays==0){
           tips+="\n今天是"+boyName+"生日，生日快乐！";
       }
        if (girlBirthdayDays==0){
            tips+="\n今天是"+girlName+"生日，生日快乐！";
        }
        templateMessage.addData(new WxMpTemplateData("tips",tips,"#FF6699"));
        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
