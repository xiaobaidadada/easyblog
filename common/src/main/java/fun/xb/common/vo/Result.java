package fun.xb.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回对象
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 成功与失败标志
     */
    private boolean status;

    /**
     * 状态码信息
     */
    private int code;

    /*
    状态信息
     */
    private String smg;

    /**
     * 返回的对象信息
     */
    private T value;

    public Result( boolean status,int code,String smg,T value){
        this.status=status;
        this.code=code;
        this.smg=smg;
        this.value=value;
    }


    public static <T> Result<T> sucess(){
        return new Result<>(true,1,"",null);
    }

    public static <T> Result<T> sucess(T value){
        return new Result<>(true,1,"",value);
    }

    public static <T> Result<T> fail(String smg){
        return new Result<>(false,10,smg,null);
    }

    public static <T> Result<T> fail(){
        return new Result<>(false,10,"",null);
    }

}
