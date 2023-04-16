package fun.xb.basefunction.entity;


import fun.xb.easySqlorm.service.annotation.table;

@table("static")
public class static_entity {

    @fun.xb.easySqlorm.service.annotation.id
    Integer id;


    int type;

    long num;
}
