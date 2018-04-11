////////
import javafx.scene.transform.Rotate;
import org.w3c.dom.events.EventException;

import java.util.*;
import java.io.*;

////////
public class HW2_1500015877 extends Tetris {
	// enter your student id here
	public String id = new String("1500015877");

	// dummy
	public boolean dummyBoard[][];
	public boolean dummyPiece[][];
	public int dummy_piece_x, dummy_piece_y;
	public int best_r = 0; // 最佳旋转
	public int best_h = 0; // 最佳水平位移
	public int best_j = -1; // 最佳水平位移方向
	public  boolean bestBoard[][]; // bestBoard引用

	public class state{
		public boolean board[][];
		public boolean piece[][];
		public int x;
		public int y;
		public state(boolean[][] b, boolean[][] p, int x, int y){
			board = b;
			piece = p;
			this.x = x;
			this.y = y;
		}
	}

	public void reset(state s){
		dummyBoard = s.board;
		dummyPiece = s.piece;
		dummy_piece_x = s.x;
		dummy_piece_y = s.y;
	}

	public HW2_1500015877(){
		super();
		bestBoard = new boolean[h][w];
		dummyBoard = new boolean[h][w];
		dummyPiece = new boolean[4][4];
		dummy_piece_x = 0;
		dummy_piece_y = 0;
	}

	public boolean[][] getDummyBoard(){
		boolean tmp[][] = new boolean[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				tmp[y][x] = dummyBoard[y][x];
			}
		}
		return tmp;
	}

	public boolean[][] getDummyPiece(){
		boolean tmp[][] = new boolean[4][4];
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				tmp[y][x] = dummyPiece[y][x];
			}
		}
		return tmp;
	}

	private boolean dummyMovePiece(PieceOperator op){
		// remove piece from board
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (dummyPiece[y][x]) dummyBoard[dummy_piece_y-y][dummy_piece_x+x] = false;
			}
		}
		// generate a new piece
		int new_piece_x =dummy_piece_x;
		int new_piece_y = dummy_piece_y;
		boolean new_piece[][] = new boolean[4][4];
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				new_piece[y][x] = dummyPiece[y][x];
			}
		}
		// piece operation
		switch (op) {
			case ShiftLeft:  new_piece_x--; break;
			case ShiftRight: new_piece_x++; break;
			case Drop:       new_piece_y--; break;
			case Rotate:
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						new_piece[y][x] = dummyPiece[x][3-y];
					}
				}
				break;
		}
		// check if new_piece is deployable
		boolean deployable = true;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (!new_piece[y][x]) continue;
				if (new_piece_x+x < 0 || new_piece_x+x >= w
						|| new_piece_y-y < 0 || new_piece_y-y >= h
						|| dummyBoard[new_piece_y-y][new_piece_x+x]) {
					deployable = false;
					break;
				}
			}
		}
		if (deployable) {
			// replace piece with new_piece
			dummy_piece_x = new_piece_x;
			dummy_piece_y = new_piece_y;
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					dummyPiece[y][x] = new_piece[y][x];
				}
			}
		}
		// deploy piece
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (dummyPiece[y][x]) dummyBoard[dummy_piece_y-y][dummy_piece_x+x] = true;
			}
		}

		return deployable;
	}

	private int boardPatchScore(boolean[][] b){
		int count = 0;
		for(int i=0; i<h; ++i){
			for(int j=0; j<w-1; ++j) {
				if(b[i][j] && b[i][j+1]) count++;
			}
		}
		return count;
	}
	private boolean isBetter(boolean[][] b1, boolean[][] b2){
        for(int i=0; i<h; ++i){
            int count1 = 0;
            int count2 = 0;
            for(int j=0; j<w; ++j){
                if(b1[i][j]) count1++;
                if(b2[i][j]) count2++;
            }

            if(count1 == count2) continue;

            return count1 > count2; // 自底而上只要最底面的某一行的填满的格子更多，则更好
        }

        return false;
	}

	public void findBestPath(){
		reset(new state(getBoard(), getPiece(), getPieceX(), getPieceY())); // 获取最新的dummy信息

		for(int i=0; i<4; ++i){
			dummyMovePiece(PieceOperator.Rotate); // 旋转
			state s1 = new state(getDummyBoard(), getDummyPiece(), dummy_piece_x, dummy_piece_y);
			// 记录当前状态
			for(int j=-1; j<2; ++j){
				int h = 0;
				while(j==-1 || dummyMovePiece(PieceOperator.values()[j])){
					state s2 = new state(getDummyBoard(), getDummyPiece(), dummy_piece_x, dummy_piece_y);
					// 记录当前状态

					if(j != -1) h++; // 记录水平位移的次数，用j==-1表示没有水平位移

					while(dummyMovePiece(PieceOperator.Drop)); // 持续落到底

					if((j == -1 && i==0)|| isBetter(dummyBoard, bestBoard)){ // 直接下落时作为比较基准，设置为初始的bestboard
						bestBoard = getDummyBoard();
						best_h = h; // 最佳水平位移
						best_r = i+1; // 最佳旋转次数
						best_j = j; // 最佳水平位移方向
					}

					reset(s2); // 重置以便循环使用
					if(j == -1) break; // j==-1表示没有水平位移，不需要循环
				}
			}

			reset(s1); // 重置以便循环使用
		}
	}

	// ####
	public PieceOperator robotPlay() {
		if(getPieceX() == w/2 && getPieceY() == (h-1) && best_r == 0) findBestPath();
		// 当新的形状deploy时寻找最佳方案

		if(best_r > 0){
			best_r--;
			return PieceOperator.Rotate;
		} // 旋转

		if(best_h > 0){
			best_h--;
			return PieceOperator.values()[best_j];
		} // 平移

		return PieceOperator.Drop; // 既不平移也不旋转就直接下落
	}
}


