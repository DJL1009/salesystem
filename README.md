## 购物系统--网易大作业

### 系统实现技术栈  
> Spring Boot + Spring Security + Thymeleaf + Mysql  


### 问题记录  
#### Spring Security启用CSRF（跨域请求伪造）防御功能  
> 问题：所有post请求都要验证csrf_token，否者返回403代码。  
> 解决：post的请求头设置X-CSRF-TOKEN字段，或data域设置_csrf。 

        <meta name="_csrf" th:content="${_csrf.token}"/>                                    //html文件添加meta
        var token = document.querySelector("meta[name='_csrf']").getAttribute("content");   //js文件获取token值
        xhr.setRequestHeader("X-CSRF-TOKEN",token);                                         //请求头设置token值

#### 文件上传路径及静态资源路径配置  
> 问题：将上传图片存入服务器指定路径（非项目路径），系统无法访问该图片。  
> 解决：将上传路径配置为项目静态路径。  

            # 静态文件请求匹配方式 /relative-path
            spring.mvc.static-path-pattern=/**
            # 自定义文件上传路径
            web.upload-path = /root/uploadImg/
            # 覆盖默认静态路径，并添加文件上传路径为静态路径
            spring.resources.static-locations = \
                              classpath:/META-INF/resources/, \
                              classpath:/resources/, \
                              classpath:/static/, \
                              classpath:/public/,\
                              file:${web.upload-path}

#### Safari浏览器cookie购物车适配  
> 问题：cookie购物车在safari浏览器中失效，解析报错。  
> 原因：Safari cookie不支持添加中文信息，导致cookie添加与解析失败。  
> 解决: js使用encodeURIComponent与decodeURIComponent对可能出现中文的变量进行编解码。  


#### 系统支持emoji输入
> 问题：系统输入emoji，插入数据库时，java程序报SQL异常。  
> 原因：mysql的utf8编码只支持3字节的数据，而emoji数据是4个字节的字符。  
> 解决：将数据库及相应数据表设置为utf8mb4编码   

            1.修改mysql配置文件my.cnf （一般在etc/mysql/my.cnf），重启数据库生效
                [client]
                default-character-set = utf8mb4
                [mysql]
                default-character-set = utf8mb4
                [mysqld]
                character-set-client-handshake = FALSE
                character-set-server = utf8mb4
                collation-server = utf8mb4_unicode_ci
                init_connect='SET NAMES utf8mb4'
            2.修改指定database与table编码。
                ALTER DATABASE database_name CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;  
                ALTER TABLE table_name CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;  
















### 数据库设计： shop
>用户表：user

|字段说明|字段名|数据类型|数据长度|允许空|默认值|约束|
|:----:|:---:|:----:|:-----:|:---:|:---:|:---:|
|用户ID|id|int||否||主键|
|用户名|name|varchar|20|否|||
|用户密码|passwd|varchar|20|否|||

>用户角色：role

|字段说明|字段名|数据类型|数据长度|允许空|默认值|约束|
|:----:|:---:|:----:|:-----:|:---:|:---:|:---:|
|角色ID|id|int||否||主键|
|角色名称|name|varchar|20|否|||

>用户与角色映射：user_role

|字段说明|字段名|数据类型|数据长度|允许空|默认值|约束|
|:----:|:---:|:----:|:-----:|:---:|:---:|:---:|
|用户ID|user_id|int||否||外键|
|角色ID|role_id|int||否||外键|

>商品表：commodity

|字段说明|字段名|数据类型|数据长度|允许空|默认值|约束|
|:----:|:---:|:----:|:-----:|:---:|:---:|:---:|
|商品ID|id|int||否||主键|
|商品名|title|varchar|80|否|||
|商品价格|price|double||否|||
|库存量|quantity|int||否|||
|图片链接|image|varchar||否|||
|简短描述|summary|varchar|140|否|||
|商品介绍|detail|varchar|1000|否|||

>订单表：order

|字段说明|字段名|数据类型|数据长度|允许空|默认值|约束|
|:----:|:---:|:----:|:-----:|:---:|:---:|:---:|
|订单ID|order_id|int||否||主键|
|买家ID|customer_id|int||否||外键|
|商品ID|commodity_id|int||否||外键|
|商品单价|price|double||否|||
|商品数量|quantity|int||否|||
|时间|time|timestamp||否|||
|已完成|is_done|boolean||否|false||

