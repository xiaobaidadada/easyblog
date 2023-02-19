package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeSeg {

    static String start="<span class=\"md_codeseg\" />";
    static   String tail="</span>";
    private static final Pattern code_PATTERN = Pattern.compile("`(.*?)`");

    public static String mdCodeSegeparse(String markdown) {
        String html = markdown;

        Matcher matcher = code_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(1);
            matcher.appendReplacement(sb, start + quote + tail);//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }
}
