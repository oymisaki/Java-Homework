import java.io.*;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;

public class WekaDemo {

    public static Evaluation classify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
    }

    public static double calculateAccuracy(ArrayList<Prediction> predictions) {
    }

    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds){
    }

    public static void main(String[] args) throws Exception{
        BufferedReader datafile = null;
        String filename = "weather.arff";
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
                    + String.format("%.2f%%", accuracy)
                    + "\n------------------------------------");
        }
    }
}
