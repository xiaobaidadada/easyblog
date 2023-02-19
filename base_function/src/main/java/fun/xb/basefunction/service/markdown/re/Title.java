package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式实现简易md解析器 标题部分
 * 标签有:
 * <h1> <h2> ... <h6>
 */
public class Title {
    private static Pattern HEADER_PATTERN = Pattern.compile("^\\s*?(#+)\\s+(.+)$",Pattern.MULTILINE);//使用多行匹配才能使用 $和^符号

    String start="";
    String tail="";
//    static public String mdTitleparse(String markdown){
//        String html = markdown;
//        Matcher headerMatcher = HEADER_PATTERN.matcher(html);
//
//        while (headerMatcher.find()) {
//            String header = headerMatcher.group(1);//标题内容部分
//            int level = headerMatcher.group(0).split("#").length-1;//分解的数量肯定会多一个
//            String headerTag = "h" + level;
//            html = html.replace(headerMatcher.group(0), "<" + headerTag + ">" + header + "</" + headerTag + ">");//只替换这一部分
//        }
//
//
//        return html;
//    }

    public static String mdTitleparse(String markdown) {
        String html = markdown;

        Matcher matcher = HEADER_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String header = matcher.group(2);
            String h=matcher.group(1);
            int level=getNum(h);
            String headerTag = "h" + level;
            matcher.appendReplacement(sb, "<" + headerTag + ">" + header + "</" + headerTag + ">");//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }

    public static int getNum(String jing){
        char [] f=jing.toCharArray();
        int i=0;
        for (char v:f){
            if(v=='#')i++;
        }
        return i;
    }
}
