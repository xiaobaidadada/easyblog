package fun.xb.basefunction.service.markdown.re;

public class Config {

    /**
     * 一些匹配的开头不一定非要是空格或者开头才可以生效
     * 每一个语法最前面都有一个字符的开头，和一个结尾，
     * 1. 结尾肯定是$或者换行符
     * 2. 开头可以是空格，或者别的语法的首字符(部分)
     * 按照他们匹配的先后顺序进行排序，以正则表达式书写
     */
    static String [] effect=new String[]{"#\\s+",">\\s+","\\d+\\.\\s+","-\\s+","^//s*?"};//最后一个需要多行匹配
    //每一个的可能都不一样，这个统一的不能用
}
