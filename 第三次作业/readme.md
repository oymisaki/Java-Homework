# 编程手记
+ `file->Project Structure` 选择 `Dependencies` 添加项目要导入的 `jar` 包
+ 对于文件输入，如果不进行 **异常处理**，编译器会通不过
    ```java
    try{
            datafile = new BufferedReader(new FileReader(filename));
        }catch (FileNotFoundException ex){
            System.err.println("File not found:" + filename);
        }
    ```
    同样的问题在使用流对象时也会出现。如 `str = datafile.readLine();` 也要进行排除。处理的异常是datafile可能为空指针，即文件不存在
    ```java
    try{
        ...
        str = datafile.readLine();
        ...
    }catch (Exception e){
    // 什么也不做
    }
    ```
+ `HanLP.properties` 应当放在工程目录的 `/out/production/projectname` 下

## java中文文件乱码问题
`FileReader` 等其他中文乱码问题，当用 `FileReader` 读取文件时，因为 `FileReader` 类继承自 `InputStreamReader` 但 **没有实现父类带字符集参数的构造函数**， 因此只能按照系统默认编码。解决方式是用父类代替。
```java
    datafile = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "GBK"));
```
这里 `FileReader` 读的是 **字符流**，而 `FileInputStream` 是 **字节流**，因此可以在 `InputStreamReader` 父类字符流中指定字符编码。

**注意** 这样的会引入新的 **未处理异常** `UnsupportedEncodingException` 需要在异常处理中加入一项。
