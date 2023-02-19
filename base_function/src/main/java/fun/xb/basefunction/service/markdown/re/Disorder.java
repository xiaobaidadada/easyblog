package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 无序
 */
public class Disorder {
    private static Pattern Disorder_PATTERN = Pattern.compile("^\\s*?[*+-]\\s*?(.*)$",Pattern.MULTILINE);//使用多行匹配才能使用 $和^符号

   static String start="<div class=\"md_disorder\">";
    static   String tail="</div>";

    public static String mdDisorderparse(String markdown) {
        String html = markdown;

        Matcher matcher = Disorder_PATTERN.matcher(html);
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
