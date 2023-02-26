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

    String time_created;

    String time_update;

    Integer type_id;
}
