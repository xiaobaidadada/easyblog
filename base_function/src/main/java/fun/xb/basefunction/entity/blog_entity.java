package fun.xb.basefunction.entity;

import fun.xb.easyorm.service.annotation.table;
import fun.xb.full_text.service.annotation.text_column;
import fun.xb.full_text.service.annotation.text_id;
import lombok.Data;
import fun.xb.easyorm.service.annotation.id;


@Data
@table("blog")
public class blog_entity {

    @id
    @text_id
    Integer id;

    String title;

    @text_column
    String context;

    //转义成HTML的数据
    String context_html;

    Long time_created;

    Long time_update;

    //类型Id
    Integer type_id;

    //目录
    String directory;

    //点击次数
    Integer click;

}
