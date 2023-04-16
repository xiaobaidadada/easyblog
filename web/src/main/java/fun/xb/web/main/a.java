package fun.xb.web.main;

import fun.xb.easySqlorm.service.annotation.id;
import fun.xb.easySqlorm.service.annotation.table;
import lombok.Data;

@table("a")
@Data
public class a {

    @id("id")
    private int id;

    private String name;

}
