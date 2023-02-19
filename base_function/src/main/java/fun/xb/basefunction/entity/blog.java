package fun.xb.basefunction.entity;

import lombok.Data;

@Data
public class blog {


    Long id;

    String title;

    String context;

    Long time_created;

    Long time_update;

    Long type;
}
