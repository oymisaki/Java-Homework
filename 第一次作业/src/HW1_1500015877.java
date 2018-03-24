/*
第一次作业反思：
1. 正确分析题意，弄清本质，设计方案
2. 基类、继承、接口、匿名类
3. 静态方法里一定要是静态成员，而非静态方法里可以使用静态成员
4. 利用静态成员的不变性，将问题questions设计为静态，将变的和不变的严格区分开
5. 静态匿名类方法闭包绑定answers数组，会绑定生成类方法时的answers数组，所以将answers数组作为参数传入
    因此以后使用外部变量的时候一定要注意，外部变量绑定是否正确会影响到结果
6. 类实例里面的元素（数组、类）的相互赋值 A.i = B.i ，是传引用的， Integer 这样的类型除外
7. debug，F7跳入，F8单步，F9执行到断点
8. debug, 通过add to watch 和跳入查看绑定的外部变量（闭包）
*/
import java.util.*;

enum Answer{A, B, C, D}

class Question{
    boolean choice(Answer answer, Answer[] answers){ return true;}
}

class Questions{
    static ArrayList<Question> questions = new ArrayList<Question>();
    Answer[] answers = new Answer[10];

    private int[] count(Answer[] answers){
        int[] c = {0, 0, 0, 0};
        for(Answer a:answers){
            if(a == null)
                continue;
            switch (a){
                case A:
                    c[0]++;
                    break;
                case B:
                    c[1]++;
                    break;
                case C:
                    c[2]++;
                    break;
                case D:
                    c[3]++;
                    break;
                default:
                    break;
            }
        }
        return c;
    }

    private static int min(int a, int b , int c){
        return Math.min(a, Math.min(b, c));
    }

    private static int min(int[] c){
        int m = 11;
        for(int i:c)
            m = Math.min(i,m);
        return m;
    }

    private static int max(int[] c){
        int m = 0;
        for(int i:c)
            m = Math.max(i,m);
        return m;
    }

