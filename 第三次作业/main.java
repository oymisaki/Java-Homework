public class main {
    public static void main(String args[]) throws Exception{
        // 产生测试集和训练集文件目录
        FileDirector.generate();

        //生成特征值文件
        AttributeGenerator ag = new AttributeGenerator("trainingSetDir.txt");
        ag.genAttribute();
        ag.storeAttr("chapter_train.arff");
        AttributeGenerator ag1 = new AttributeGenerator("testingSetDir.txt");
        ag1.genAttribute();
        ag1.storeAttr("chapter_test.arff");

        // 训练
        WekaTrainer.train("chapter_train.arff", "chapter_test.arff");
    }

}
