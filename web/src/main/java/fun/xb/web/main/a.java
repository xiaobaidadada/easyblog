package fun.xb.web.main;

import fun.xb.easyorm.service.annotation.id;
import fun.xb.easyorm.service.annotation.table;
import fun.xb.easyorm.service.annotation.table;
import lombok.Data;

@table("a")
@Data
public class a {

    @id("id")
    private int id;

    private String name;

}
