public class main {
    public static void main(String args[]){
        AttributeGenerator ag = new AttributeGenerator("trainingSetDir.txt");
        ag.genAttribute();
        ag.storeAttr("chapter.arff");
    }

}
