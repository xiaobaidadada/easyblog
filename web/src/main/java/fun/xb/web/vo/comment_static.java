package fun.xb.web.vo;

import lombok.Getter;

@Getter
public class comment_static {

    Long total;

    String  title;

    public comment_static total(Long total) {
        this.total = total;
        return this;
    }

    public comment_static title(String title) {
        this.title = title;
        return this;
    }

    static public comment_static build (){
        return new comment_static();
    }
}
