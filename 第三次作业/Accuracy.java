import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Accuracy {
    public static void main(String[] args) throws Exception{
        BufferedReader datafile = null;
        datafile = new BufferedReader(new FileReader("TestingSetDir.txt"));
        String line = datafile.readLine();
        ArrayList<Integer> typeList = new ArrayList<>();
        while (line != null){
            String type = line.substring(7,8);
            typeList.add(Integer.parseInt(type));
            line = datafile.readLine();
        }

        datafile = new BufferedReader(new FileReader("HW3_1500015877.txt"));
        line = datafile.readLine();
        ArrayList<Integer> predictions = new ArrayList<>();
        while(line != null){
            predictions.add(Integer.parseInt(line.split("\\.")[0]));
            line = datafile.readLine();
        }

        int correct = 0;
        for(int i=0; i<typeList.size(); ++i){
            if(typeList.get(i) == predictions.get(i))
                correct++;
        }
        double accuracy = 100 * correct / predictions.size();
        System.out.println(String.format("Accuracy: %.2f%%", accuracy));
    }
}
