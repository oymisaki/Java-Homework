import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class FileDirector {
    private static ArrayList<Integer> removeRandom(ArrayList<Integer> remove, int start, int end, int count){
        if(remove == null)
            remove = new ArrayList<>(0);
        ArrayList<Integer> numbers = new ArrayList<>(end - start);
        for(int i=0; i<end-start; ++i){
            if(!remove.contains(start + i + 1))
                numbers.add(start + i + 1);
        }
        end = end - remove.size();
        ArrayList<Integer> result = new ArrayList<>(count);
        Random random = new Random();
        for(int i=0; i<count; ++i){
            int rNumber = random.nextInt(end - start - i);
            result.add(numbers.get(rNumber));
            numbers.remove(rNumber);
        }
        return result;
    }

    private static String getStr(int chapterNum){
        String format = null;
        if(chapterNum <= 80)
            format = "0|./data/0/%d.txt";
        else if(chapterNum <=120)
            format = "1|./data/1/%d.txt";
        else {
            chapterNum = chapterNum - 120;
            format = "2|./data/2/%d.txt";
        }

        return String.format(format, chapterNum);
    }

    private static void generate(){
        ArrayList<Integer> all = removeRandom(null,0, 80, 20);
        ArrayList<Integer> last40 = removeRandom(null,80,120,20);
        ArrayList<Integer> sg40 = removeRandom(null,120,240,40);
        all.addAll(last40);
        all.addAll(sg40);

        BufferedWriter datafile = null;
        PrintWriter out = null;
        String filename = "TrainingSetDir.txt";
        String testSetName = "TestingSetDir.txt";
        try{
            datafile = new BufferedWriter(new FileWriter(filename));
            out = new PrintWriter(testSetName);
            for(int i : all){
                String line = getStr(i);
                datafile.write(line);
                datafile.newLine();
                // 写入训练集
            }
            datafile.flush();

            ArrayList<Integer> test = removeRandom(all, 0, 240, 80);
            for(int i : test){
                String line = getStr(i);
                line = line.split("\\|")[1];
                out.println(line);
            }
            out.flush();
        } catch (Exception ex){
            System.out.println("写入失败：" + filename);
        }
    }
    public static void main(String[] args) throws Exception{
        generate();
    }

}
