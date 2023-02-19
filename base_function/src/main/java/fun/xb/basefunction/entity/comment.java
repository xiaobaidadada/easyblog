package fun.xb.basefunction.entity;


import lombok.Data;

@Data
public class comment {
    Long id;

    String context;

    Long father_id;

    Long time;

    Long blog_id;
}
