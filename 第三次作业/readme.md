# 编程手记

## About Atom
+ 修改 `File -> Stylesheet` 的 `style.less` 文件来更改 `Markdown-pdf` 的字体

**IntelliIJ**
+ `file->Project Structure` 选择 `Dependencies` 添加项目要导入的 `jar` 包
+ **命令行参数设置** 在 `run->Edit Configuration` 里面添加命令行参数，多个参数间用空格隔开

**文件流和异常处理**
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
+ 对于文件输出，可以用
    + `BufferedWriter` + `FileWriter`
    + `PrintWriter`
    + `FileOutputStream`
+ 不要轻易在 `try` 块后添加一个能够捕捉所有异常的 `catch` 块，容易使得出现的其他错误被捕捉到。

**HanLP**
+ `HanLP.properties` 应当放在工程目录的 `/out/production/projectname` 下

**Weka**
+ `FastVector` 被弃用了，改用 `ArrayList<>` 具体类型视情况而定

**随机数**
+ 随机数生成

    ```java
    Random random = new Random();
    int rNumber = random.nextInt(start - end);
    ```
    其中参数 **bound** 代表上界，不包含在内

**ArrayList**
+ `ArrayList` 的 `get()` 方法只能获取，不能修改，修改要用 `set()` 方法
+ `ArrayList` 的初始化的整形参数指定了 **Capacity** 而不是 **Size** 因此还是需要使用 `add` 方法而不是 `set` 方法
+ `collection` 的 `allAll()` 方法不能直接作用于 `Stringp[]` ，但可以利用 `Arrays.asList()` 达成。
    ```java
    wordSet.addAll(Arrays.asList(words));
    ```
**字符串**
+ `String.format()` 可以用来格式化生成字符串
+ `Integer.toString()` 将数字转换为字符串
+ `Integer.parseInt()` 和 `Integer.valueOf()`将字符串转换为数字
+ `string.split(regex)` 方法中的参数是一个 **正则表达式**，如果要表示正则表达式中的字符，需要 **转义** 如

    ```java
    String filename = tline.split("\\|")[1];
    String filename = tline.split("|")[1];
    ```
    两个的匹配结果会不一样
+ `string.replaceAll(regex)` 方法中也是一个正则表达式，会精确匹配，并将匹配的部分全部替换

**三元运算符**
+ `C = A > B ? A : B` 为真则是A，假则为B

    ```java
    String format = last40.get(i) >= 20 ? "0|./1/%d.txt" : "0|./1/0%d.txt";
    String format = pre80.get(i) < 10 ? "0|./1/00%d.txt" : "0|./1/0%d.txt";
    ```
**参数重载**
java **不支持** 参数重载

## java中文文件乱码问题
`FileReader` 等其他中文乱码问题，当用 `FileReader` 读取文件时，因为 `FileReader` 类继承自 `InputStreamReader` 但 **没有实现父类带字符集参数的构造函数**， 因此只能按照系统默认编码。解决方式是用父类代替。
```java
    datafile = new BufferedReader(
        new InputStreamReader(
        new FileInputStream(filename), "GBK"));
```
这里 `FileReader` 读的是 **字符流**，而 `FileInputStream` 是 **字节流**，因此可以在 `InputStreamReader` 父类字符流中指定字符编码。

**注意** 这样的会引入新的 **未处理异常** `UnsupportedEncodingException` 需要在异常处理中加入一项。
