package fun.xb.basefunction.entity;

import fun.xb.easySqlorm.service.annotation.table;
import lombok.Data;

/**
 * 数据字典表
 */
@Data
@table("js_plug")
public class data_config_entity {

    /**
     * 自增id
     */
    Integer id;

    /**
     * 内容信息
     */
    String context;


    /**
     *type 值
     */
    Integer type;

    /**
     * 类型名字
     */
    String type_name;
}
