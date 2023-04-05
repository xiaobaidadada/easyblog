package fun.xb.basefunction.constant;

import fun.xb.basefunction.entity.dict_entity;
import fun.xb.easyorm.service.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class cache implements CommandLineRunner {

    static public int web_index_click_num = 0;

    @Autowired
    SqlSession session;

    @Override
    public void run(String... args) throws Exception {

        dict_entity entity = session.selectOne("select * from dict where type =3 and name = 'web_index_click_num'",dict_entity.class);

        web_index_click_num = Integer.parseInt(entity.getValue());

    }
}
