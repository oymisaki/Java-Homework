////////
import java.util.*;
import java.io.*;

////////
public class Main {
	// ####
	public static void main(String [] args) throws IOException, InterruptedException {
		int seed = new Random().nextInt(128);
		Random rand = new Random(seed);
		HW2_1500015877 tetris = new HW2_1500015877();
		int score = tetris.run(rand);
		System.out.println(tetris.id+": score="+score);
	}
}


