package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 换行处理
 */
public class Br {

    static public String BrTo(String mardown){
        Pattern br= Pattern.compile("\n",Pattern.MULTILINE);
        Matcher br_c=br.matcher(mardown);
        String context=br_c.replaceAll("<br/>");
        return context;
    }
}
