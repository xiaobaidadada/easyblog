package fun.xb.web.vo;

import lombok.Getter;

@Getter
public class plug_static {

    //两类插件总和
    Long plug_total;


    //正在使用
    String using_name;

    public plug_static plug_total(Long plug_total) {
        this.plug_total = plug_total;
        return this;
    }

    public plug_static using_name(String using_name) {
        this.using_name = using_name;
        return this;
    }

    public static plug_static build (){
        return new plug_static();
    }
}
