package Ai;

import java.util.Set;

import ChessBoard.ChessState;
import ChessBoard.ConstRec;
import ChessBoard.SampleChessState;

public class Node extends SampleChessState{
	final static int maxn = ConstRec.maxn;
	final static int maxnsqr = maxn*maxn;
	int fa;
	int N, Q;
	int son[] = new int[maxn*maxn];

	public Node(SampleChessState state) {
		// TODO Auto-generated constructor stub
		super();
		for (int i = 0; i < maxn; ++ i)
			this.flag[i] = state.flag[i].clone();
		this.count = state.count;
		this.avaidcnt = state.avaidcnt;
		this.turn = state.turn;
		this.avaid = state.avaid.clone();
		this.finish = state.finish;
		N = Q = 0;
		fa = -1;
		for (int i = 0; i < 64; ++ i)
			son[i] = -1;
	}

	public void setfa(int fa){
		this.fa = fa;
	}

	
	public void set(int rt){
		int opp = turn^1;
		int i = rt>>3;
		int j = rt&7;
		//System.out.println(i + " " + j + " " + turn);
		for (int k = 0; k < 8; ++ k){
			int x = i + fx[k][0];
			int y = j + fx[k][1];
			if (x<0 || y<0 || x>=maxn || y>=maxn || flag[x][y] == turn) continue;
			
			while (flag[x][y]==opp){
				x += fx[k][0];
				y += fx[k][1];
				if (x<0 || y<0 || x>=maxn || y>=maxn) break;
			}
			if (x<0 || y<0 || x>=maxn || y>=maxn) continue;
			
			if (flag[x][y]==turn){
				x = i + fx[k][0];
				y = j + fx[k][1];
				while  (flag[x][y]==opp){
					flag[x][y] = turn;
					x += fx[k][0];
					y += fx[k][1];
				}
			}
		}

		flag[i][j] = turn;
		turn = turn^1;
		count ++;

		updateavaid();

		if (count<maxnsqr && avaidcnt==0) pass();

		if (count>=maxnsqr || avaidcnt == 0){		
			finish = getwinner();
			//System.out.println("nihao" + finish);

		} 
	}
	
	public void pass(){
		turn ^= 1;
		updateavaid();
	}
}