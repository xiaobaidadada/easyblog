# easyOrm使用
原理上是在运行期间进行，注解，反射的提取映射，拼凑sql

# 实体类
默认会按照实体类的属性的字段名字进行获取，数据库的字段名字，如果加了注解，映射的数据库的字段名字就会不再使用属性的

如果实体类的id属性名字就是id，会自动判断是id字段，如果不是id名字属性，或者id起了别的名字，需要加id注解；

# 使用
注入session后，session的实现类提供了运行期间执行sql拼凑功能的函数，每个函数都提供了动态类型数组，作为sql参数绑定，使用?占位符，按前后顺序填充
