package fun.xb.basefunction.entity;


import fun.xb.easyorm.service.annotation.table;
import lombok.Data;
import fun.xb.easyorm.service.annotation.id;

@Data
@table("type")
public class type_entity {

    @id
    Integer id;


    String type_name;

}
