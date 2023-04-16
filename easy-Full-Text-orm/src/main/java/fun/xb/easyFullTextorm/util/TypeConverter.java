package fun.xb.easyFullTextorm.util;


/**
 * 本类用于编写类型转换器，从基本类型到对象，从long到int等可以转换的转换
 * 对于数字类型，目前只支持 低类型到高类型的转换
 */
public class TypeConverter {

    /**
     * 类型转换
     * @param other_type 是对方的类型
     * @param self 是需要被转成对方类型的值
     * @return
     */
    static public Object selfType_to_otherType(String other_type , Object self){
        String hehe = self.getClass().getTypeName();
        String type = other_type;
        if(type.equals("double") || type.equals("java.lang.Double")){
            if(hehe.equals("java.lang.Float"))return ((Float)self).doubleValue();
            else if(hehe.equals("java.lang.Long"))return ((Long)self).doubleValue();
            else if(hehe.equals("java.lang.Integer"))return ((Integer)self).doubleValue();
            else if(hehe.equals("java.lang.Short"))return ((Short)self).doubleValue();
            else if(hehe.equals("java.lang.Byte"))return ((Byte)self).doubleValue();
            return (double)self;
        }
        else if (type.equals("float") || type.equals("java.lang.Float")){
            if(hehe.equals("java.lang.Long"))return ((Long)self).floatValue();
            else if(hehe.equals("java.lang.Integer"))return ((Integer)self).floatValue();
            else if(hehe.equals("java.lang.Short"))return ((Short)self).floatValue();
            else if(hehe.equals("java.lang.Byte"))return ((Byte)self).floatValue();
            return (float)self;
        }
        else if (type.equals("long") || type.equals("java.lang.Long")){
            if(hehe.equals("java.lang.Integer"))return ((Integer)self).longValue();
            else if(hehe.equals("java.lang.Short"))return ((Short)self).longValue();
            else if(hehe.equals("java.lang.Byte"))return ((Byte)self).longValue();
            return (long)self;
        }
        else if (type.equals("int") || type.equals("java.lang.Integer")) {
            if(hehe.equals("java.lang.Short"))return ((Short)self).intValue();
            else if(hehe.equals("java.lang.Byte"))return ((Byte)self).intValue();
            return (int)self;
        }
        else if (type.equals("short") || type.equals("java.lang.Short")){
            if(hehe.equals("java.lang.Byte"))return ((Byte)self).shortValue();
            return (short)self;
        }
        return self;
    }
}
