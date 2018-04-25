import java.util.List;
import java.io.*;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.seg.*;
import com.hankcs.hanlp.seg.NShort.*;

public class HanLPDemo {
    public static void main(String args[]){
        BufferedReader datafile = null;
        String filename = "test.txt";
        try{
            datafile = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "GBK"));
        }catch (FileNotFoundException ex){
            System.err.println("File not found:" + filename);
        }catch (UnsupportedEncodingException ex){
            System.err.println("Wrong file encoding");
        }
        Segment segment = new NShortSegment();
        try{

            List<Term> termList = null;
            String str = datafile.readLine();
            while(str != null){
                System.out.println(str);
                termList = segment.seg(str); //切词
                for(Term t:termList){
                    System.out.println(t);
                }
                str = datafile.readLine();
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            // 处理遗留问题，即文件不存在时，datafile为空
        }
    }
}
