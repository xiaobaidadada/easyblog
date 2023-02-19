package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {

    // 表格 HTML 模板
    private static final String TEMPLATE_TABLE = "<tr><td>$1</td></tr>";
    static String start="<table class=\"md_table\">";

    static String tail ="</table>";

    private static Pattern Table_PATTERN = Pattern.compile("^(\\|.*?\\|)$",Pattern.MULTILINE);
    public static String parseDiv(String markdown) {
        String html = markdown;

        Matcher matcher = Table_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(1);
            matcher.appendReplacement(sb, start + quote + tail);//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }

    public static String mergeDiv(String markdown){

        String html = markdown;

        Pattern pattern = Pattern.compile("^"+start+".*?"+tail+"\\n"+start+".*?"+tail+"$", Pattern.MULTILINE  );//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String dui = matcher.group(0);
            int oneStLenth=start.length();
            String [] list=dui.split(tail+"\\s*?\n\\s*?"+start);
            list[0]=list[0]+tail;
            list[1]=start+list[1];

                String qq=getContext(list[0]);
                StringBuffer sbb = new StringBuffer(list[1]);
                sbb.insert(oneStLenth,qq);
                matcher.appendReplacement(sb, sbb.toString());//这样的做法可以更为精细的处理每一个匹配


        }
        matcher.appendTail(sb);
        html = sb.toString();

        if(pattern.matcher(html).find())//还有多余的符合直到每个只剩独立的一行
            html=mergeDiv(html);

        return html;
    }

    public static String main(String markdown) {

        String re=parseDiv(markdown);

        re=mergeDiv(re);

        re=handleTable(re);

        return re;

    }

    static String getContext(String line){

        Pattern pattern = Pattern.compile("^"+start+"(.*?)"+tail+"$", Pattern.MULTILINE  );//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    static String handleTable(String markDown){
        String html = markDown;
        Pattern pattern = Pattern.compile(start+"(.*)"+tail, Pattern.MULTILINE);//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(1);
            String [] table=quote.split("\\|\\s*\\|");
            String tab=start;
            for (int i=0;i<table.length;i++){//每一行
                String v=table[i];
                String [] tr=v.split("\\|");
                if(tr[0].indexOf("----")!=-1)continue;//结束本次

                if(i==0){
                    String th="<thead><tr>";
                    for (String td:tr){
                        if(td.equals("") )continue;
                        th+="<th>"+td+"</th>";
                    }
                    th+="</tr></thead>";
                    tab+=th;
                }else {
                    String th="<tbody><tr>";
                    for (String td:tr){
                        if(td.equals(""))continue;
                        th+="<td>"+td+"</td>";
                    }
                    th+="</tr></tbody>";
                    tab+=th;
                }

            }
            tab+=tail;

            matcher.appendReplacement(sb,  tab );//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;


    }
}
