package ChessBoard;

public class SampleChessState {
    final static int maxn = ConstRec.maxn;
    final static int maxnsqr = maxn*maxn;

    public int fx[][] = ConstRec.fx;

    public int flag[][], avaid[];

    public int count,avaidcnt,turn;
    public int finish = -2;
    boolean start = false;

    public SampleChessState() {
            // TODO Auto-generated constructor stub
            avaid = new int[maxn*maxn];
            flag = new int[maxn][maxn];
    }

    public SampleChessState(SampleChessState state) {
            // TODO Auto-generated constructor stub
            super();
            avaid = new int[maxn*maxn];
            flag = new int[maxn][maxn];
            for (int i = 0; i < maxn; ++ i)
            {
                    this.flag[i] = state.flag[i].clone();
            }
                    this.count = state.count;
            this.avaidcnt = state.avaidcnt;
            this.turn = state.turn;
            this.avaid = state.avaid.clone();
            this.finish = state.finish;
    }
    public void show(){
            for (int i = 0; i < maxn; ++ i){
                    for (int j = 0; j < maxn; ++ j)
                            System.out.print(flag[i][j]);
                    System.out.println();
            }
    }
    public void updateavaid(){
            avaidcnt = 0;
            int opp = turn^1;

            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++ j)
                            if (flag[i][j]==2){
                                    int k;
                                    for (k = 0; k < 8; ++ k){
                                            int x = i + fx[k][0];
                                            int y = j + fx[k][1];

                                            if (x<0 || y<0 || x>=maxn || y>=maxn || flag[x][y] == turn) continue;

                                            while (flag[x][y]==opp){
                                                    x += fx[k][0];
                                                    y += fx[k][1];
                                                    if (x<0 || y<0 || x>=maxn || y>=maxn) break;
                                            }
                                            if (x<0 || y<0 || x>=maxn || y>=maxn) continue;
                                            if (flag[x][y]==turn)
                                                    break;
                                    }

                                    if (k < 8){
                                            avaid[avaidcnt++] = (i<<3) | j;
                                    }
                            }
    }

    public int getwinner(){
            int sum = 0;
            for (int i = 0 ;i < maxn; ++ i)
                    for (int j = 0 ; j < maxn; ++ j)
                            if (flag[i][j]==0) sum ++;
                            else if (flag[i][j]==1) sum--;
            if (sum>0) return 1;
            else if (sum==0) return 0;
            else return -1;
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
            } 
    }

    public void pass(){
            turn ^= 1;
            updateavaid();
    }

}
