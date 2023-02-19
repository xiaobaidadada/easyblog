package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分割线
 */
public class SqLine {

    private static Pattern Line_PATTERN = Pattern.compile("\"^[*-_]{3,}$\"",Pattern.MULTILINE);//使用多行匹配才能使用 $和^符号

    static String ling="<div class=\"md_spline\"/></div>";

    public static String mdLineparse(String markdown) {
        String html = markdown;

        Matcher matcher = Line_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group();
            matcher.appendReplacement(sb, ling);//这样的做法可以更为精细的处理每一个匹配
        }

        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }
}
