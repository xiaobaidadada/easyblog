package fun.xb.web.vo;


import lombok.Data;

@Data
public class plug_vo {

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
     * 插件类型，主页1 博客2
     */
    Integer type;

}
