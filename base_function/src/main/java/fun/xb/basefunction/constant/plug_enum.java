package fun.xb.basefunction.constant;




public enum plug_enum {

    index(1,"主页插件") ,

    blog(2,"博客界面插件")
    ;

    private int type;

    private String message;

    plug_enum(int type,String message){
        this.message = message;
        this.type = type;
    }

    public Integer getType(){
        return this.type;
    }

    public String getMessage(){
        return this.message;
    }

}
