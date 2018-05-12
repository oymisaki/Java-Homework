import java.io.*;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;

public class WekaTrainer {

    private Evaluation evaluation;
    private Classifier model;
    private ArrayList<Prediction> predictions;

    public WekaTrainer(String trainFile) throws Exception{
        BufferedReader datafile = null;
        try{
            datafile = new BufferedReader(new FileReader(trainFile));
        } catch (FileNotFoundException ex){
            System.err.println("File not found:" + trainFile);
        }
        Instances trainData = new Instances(datafile);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        // 将某个attr设置为类别

        predictions = new ArrayList<>();
        evaluation = new Evaluation(trainData);
        model = new J48();
        model.buildClassifier(trainData);
    }

    public void classify(String  testFile) throws Exception {
        BufferedReader datafile = null;
        try{
            datafile = new BufferedReader(new FileReader(testFile));
        } catch (FileNotFoundException ex){
            System.err.println("File not found:" + testFile);
        }
        Instances testData = new Instances(datafile);
        testData.setClassIndex(testData.numAttributes() - 1);
        // 将某个attr设置为类别

        evaluation.evaluateModel(model, testData);
        predictions.addAll(evaluation.predictions());
    }

    public void store(String outFile) {
        PrintWriter out = null;
        try{
            out = new PrintWriter(outFile);
            for(Prediction p : predictions){
                NominalPrediction np = (NominalPrediction)p;
                out.println(np.predicted());
            }
            out.flush();
        }catch (Exception ex){
            System.out.println("写入失败：" + outFile);
        }
    }
}
