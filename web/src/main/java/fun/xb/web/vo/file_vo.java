package fun.xb.web.vo;

import lombok.Data;

@Data
public class file_vo {

        //1 是文件  2 文件夹
        public int file_type;

        //文件夹或者文件的名字
        public String name;

        //请求 文件夹内容 或者下载文件的 url
        public String url;

    public file_vo file_type(int file_type) {
        this.file_type = file_type;
        return this;
    }

    public file_vo name(String name) {
        this.name = name;
        return this;
    }

    public file_vo url(String url) {
        this.url = url;
        return this;
    }

    static public file_vo build (){
        return new file_vo();
    }

}
