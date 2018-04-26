import java.io.*;
import java.util.ArrayList;

public class AttributeGenerator {
    private ArrayList<String> trainingFileSet = new ArrayList<>();
    private class Attribute{
        int size;
        int chapter;
        public Attribute(){
            size = 0;
            chapter = 0;
        }
    }
    private ArrayList<Attribute> attrList = new ArrayList<>();

    public AttributeGenerator(String filename){
        BufferedReader datafile = null;
        try{
            datafile = new BufferedReader(new FileReader(filename));
            String trainingFile = datafile.readLine();
            while(trainingFile != null){
                trainingFileSet.add(trainingFile);
                trainingFile = datafile.readLine();
            }
        } catch (FileNotFoundException ex){
            System.err.println("文件不存在：" + filename);
        } catch (Exception ex){
        }
    }

    public void genAttribute(){
        BufferedReader datafile = null;
        try {
            for (String tline : trainingFileSet) {
                String filename = tline.split("\\|")[1];
                int chapterNum = Integer.parseInt(filename.substring(9,12));
                filename = filename.replaceAll("[0-9]+\\.", String.format("%d.", chapterNum));
                System.out.println(filename);
                int chapter = Integer.parseInt(tline.split("\\|")[0]);
                datafile = new BufferedReader(
                                new InputStreamReader(
                                new FileInputStream(filename), "GBK"));
                // 读入文件
                Attribute attr = new Attribute();
                attr.chapter = chapter;
                String line = datafile.readLine();
                while (line != null){
                    attr.size += line.length();
                    // 处理文件生成特征值
                    line = datafile.readLine();
                }
                attrList.add(attr);
                // 生成特征值列表
            }
        }
        catch (FileNotFoundException ex){
            System.err.println("genAttribute() 文件不存在");
        }
        catch (UnsupportedEncodingException ex){
            System.err.println("genAttribute() 编码错误");
        }
        catch (IOException ex){
            System.err.println("genAttribute() 未知错误");
            System.err.println(ex.getMessage());
        }
    }

    public void storeAttr(String filename){
        PrintWriter out = null;
        try{
            out = new PrintWriter(filename);
            out.println("@relation guess_chapter");
            out.println("@attribute size real");
            out.println("@attribute chapter {0, 1}");
            out.println("@data");
            String format = "%d,%d";
            for(Attribute attr : attrList)
                out.println(String.format(format, attr.size, attr.chapter));
            out.flush();
        } catch (Exception ex){
            System.err.println("storeAttr() 文件写入出错");
        }
    }
}
