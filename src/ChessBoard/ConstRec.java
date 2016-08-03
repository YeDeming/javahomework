package ChessBoard;

public class ConstRec {
    
   public final static int border = 10;
   public  static int gridsize = 60;

    public final static int maxn = 8;
    public final static int maxsize = 2*border+maxn*gridsize;

    public final static int stdborder = 10;
    public final static int stdgridsize = 60;
    public final static int stdmaxsize = 2*stdborder+maxn*stdgridsize;
    

    public static int maxsizex = 2*border+maxn*gridsize;
  
    public static int maxsizey = 2*border+maxn*gridsize;    
    
    public final static int fx[][] =  {{0,1},{1,0},{0,-1},{-1,0}, 
                                                      {-1,1},{1,-1},{-1,-1},{1,1}};
    public final static int maxstep = 500000;
    public static int limitsecond = 10;
    
    public static void update(){
         ConstRec.gridsize = (Math.min(ConstRec.maxsizex,ConstRec.maxsizey)-2*ConstRec.border)/ConstRec.maxn;
    }
}
