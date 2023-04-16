package fun.xb.basefunction.job;
 
import fun.xb.basefunction.constant.cache;
import fun.xb.basefunction.entity.dict_entity;
import fun.xb.easySqlorm.service.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务的使用
 * @author pan_junbiao
 **/
@Component
public class Task
{

    @Autowired
    SqlSession session;

    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次
    public void execute(){

        //持久化点击信息
        dict_entity entity = new dict_entity();
        entity.setValue(String.valueOf(cache.web_index_click_num));
        session.updateByWhereSql(entity,"type =3 and name = 'web_index_click_num'");

    }
}