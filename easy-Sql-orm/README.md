
# easyorm使用指南

特定：Mybatis或者springjap,需要写mapper或者repository，这些东西都是为了做映射，把一个方法的sql确定好；这是为了入参orm，而大多数场景下，
并不需要很复杂的入参封装，因此才有easyorm，只提供能够满足查询的特定几个方法，而不给使用者提供自定义的orm入参，所以easyorm并不称为一个完整的orm,
查询结果支持一对一和一对多映射；目的是为了 使用原生sql简化小项目项目的开发；

