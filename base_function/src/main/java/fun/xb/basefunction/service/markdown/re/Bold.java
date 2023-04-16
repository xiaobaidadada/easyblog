package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 加粗部分
 * 标签有：
 * <p></p>
 * class有：
 * md_bold
 */
public class Bold {

    static String start="<span class=\"md_bold\" />";
    static   String tail="</span>";
    private static final Pattern BOLD_PATTERN = Pattern.compile("\\*\\*(.+?)\\*\\*");

    public static String mdBoldeparse(String markdown) {
        String html = markdown;

        Matcher matcher = BOLD_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(1);
            matcher.appendReplacement(sb, start + quote + tail);//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }

    //    static public String mdBoldeparse(String markdown){
//        String html = markdown;
//        Matcher boldMatcher = BOLD_PATTERN.matcher(html);
//        while (boldMatcher.find()) {
//            String boldText = boldMatcher.group(1);
//            html = html.replace(boldMatcher.group(0), "<p class=\"md_bold\" />" + boldText + "</p>");
//        }
//
//        return html;
//    }

}
