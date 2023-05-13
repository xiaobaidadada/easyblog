package fun.xb.web.main;



import fun.xb.easySqlorm.service.EasyOrmSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class Schedule {

    @Autowired
    EasyOrmSession easySession;


    //3.添加定时任务 每小时执行一次
//    @Scheduled(cron = "0 0 0/1 * * ?")
    //或直接指定时间间隔，例如：5秒0 * * * * ?
//    @Scheduled(fixedRate=1000)
//    @Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks()   {
       a a=new a();
        List<a> t=easySession.select("select * from a", a.class);
        t.forEach(v->{
            System.out.println(v.getName());
        });
    }


}
