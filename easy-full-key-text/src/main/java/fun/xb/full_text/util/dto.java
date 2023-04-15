package fun.xb.full_text.util;


import fun.xb.full_text.service.annotation.text_column;
import fun.xb.full_text.service.annotation.text_id;

public class dto {

    @text_id
    Long id;

    @text_column
    String a;

    @text_column
    String b;

}
