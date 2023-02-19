package fun.xb.basefunction.entity;


import lombok.Data;
import fun.xb.easyorm.service.annotation.id;

@Data
public class type {

    @id
    Integer id;


    String type_name;

}
