public class main {
    public static void main(String args[]) throws Exception{
        //生成特征值文件
        AttributeGenerator ag = new AttributeGenerator(args[0]);
        ag.genAttribute(true);
        ag.storeAttr("chapter_train.arff");
        AttributeGenerator ag1 = new AttributeGenerator(args[1]);
        ag1.genAttribute(false);
        ag1.storeAttr("chapter_test.arff");

        // 训练
        WekaTrainer wt = new WekaTrainer("chapter_train.arff");
        wt.classify("chapter_test.arff");
        wt.store("HW3_1500015877.txt");
    }
}
