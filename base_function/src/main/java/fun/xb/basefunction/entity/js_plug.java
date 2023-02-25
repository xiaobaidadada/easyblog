package fun.xb.basefunction.entity;


import fun.xb.easyorm.service.annotation.table;
import lombok.Data;

@Data
@table("css_plug")
public class js_plug {

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
    String time;

    /**
     * 是否启用 0启用 1关闭，只能有一个启动
     */
    Integer on_off;

    /**
     * 排序，默认为0
     */
    Integer sort;

}
