package fun.xb.basefunction.constant;

import fun.xb.basefunction.entity.dict_entity;
import fun.xb.easySqlorm.service.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class cache implements CommandLineRunner {

    //使用原子类，防止访问量过高时的冲突
    static public AtomicInteger web_index_click_num = new AtomicInteger(0);

    @Autowired
    SqlSession session;

    @Override
    public void run(String... args) throws Exception {

        dict_entity entity = session.selectOne("select * from dict where type =3 and name = 'web_index_click_num'",dict_entity.class);

        web_index_click_num.set(Integer.parseInt(entity.getValue()));



    }
}
