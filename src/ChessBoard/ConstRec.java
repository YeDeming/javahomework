package ChessBoard;

public class ConstRec {
	public final static int border = 10;
	public final static int maxn = 8;
	public final static int gridsize = 60;
	public final static int maxsize = 2*border+maxn*gridsize;
	public final static int fx[][] =  {{0,1},{1,0},{0,-1},{-1,0}, 
													{-1,1},{1,-1},{-1,-1},{1,1}};
	public final static int maxstep = 500000;

}
