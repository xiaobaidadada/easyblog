package fun.xb.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 通用返回对象
 * @param <T>
 */
@Data
public class Result<T>  {

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSmg() {
        return smg;
    }

    public void setSmg(String smg) {
        this.smg = smg;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

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

    // 额外的信息
    private Map<String,Object> map;


    public static <T> Result<T> sucess(){
        return new Result<>(true,1,"",null);
    }

    public static <T> Result<T> sucess(T value){
        return new Result<>(true,1,"",value);
    }

    public static <T> Result<T> sucess(T value,Map<String,Object> map){
        Result result =  new Result<>(true,1,"",value);
        result.setMap(map);
        return result;
    }

    public static <T> Result<T> fail(String smg){
        return new Result<>(false,2,smg,null);
    }

    public static <T> Result<T> fail(int code){
        return new Result<>(false,code,null,null);
    }

    public static <T> Result<T> fail(){
        return new Result<>(false,2,"",null);
    }

}
