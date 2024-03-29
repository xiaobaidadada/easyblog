package fun.xb.basefunction.entity;


import fun.xb.easySqlorm.service.annotation.table;
import lombok.Data;

@Data
@table("js_plug")
public class js_plug_entity {

    /**
     * 自增id
     */
    Integer id;

    /**
     * 插件内容
     */
    String context;


    /**
     * 创建时间
     */
    Long time;

    /**
     * 是否启用 0启用 1关闭，只能有一个启动
     */
    Integer on_off;

    /**
     * 排序，默认为0
     */
    Integer sort;

    /**
     * 插件类型，主页1 博客2
     */
    Integer type;

    /**
     * 插件名字
     */
    String name;

}
