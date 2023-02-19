package fun.xb.basefunction.entity;


import lombok.Data;
import fun.xb.easyorm.service.annotation.id;

@Data
public class comment {

    @id
    Integer id;

    String context;

    Integer father_id;

    String time;

    Integer blog_id;
}
