
#说明

只做数据的辅助全文索引，且只起数据库某些字段的全文索引功能；不做分页和其他更复杂的操作；

lucene 是采用单线程的方式进行读取，所以没有连接的概念，也就不需要连接池；

#使用方式
1. 在实体类上注解，text_column 和 text_id 分别代表id和文本，文本仅支持字符串，id可以是数字也可以是字符串
2. 在别的模块引入这个包，自动注入全局的FullTextSession。实现增删改查
3. 目前查询仅仅支持 属性字段的多个匹配，如果属性id不为空，查询的时候只查id忽略其他
4. 目前并没有自动同步数据库的功能
5. 使用思想是，只保存数据库字段的部分文本做全文索引，通过全文索引查到数据后，根据id再查数据库


# 未来打算开发的特性
通过添加text-indexs注解，为每个关系数据库的表创建一个独立的目录，可以针对不同的表进行专有的全文索引，目前只支持一个，且如果多表使用数据会混乱，除非自己添加字段标识；
并为每个索引的连接提供专门的对象池；



