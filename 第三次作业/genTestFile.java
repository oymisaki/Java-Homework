import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class genTestFile {
    public static ArrayList<Integer> removeRandom(int start, int end, int count){
        ArrayList<Integer> numbers = new ArrayList<>(end - start);
        for(int i=0; i<end-start; ++i){
            numbers.add(i+1);
        }
        ArrayList<Integer> result = new ArrayList<>(count);
        Random random = new Random();
        for(int i=0; i<count; ++i){
            int rNumber = random.nextInt(end - start - i);
            result.add(numbers.get(rNumber));
            numbers.remove(rNumber);
        }
        return result;
    }

    public static void main(String args[]){
        ArrayList<Integer> pre80 = removeRandom(0, 80, 20);
        ArrayList<Integer> last40 = removeRandom(0,40,20);
        BufferedWriter datafile = null;
        PrintWriter out = null;
        String filename = "TrainingSetDir.txt";
        String testSetName = "TestingSetDir.txt";
        try{
            datafile = new BufferedWriter(new FileWriter(filename));
            out = new PrintWriter(testSetName);
            for(int i=0; i<pre80.size(); ++i){
                String format = pre80.get(i) < 10 ? "0|./data/0/00%d.txt" : "0|./data/0/0%d.txt";
                String message = String.format(format, pre80.get(i));
                datafile.write(message);
                datafile.newLine();
                // 写入训练集
                out.println(pre80.get(i));
                // 写入测试集
            }
            for(int i=0; i<last40.size(); ++i){
                String format = last40.get(i) >= 20 ? "1|./data/1/%d.txt" : "1|./data/1/0%d.txt";
                String message = String.format(format, 80 + last40.get(i));
                datafile.write(message);
                datafile.newLine();
                // 写入训练集
                out.println(last40.get(i)+40);
                // 写入测试集
            }
            out.flush();
            datafile.flush();
        } catch (Exception ex){
            System.out.println("写入失败：" + filename);
        }
    }
}
