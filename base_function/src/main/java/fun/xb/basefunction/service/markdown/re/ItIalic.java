package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 斜体
 */
public class ItIalic {

    static String start="<em>";
    static   String tail="</em>";
    private static final Pattern ITALIC_PATTERN = Pattern.compile("\\*(.+?)\\*");

    public static String mdBoldeparse(String markdown) {
        String html = markdown;

        Matcher matcher = ITALIC_PATTERN.matcher(html);
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
