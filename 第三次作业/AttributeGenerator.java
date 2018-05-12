import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.seg.*;
import com.hankcs.hanlp.seg.NShort.*;

public class AttributeGenerator {
    private ArrayList<String> trainingFileSet = new ArrayList<>();
    static String wordStr;
    static HashSet<String> wordSet;
    static String alphabet;
    static HashMap<String, String> Alphabet;
    static {
        wordStr = "之,其,或,亦,方,于,即,皆,因,仍,故,尚,呢,了,的,着,一,不,乃,呀," +
                "吗,咧,啊,把,让,向,往,是,在,越,再,更,比,很,偏,别,好,可,便,就,但,儿," +
                "又,也,都,要,这,那,你,我,他,来,去,道,说";
        alphabet = "zhi,qi,huo,yi1,fang,yu,ji,jie,yin,ren,gu,shang,ne,le,de,zhe1,yi2,bu,nai,ya," +
                "ma,lie,a,ba,rang,xiang,wang,shi,zai1,yue,zai2,geng,bi,hen,pian,bie,hao,ke,bian,jiu,dan,er," +
                "you,ye,dou,yao,zhe2,na,ni,wo,ta,lai,qu,dao,shuo";
        wordSet = new HashSet<>();
        Alphabet = new HashMap<>();

        String[] words = wordStr.split(",");
        String[] alphas = alphabet.split(",");
        for(String str: words)
            wordSet.add(str);
        for(int i=0;i<words.length;++i)
            Alphabet.put(words[i], alphas[i]);
    }
    private class Attribute{
        int size;
        int chapter;
        HashMap<String, Integer> wordMap;
        public Attribute(){
            size = 0;
            chapter = 0;
            wordMap = new HashMap<>();
            for(String str : wordStr.split(","))
                wordMap.put(str, 0);
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
                String[] splits = tline.split("\\|");
                String filename = splits[1];
                int chapter = Integer.parseInt(splits[0]);
                System.out.println(filename);
                datafile = new BufferedReader(
                                new InputStreamReader(
                                new FileInputStream(filename), "GBK"));
                // 读入文件
                Attribute attr = new Attribute();
                attr.chapter = chapter;
                String line = datafile.readLine();

                // 初始化分词器
                Segment segment = new NShortSegment();
                List<Term> termList = null;
                while (line != null){
                    attr.size += line.length();
                    termList = segment.seg(line);
                    for(Term t:termList){
                        if(wordSet.contains(t.word))
                            attr.wordMap.put(t.word, attr.wordMap.get(t.word)+1);
                    }
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
            for(String str: wordStr.split(","))
                out.println(String.format("@attribute %s real", Alphabet.get(str)));
            out.println("@attribute chapter {0, 1}");
            out.println("@data");
            for(Attribute attr : attrList){
                String str = Integer.toString(attr.size) + ",";
                for(int i: attr.wordMap.values()) {
                    str += (Integer.toString(i) + ",");
                }
                str += Integer.toString(attr.chapter);
                out.println(str);
            }
            out.flush();
        } catch (Exception ex){
            System.err.println("storeAttr() 文件写入出错");
        }
    }

    public static void main(String[] args) throws Exception{
        for(String str : wordStr.split(","))
            System.out.println(str);
    }
}
