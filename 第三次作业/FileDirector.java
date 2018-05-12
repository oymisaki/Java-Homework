import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class FileDirector {
    public static ArrayList<Integer> removeRandom(ArrayList<Integer> remove, int start, int end, int count){
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

    public static String getStr(int chapterNum){
        String format = null;
        if(chapterNum <= 80)
            format = "0|./data/0/%d.txt";
        else
            format = "1|./data/1/%d.txt";
        String line = String.format(format, chapterNum);
        return line;
    }

    public static void generate(){
        ArrayList<Integer> all = removeRandom(null,0, 80, 20);
        ArrayList<Integer> last40 = removeRandom(null,80,120,20);
        all.addAll(last40);

        BufferedWriter datafile = null;
        PrintWriter out = null;
        String filename = "trainingSetDir.txt";
        String testSetName = "testingSetDir.txt";
        try{
            datafile = new BufferedWriter(new FileWriter(filename));
            out = new PrintWriter(testSetName);
            for(int i=0; i<all.size(); ++i){
                String line = getStr(all.get(i));
                datafile.write(line);
                datafile.newLine();
                // 写入训练集
            }

            ArrayList<Integer> test = removeRandom(all, 0, 120, 1);
            String line = getStr(test.get(0));
            out.println(line);
            out.flush();
            datafile.flush();
        } catch (Exception ex){
            System.out.println("写入失败：" + filename);
        }
    }
}
