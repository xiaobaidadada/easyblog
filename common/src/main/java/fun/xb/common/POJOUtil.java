package fun.xb.common;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * 进行对象，集合元素之间的属性复制，设置值，计算后复制等操作，不对static ,final进行复制
 */
public class POJOUtil {

    /**
     * 复制属性，不允许强制类型转换，不复制函数，浅拷贝
     *
     * @param source   源对象
     * @param target   目标对象
     * @param nameMate 做额外匹配用的，要成偶数出现，一一对应，前是源对象属性，后是目标对象属性； 设置为空可以跳过
     */
    static public void copyProperties(Object source, Object target, String... nameMate) {
        Class sourceLei = source.getClass();
        Class targetLei = target.getClass();
        Field[] sourceFields = source.getClass().getDeclaredFields();//源属性集合
        Field[] targetFields = target.getClass().getDeclaredFields();//目标属性集合
        try {

            //先按属性名字进行copy
            for (Field fieldTarget : targetFields) {

                //不对特殊修饰的属性进行copy，就算进行也可能会报错，判断在这里不能用等于号
                int fileModifyType = fieldTarget.getModifiers();
                if ( Modifier.isStatic(fileModifyType) ||  Modifier.isFinal(fileModifyType) ) {
                    continue;
                }

                boolean p = false;
                for (int i = 0; i < nameMate.length; ) {//检查源有没有匹配的要不要匹配
                    if (nameMate[i].equals(fieldTarget.getName())) {
                        p = true;
                        break;
                    }
                    i += 2;
                }
                if (p) {
                    continue;//跳过本字段
                }

                for (Field fieldSource : sourceFields) {

                    if (fieldTarget.getName().equals(fieldSource.getName())) {
                        fieldTarget.setAccessible(true);
                        fieldSource.setAccessible(true);
                        fieldTarget.set(target, fieldSource.get(source));
                    }

                }

            }

            //设置特殊属性名，字符串一一对应的情况，这个时候要注意不能对特殊属性比如final进行copy
            for (int i = 0; i < nameMate.length; ) {
                if (nameMate[i + 1].equals("") || nameMate[i].equals("")) {//判断空
                    i += 2;
                    continue;
                }
                Field sourceT = sourceLei.getDeclaredField(nameMate[i]);//取值
                Field targetT = targetLei.getDeclaredField(nameMate[i + 1]);//取值
                targetT.setAccessible(true);
                sourceT.setAccessible(true);
                targetT.set(target, sourceT.get(source));

                i += 2;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }


    }

    static public <S, T> void copyProperties(S source, T target, Converter<S, T> converter, String... nameMate) {
        copyProperties(source, target, nameMate);
        converter.converter(source, target);//自定义处理方法

    }

    /**
     * 集合默认复制，从一个集合到另一个集合，不包含map，都有add函数，浅拷贝
     *
     * @param collectionSource 源集合
     * @param collectionTaret  目标集合
     * @param clazz            目标集合的类型，生成对象需要知道类型
     * @param nameMate         做额外匹配用的，要成偶数出现，一一对应，前是源对象属性，后是目标对象属性；
     */
    public static <S, T> void copyPropertyToList(Collection<S> collectionSource, Collection<T> collectionTaret, Class<T> clazz, String... nameMate) {
        collectionSource.stream().forEach((S s) -> {
            try {
                T t = clazz.getDeclaredConstructor().newInstance();
                copyProperties(s, t, nameMate);
                collectionTaret.add(t);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static <S, T> void copyPropertyToList(Collection<S> collectionSource, Collection<T> collectionTaret, Class<T> clazz, Converter<S, T> converter, String... nameMate) {
        //POJOUtil.<SurverVO,SurverVO>copyPropertyToList(page.getList(), target, SurverVO.class,(a,b)->{}); 函数泛型确定例子
        collectionSource.stream().forEach((S s) -> {
            try {
                T t = clazz.getDeclaredConstructor().newInstance();
                copyProperties(s, t, converter, nameMate);
                collectionTaret.add(t);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 将列表的属性全部设置为null
     *
     * @param collection
     * @param nameMate
     * @param <S>
     */

    public static <S> void setPartOfCollectoionPropertyToNull(Collection<S> collection, String... nameMate) {
        collection.stream().forEach((S s) -> {

            Class target = s.getClass();
            Field[] targetFields = target.getDeclaredFields();//目标属性集合
            for (Field fieldTarget : targetFields) {

                for (String fieldName : nameMate) {

                    if (fieldTarget.getName().equals(fieldName)) {
                        fieldTarget.setAccessible(true);
                        try {
                            fieldTarget.set(s, null);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }

        });
    }

    public static <S> void setPartOfPropertyToNull(S collection, String... nameMate) {


        Class target = collection.getClass();
        Field[] targetFields = target.getDeclaredFields();//目标属性集合
        for (Field fieldTarget : targetFields) {

            for (String fieldName : nameMate) {

                if (fieldTarget.getName().equals(fieldName)) {
                    fieldTarget.setAccessible(true);
                    try {
                        fieldTarget.set(target, null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }


    }


    public static interface Converter<S, T> {
        void converter(S source, T garget);

    }
}


