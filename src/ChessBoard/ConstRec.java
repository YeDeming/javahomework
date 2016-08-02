package ChessBoard;

public class ConstRec {
    public final static int maxn = 8;

    public final static int stdborderx = 10;
    public final static int stdgridsizex = 60;
    public final static int stdmaxsizex = 2*stdbordery+maxn*stdgridsizex;
    
    public final static int stdbordery = 10;
    public final static int stdgridsizey = 60;
    public final static int stdmaxsizey = 2*stdbordery+maxn*stdgridsizey;
    
    public static int border = 10;

    public static int gridsize = 60;
    public static int maxsize = 2*border+maxn*gridsize;
    
    public final static int fx[][] =  {{0,1},{1,0},{0,-1},{-1,0}, 
                                                      {-1,1},{1,-1},{-1,-1},{1,1}};
    public final static int maxstep = 500000;
    public static int limitsecond = 10;
}
