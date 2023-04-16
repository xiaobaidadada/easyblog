package fun.xb.easyFullTextorm.util;


import fun.xb.easyFullTextorm.service.annotation.text_column;
import fun.xb.easyFullTextorm.service.annotation.text_id;

public class dto {

    @text_id
    Long id;

    @text_column
    String a;

    @text_column
    String b;

}
