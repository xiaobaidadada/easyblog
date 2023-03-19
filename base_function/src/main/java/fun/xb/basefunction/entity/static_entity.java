package fun.xb.basefunction.entity;


import fun.xb.easyorm.service.annotation.id;
import fun.xb.easyorm.service.annotation.table;

@table("static")
public class static_entity {

    @fun.xb.easyorm.service.annotation.id
    Integer id;


    int type;

    long num;
}
