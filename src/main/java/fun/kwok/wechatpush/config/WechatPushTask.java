package fun.kwok.wechatpush.config;

import fun.kwok.wechatpush.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class WechatPushTask implements SchedulingConfigurer {

    @Value("${Task.cron}")
    private String cron;

    @Autowired
    MainService mainService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> mainService.SendTemplateMsg(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
