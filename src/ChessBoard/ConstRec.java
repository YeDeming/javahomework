package ChessBoard;

public class ConstRec {
	final static int border = 10;
	public final static int maxn = 8;
	final static int gridsize = 60;
	final static int maxsize = 2*border+maxn*gridsize;
	final static int fx[][] =  {{0,1},{1,0},{0,-1},{-1,0}, 
													{-1,1},{1,-1},{-1,-1},{1,1}};
	public final static int maxstep = 500000;

}
