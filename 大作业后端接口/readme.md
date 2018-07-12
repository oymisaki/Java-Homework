# readme

+ 上传文件
```shell
pscp -r C:\Users\阳磊\Desktop\大作业\back-side\ root@58.87.xxx.xx:/usr/local/tomcat/apache-tomcat-8.0.52/webapps/course/
```
+ 访问网站
`http://58.87.xxx.xx:8080/course/register.jsp?nickname=cc&password=123456`

+ java位置
/usr/lib/jvm/java-1.8.0-openjdk-amd64

+ tomcat位置
/usr/local/tomcat/apache-tomcat-8.0.52
**把新的jar包导进去的时候需要重启才能生效！**

## ubuntu
+ `root` 登陆
```shell
vim /etc/ssh/sshd_config
加上 PermitRootLogin yes
/etc/init.d/ssh restart
```
+ `~/.bashrc` 作用于当前用户，`etc/profile` 作用于所有用户
+ `sudo` 命令在 `sh bin/startup.sh` 之前
+ 将服务启动文件放入 `/etc/init.d` 中可以变成自启动服务

## mysql
账号密码
+ `root` `123456`
+ `tomcat` `tomcat`
+ 在 `tomcat` 中连接 `mysql`，把 `connector` 加入 `lib` 中
+ 创建用户 ` CREATE USER 'tomcat'@'localhost' IDENTIFIED BY 'tomcat';`
+ 授权 `GRANT ALL ON maindataplus.* TO 'pig'@'%';`
+ 替换 `replace` 语句，在有主键的表中，可以对已存在的行进行 **更新**，未存在的行进行 **插入**
+ 求均值，可以用 `group by courseID having courseID=1` 也可以用 `SELECT AVG(score) FROM Rate WHERE courseID=1`
+ 删除记录 `delete from tablename where col = 1`
+ 模糊匹配

**自增问题**
+ 创建表时自增 `create table tablename(id int auto_increment primary key,...)`
+ 创建表后自增 `alter table tablename add id int auto_increment primary key`
+ 自增断点不连续 `alter table friends  AUTO_INCREMENT=10;` 此处10改为自己的断点即可
+ 自增时的插入有两种方法
  + `insert into userInfo values(null,'ddf','8979')` `null` 也可以是0
  + `insert into userInfo(name,password) values('ddf','8979');`

**字符集问题**
+ 修改表字符集 `alter table Course character set utf8`
+ 修改库字符集 `alter database course character set utf8`
+ 修改字段 `alter table Course change name name char(10) character set utf8`
+ 查看字符集 `show create table Course`

**插入问题**
解决限制读写，编辑 `my.cnf`
```shell
[mysqld]
secure_file_priv=''
```

`result.csv` 应该放在 `var/lib/mysql/course` 目录下，其中 `course` 为对应的数据库名
```sql
LOAD DATA INFILE 'result.csv'
INTO TABLE Course
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
```

### 表结构
+ `BasicInfo` `userID` `nickname` `password`
```sql
create table BasicInfo(userID int, nickname varchar(30), password varchar(30))
insert into BasicInfo values(1, 'yang', '123456');

create table Course(courseID int, name varchar(30), prof varchar(30), department varchar(30), intro varchar(300));
insert into Course values(2, 'Math', 'Prof Yang', 'Math', 'bad');

create table Rate(userID int, courseID int, score float);
alter table Rate add primary key(userID, courseID);
replace into Rate(`userID`, `courseID`, `score`) values(1, 2, 9.0)

create table Post(postID int, userID int, courseID int, content varchar(300))
```

## jsp-jdbc
+ 只从本地访问，mysql默认不开放远程访问
```java
final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
```

+ `session` 中的属性需要用强制类型转换 `(Integer)` 不能用 `Integer.parseInt()`

**接口**
+ login.jsp
+ logout.jsp
+ register.jsp
+ getCourseInfo.jsp
+ rate.jsp
+ getRate.jsp
+ post.jsp
+ getPost.jsp
+ deletePost.jsp
