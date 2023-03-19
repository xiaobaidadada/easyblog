package fun.xb.basefunction.entity;

import fun.xb.easyorm.service.annotation.table;
import lombok.Data;
import fun.xb.easyorm.service.annotation.id;


@Data
@table("blog")
public class blog_entity {

    @id
    Integer id;

    String title;

    String context;

    Long time_created;

    Long time_update;

    //类型Id
    Integer type_id;

    //目录
    String directory;

    //点击次数
    Integer click;

}
