package fun.xb.easyFullTextorm.util;

import fun.xb.easyFullTextorm.service.EasyFullTextSession;
import fun.xb.easyFullTextorm.service.FullTextSession;
import fun.xb.easyFullTextorm.service.datasource.EasyFullTextSource;
import fun.xb.easyFullTextorm.service.datasource.sourConfig;

import java.util.List;

public class main_test {

    public static void main(String[] args) {
        sourConfig s = new sourConfig();
        s.home_path="D:\\databases_data\\lucene";
        EasyFullTextSource source = new EasyFullTextSource(s);
        FullTextSession session = new EasyFullTextSession(source);
        dto query= new dto();
        query.a="666667";
//        query.id=123L;
//        query.b="初次登场DVD v 打算大苏打大";

        dto data= new dto();
        data.a="666667";
        data.id=123L;

//        session.insert(data);

//        session.update(data,query);
        List list =session.query(query);
        System.out.println(list.size());
    }
}
