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

    public static Evaluation classify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
        Evaluation evaluation = new Evaluation(trainingSet);
        model.buildClassifier(trainingSet);
        evaluation.evaluateModel(model, testingSet);
        return evaluation; // 估计模型
    }

    public static double calculateAccuracy(ArrayList<Prediction> predictions) {
        double correct = 0;
        for(int i=0; i<predictions.size(); i++){
            NominalPrediction np = (NominalPrediction) predictions.get(i);
            if(np.predicted() == np.actual()){
                correct++;
            }
        }
        return 100 * correct / predictions.size();
    }

    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds){
        Instances[][] split = new Instances[2][numberOfFolds];
        for(int i=0; i<numberOfFolds; i++){
            split[0][i] = data.trainCV(numberOfFolds, i); // 剩下9/10作为train
            split[1][i] = data.testCV(numberOfFolds, i); // 剩下1/10作为test
        }
        return split;
    }

    public static void train(String trainFile, String testFile) throws Exception{
        BufferedReader datafile = null;
        try{
            datafile = new BufferedReader(new FileReader(trainFile));
        } catch (FileNotFoundException ex){
            System.err.println("File not found:" + trainFile);
        }
        Instances trainData = new Instances(datafile);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        // 将某个attr设置为类别

        try{
            datafile = new BufferedReader(new FileReader(testFile));
        } catch (FileNotFoundException ex){
            System.err.println("File not found:" + testFile);
        }
        Instances testData = new Instances(datafile);
        testData.setClassIndex(trainData.numAttributes() - 1);
        // 将某个attr设置为类别

        Classifier[] models = {
                new J48(),             // 决策树
                new DecisionTable(),    // 精度表
                new DecisionStump()    // 一阶决策树
        };
        for(int j=0; j<models.length; j++){
            ArrayList<Prediction> predictions = new ArrayList<>();
            Evaluation validation = classify(models[j], trainData, testData);
            predictions.addAll(validation.predictions());

            double accuracy = calculateAccuracy(predictions);
            // 打印当前模型的精度和名称
            System.out.println("Accuracy of" + models[j].getClass().getSimpleName() + ":"
                    + String.format("%.2f%%", accuracy)
                    + "\n------------------------------------");
        }
    }
    /*
    public static void main(String[] args) throws Exception{
        BufferedReader datafile = null;
        String filename = "weather.nominal.arff";
        try{
            datafile = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex){
            System.err.println("File not found:" + filename);
        }

        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        // 将某个attr设置为类别

        // 输入数据为一个整体，需要分训练集测试集，此函数分测试和训练集
        Instances[][] split = crossValidationSplit(data, 10);

        Instances[] trainingSplits = split[0]; // 取出训练集
        Instances[] testingSplits = split[1]; // 取出测试集

        // 一系列分类器
        Classifier[] models = {
                new J48(),             // 决策树
                new DecisionTable(),    // 精度表
                new DecisionStump()    // 一阶决策树
        };

        // 对每个分类器都跑一遍
        for(int j=0; j<models.length; j++){
            // 收集模型的每组预测值
            ArrayList<Prediction> predictions = new ArrayList<>();
            // 对每个训练集-测试集，训练并测试分类器
            for(int i=0; i<trainingSplits.length; i++){
                Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
                predictions.addAll(validation.predictions());
                System.out.println(models[j].toString());
                // 查看模型
            }
            // 计算当前模型的整体精确度
            double accuracy = calculateAccuracy(predictions);
            // 打印当前模型的精度和名称
            System.out.println("Accuracy of" + models[j].getClass().getSimpleName() + ":"
                    + String.getStr("%.2f%%", accuracy)
                    + "\n------------------------------------");
        }
    }*/
}
