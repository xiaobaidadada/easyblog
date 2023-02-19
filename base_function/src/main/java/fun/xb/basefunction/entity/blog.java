package fun.xb.basefunction.entity;

import lombok.Data;
import fun.xb.easyorm.service.annotation.id;


@Data
public class blog {

    @id
    Integer id;

    String title;

    String context;

    String time_created;

    String time_update;

    Integer type_id;
}
