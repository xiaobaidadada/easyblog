package fun.xb.easyorm.util;

import lombok.Data;

import java.util.List;

/**
 * 分页返回值
 */
@Data
public class SelectPage<T> {

    private int num;//页数

    private int size;//页面大小

    private int total;//总数

    private int pages;//页面总数量

    private List<T> list;//具体的页面

    private int type;//为mysql还是postgresql，默认是postgresql

    static public int getPages(int total,int size){

        if(size==0){return 0;}
        if(total % size!=0){
            return total / size + 1;
        }
        else {
            return total / size ;
        }

    }
}
