package fun.xb.web.vo;

import lombok.Getter;

@Getter
public class essay_static {

    Long 文章总数量;

    Long 文章浏览总量;


    String 最高浏览文章;

    public essay_static 文章总数量(Long 文章总数量) {
        this.文章总数量 = 文章总数量;
        return this;
    }

    public essay_static 文章浏览总量(Long 文章浏览总量) {
        this.文章浏览总量 = 文章浏览总量;
        return this;
    }

    public essay_static 最高浏览文章(String 最高浏览文章) {
        this.最高浏览文章 = 最高浏览文章;
        return this;
    }

    static public essay_static build(){
        return new essay_static();
    }
}
