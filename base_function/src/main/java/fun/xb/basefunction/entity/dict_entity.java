package fun.xb.basefunction.entity;


import fun.xb.easyorm.service.annotation.table;
import lombok.Data;

/**
 * 数据字典
 */
@Data
@table("dict")
public class dict_entity {


    /**
     * 自增id
     */
    Integer id;

    /**
     *  数据字段名字
     */
    public String name;

    /**
     * 数据的值
     */
    public String value;

    /**
     * 字典类型
     */
    Integer type;

}
