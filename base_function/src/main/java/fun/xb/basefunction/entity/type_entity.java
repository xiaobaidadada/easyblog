package fun.xb.basefunction.entity;


import fun.xb.easySqlorm.service.annotation.table;
import lombok.Data;
import fun.xb.easySqlorm.service.annotation.id;

@Data
@table("type")
public class type_entity {

    @id
    Integer id;


    String type_name;

}
