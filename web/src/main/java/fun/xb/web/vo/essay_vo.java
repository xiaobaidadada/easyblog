package fun.xb.web.vo;

import fun.xb.easyorm.service.annotation.id;
import lombok.Data;

@Data
public class essay_vo {



    Integer id;

    String title;


    //类型Id
    String type;


    //点击次数
    Integer click;
}
