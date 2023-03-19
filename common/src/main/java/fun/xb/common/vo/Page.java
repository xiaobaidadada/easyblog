package fun.xb.common.vo;


import lombok.Data;

import java.util.List;

/**
 * 分页返回值
 */
@Data
public class Page<T> {

    private int page;//页数

    private int size;//页面大小

    private int total;//总数

    private int pages;//页面总数量

    private List<T> list;//具体的页面
}
