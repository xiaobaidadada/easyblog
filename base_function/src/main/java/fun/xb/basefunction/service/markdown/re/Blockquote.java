package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blockquote {

    static String start="<div class=\"md_quote\">";

    static String tail ="</div>";

    public static String parseBlockquote(String markdown) {
        String html = markdown;
        //DOTALL . 可以匹配换行符
        // 处理多级引用
        Pattern pattern = Pattern.compile("^[ \t]*>[ \t]*(.*)$", Pattern.MULTILINE);//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(1);
            quote = parseBlockquote(quote);  // 递归处理多级引用
            matcher.appendReplacement(sb, start + quote + tail);//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }



    public static String mergeBlockquote(String markdown){

        String html = markdown;

        Pattern pattern = Pattern.compile("^"+start+".*?[\\n \\t]*"+tail+".*?\\n"+start+".*?[\\n \\t]*.*?"+tail+"$", Pattern.MULTILINE  );//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String dui = matcher.group(0);
            int oneStLenth=start.length();
            String [] list=dui.split(tail+"\\s*?\n\\s*?"+start);
            list[0]=list[0]+tail;
            list[1]=start+list[1];
            int num1=getIndex(start,list[0]);//分别获取两个字符串 符号的前部的长度
            int num2=getIndex(start,list[1]);

            if(num1==num2){
//                String qq=list[0].substring(num1*oneStLenth,list[0].length()-(num1*oneStLenth+num1));//截取第一个
                String qq=getContext(list[0],num1);
                StringBuffer sbb = new StringBuffer(list[1]);
                sbb.insert(num2*oneStLenth,qq);
                matcher.appendReplacement(sb, sbb.toString());//这样的做法可以更为精细的处理每一个匹配
            }
            if(num1>num2){
//                String qq=list[1].substring(num2*oneStLenth,list[1].length()-(num2*oneStLenth+num2));//截取第二个
                String qq=getContext(list[1],num2);
                StringBuffer sbb = new StringBuffer(list[0]);
                sbb.insert(num2*oneStLenth,qq);
                matcher.appendReplacement(sb, sbb.toString());
            }
            if(num2>num1){
//                String qq=list[0].substring(num1*oneStLenth,list[0].length()-(num1*oneStLenth)+num1);//截取第一个
                String qq=getContext(list[0],num1);
                StringBuffer sbb = new StringBuffer(list[1]);
                sbb.insert(num1*oneStLenth,qq);
                matcher.appendReplacement(sb, sbb.toString());
            }

        }
        matcher.appendTail(sb);
        html = sb.toString();

        if(pattern.matcher(html).find())//还有多余的符合直到每个只剩独立的一行
            html=mergeBlockquote(html);

        return html;
    }

    public static String mdQuoteParse(String markdown) {
        String html = parseBlockquote(markdown);
        html=mergeBlockquote(html);
        return html;
    }

    static int getIndex(String start,String context){
        int oneStLenth=start.length();
        int length=start.length();
        int a=context.indexOf(start);
        a+=length;
        while (context.indexOf(start,a)==a){
            a+=length;
        }
        return a/oneStLenth;
    }

    static String getContext(String line,int num){
        
        String startb=start;
        String end=tail;
        for (int i=1;i<num;i++){
            startb+=start;
            end+=tail;
        }
        Pattern pattern = Pattern.compile("^"+startb+"(.*?\\n*)"+end+"$", Pattern.MULTILINE  );//对于引用来说最前面只能是^或者空格
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "";
    }

}
