package fun.xb.basefunction.entity;


import fun.xb.easySqlorm.service.annotation.table;
import lombok.Data;
import fun.xb.easySqlorm.service.annotation.id;

@Data
@table("comment")
public class comment_entity {

    @id
    Integer id;

    String context;

    Integer father_id;

    Long time;

    Integer blog_id;
}