    public void init(){
        questions.add(new Question()); //q1
        questions.add(new Question() {
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[4]==null||answers[4] == Answer.C;
                    case B:
                        return answers[4]==null||answers[4] == Answer.D;
                    case C:
                        return answers[4]==null||answers[4] == Answer.A;
                    case D:
                        return answers[4]==null||answers[4] == Answer.B;
                }
                return true;
            }
        }); //q2
        questions.add(new Question() {
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                boolean equal;
                boolean not_equal;
                boolean is_null;
                switch (answer) {
                    case A:
                        equal = answers[1] == answers[3] || answers[3] == answers[5];
                        not_equal = answers[1] != answers[2];
                        is_null = answers[3] == null || answers[5] == null;
                        return is_null || (equal && not_equal) ;
                    case B:
                        equal = answers[1] == answers[2] || answers[2] == answers[3];
                        not_equal = answers[1] != answers[5];
                        is_null = answers[3] == null;
                        return is_null || (equal && not_equal);
                    case C:
                        equal = answers[2] == answers[3] || answers[3] == answers[5];
                        not_equal = answers[1] != answers[2];
                        is_null = answers[3] == null || answers[5] == null;
                        return is_null || (equal && not_equal);
                    case D:
                        equal = answers[1] == answers[2] || answers[2] == answers[5];
                        not_equal = answers[1] != answers[3];
                        is_null = answers[5] == null;
                        return is_null || (equal && not_equal);
                }
                return true;
            }
        }); //q3
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[4] == null || answers[0] == answers[4];
                    case B:
                        return answers[6] == null || answers[1] == answers[6];
                    case C:
                        return answers[8] == null || answers[0] == answers[8];
                    case D:
                        return answers[9] == null || answers[5] == answers[9];
                }
                return true;
            }
        }); //q4
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[7]==null || answer == answers[7];
                    case B:
                        return answers[3]==null || answer == answers[3];
                    case C:
                        return answers[8]==null || answer == answers[8];
                    case D:
                        return answers[6]==null || answer == answers[6];
                }
                return true;
            }
        }); //q5
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[7] == null ||
                                (answers[1] == answers[7] &&
                                        answers[3] == answers[7]);
                    case B:
                        return answers[7] == null ||
                                (answers[0] == answers[7] &&
                                        answers[5] == answers[7]);
                    case C:
                        return answers[9] == null ||
                                (answers[2] == answers[7] &&
                                        answers[9] == answers[7]);
                    case D:
                        return answers[8] == null ||
                                (answers[4] == answers[7] &&
                                        answers[8] == answers[7]);
                }
                return true;
            }
        }); //q6
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                for(Answer a:answers)
                    if(a == null) return true;
                int[] c = count(answers);
                switch (answer) {
                    case A:
                        return c[2] < min(c[0],c[1],c[3]);
                    case B:
                        return c[1] < min(c[0],c[2],c[3]);
                    case C:
                        return c[0] < min(c[1],c[2],c[3]);
                    case D:
                        return c[3] < min(c[0],c[1],c[2]);
                }
                return true;
            }
        }); //q7
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[6] == null ||
                                (Math.abs(answers[6].ordinal() - answers[0].ordinal()) > 1);
                    case B:
                        return answers[4] == null ||
                                (Math.abs(answers[4].ordinal() - answers[0].ordinal()) > 1);
                    case C:
                        return answers[1] == null ||
                                (Math.abs(answers[1].ordinal() - answers[0].ordinal()) > 1);
                    case D:
                        return answers[9] == null ||
                                (Math.abs(answers[9].ordinal() - answers[0].ordinal()) > 1);
                }
                return true;
            }
        }); //q8
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                if(answer == null)
                    return true;
                switch (answer) {
                    case A:
                        return answers[0] == answers[5] ^
                                answers[4] == answers[5];
                    case B:
                        return answers[9] == null ||
                                (answers[0] == answers[5] ^
                                        answers[4] == answers[9]);
                    case C:
                        return answers[0] == answers[5] ^
                                answers[1] == answers[4];
                    case D:
                        return answers[0] == answers[5] ^
                                answers[4] == answers[8];
                }
                return true;
            }
        }); //q9
        questions.add(new Question(){
            public boolean choice(Answer answer, Answer[] answers) {
                for(Answer a:answers)
                    if(a == null) return true;
                int[] c = count(answers);
                switch (answer) {
                    case A:
                        return (max(c) - min(c)) == 3;
                    case B:
                        return (max(c) - min(c)) == 2;
                    case C:
                        return (max(c) - min(c)) == 4;
                    case D:
                        return (max(c) - min(c)) == 1;
                }
                return true;
            }
        }); //q10
    }

    public boolean compatible(){
        boolean comp = true;
        for(int i=0; i<10; ++i)
            comp = comp && questions.get(i).choice(answers[i], answers);
        return comp;
    }
}
public class HW1_1500015877 {

    private static boolean end = false;
    private static Answer[] answers = new Answer[10];

    private static void compute(int i, Questions qs){
        if(end)
            return;

        if(i == 10 && qs.compatible()){
            for(int k=0; k<10; ++k)
                answers[k] = qs.answers[k];
            end = true;
            return;
        }
/*
        System.out.print(i);
        for(Answer a:qs.answers)
            System.out.print(a);
        System.out.println();
*/
        if(!qs.compatible())
            return; // 没找到，递归结束

        Questions qs1 = new Questions();
        for(int k=0; k<10; ++k) qs1.answers[k] = qs.answers[k];
        qs1.answers[i] = Answer.A;
        compute(i+1, qs1);

        qs1.answers[i] = Answer.B;
        compute(i+1, qs1);

        qs1.answers[i] = Answer.C;
        compute(i+1, qs1);

        qs1.answers[i] = Answer.D;
        compute(i+1, qs1);
    }

    public static void main(String[] args) {

        Questions qs = new Questions();
        qs.init();
        compute(0, qs);
        for(Answer a:answers)
            System.out.println(a);
    }
}