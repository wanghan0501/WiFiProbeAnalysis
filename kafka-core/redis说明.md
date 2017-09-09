# Redis 说明

## redis表

### table\_user
这个表是一个hashmap,表名叫做talbe\_user(有\是为了md转译，内存表中是没有的)
其中的field为

```
shop_id||mac
```

value为UserBean构成的json

事例数据

```
"1||f4:5c:89:de:56:bc"
"{\"brand\":\"Apple,\",\"mac\":\"f4:5c:89:de:56:bc\",\"shopId\":1}"
```

### table\_user\_visit
这个表是一个hashmap,表名叫做talbe\_user\_visit(有\是为了md转译，内存表中是没有的)

其中的field为

```
shop_id||mmac||visitTime
```

value为UserVisitBean构成的json

事例数据

```
field: "1||f4:5c:89:b6:e9:7c||1503726476000"
value: "{\"checkInFlow\":10,\"checkInRate\":0.35714285714285715,\"deepVisitRate\":0.0,\"mmac\":\"f4:5c:89:b6:e9:7c\",\"shallowVisitRate\":1.0,\"shopId\":1,\"time\":1503726476000,\"totalFlow\":28}"
```


### table\_user\_visit\_time

这个表逻辑上其实是一个list构成的集合
list 的key是

```
shop_id||mac
```

value是对应的timestamp的列表



### table\_user\_visit\_time
