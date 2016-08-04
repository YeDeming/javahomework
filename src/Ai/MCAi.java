package Ai;

import ChessBoard.BasicBoard;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ChessBoard.SampleChessState;
import ChessBoard.ChessState;
import ChessBoard.ConstRec;

public class MCAi extends FatherAi {
        Random random;
        final static int maxstep = ConstRec.maxstep;
        int limit_time = (int) (ConstRec.limitsecond*900);
        final static double c = 1.414;
        double sqrtArray[] = new double[maxstep];
        double inv[] = new double[maxstep];
        Node pool[] = new Node[maxstep];

        int root,tot;
        long begin_time;
        public MCAi(ChessState state, int ai_turn,BasicBoard basicBoard) {
                super(state,ai_turn,basicBoard);
                // TODO Auto-generated constructor stub
                //limit_time = (int) (ConstRec.limitsecond*900);

                random = new Random();
                for (int i = 1; i < maxstep; ++i) {
                        sqrtArray[i] = 1.0 / Math.sqrt(i);
                        inv[i] = 1.0 / i;
                }
        }

        @Override
        public void strategy() {
                // TODO Auto-generated method stub
                begin_time = System.currentTimeMillis(); //获取开始时间
                int ans = UCTSearch();
                //System.out.println("my turn:" + ai_turn);
                state.set(ans>>3,ans & 7);
                
        }

        public int  UCTSearch(){	
                root = 0;
                pool[0] = new Node(state);
                tot = 1;
                int i  = 0;
                for  (i = 1; i < maxstep; ++i) {
                        if ((i & 32) == 0 && System.currentTimeMillis() - begin_time > limit_time)
                                break;
                        int v = TreePolicy(root);

                        int delta = DefaultPolicy(v);
                        //System.out.println(delta);
                        Backup(v, delta);
                }
                System.out.println("Calc" + i + "steps");

                int tmp = BestChildTrue(root);
                if (tmp == -1)
                        return 0;
                else
                        return tmp;
        }

        public int TreePolicy(int v){
                int tmpV;
                Node currentnode;
                while (true){
                        currentnode = pool[v];

                        double maxP = -1e5;
                        int maxV = -1;
                        int  i = 0,x = 0;
                        double c2 = 0;
                        if (currentnode.N>0)
                                c2 = c * Math.sqrt(2 * Math.log(currentnode.N));
                        for (i = 0; i < currentnode.avaidcnt; ++ i){
                                        x = currentnode.avaid[i];
                                        int j = currentnode.son[x];
                                        if (j == -1){
                                                break;
                                        } else{
                                                double value = (double)pool[j].Q * inv[pool[j].N] + c2 * sqrtArray[pool[j].N];
                                                if (value > maxP) {
                                                        maxP = value;
                                                        maxV = j;
                                                }
                                        }
                        }


                        if (i < currentnode.avaidcnt){
                           
                                pool[tot] = new Node(currentnode);
                                pool[tot].setfa(v);

                                pool[tot].set(x);
       
                                
                                if (pool[tot].finish==1){
                                    pool[tot].turn = 1;
                                } else  if (pool[tot].finish==-1){
                                    pool[tot].turn = 0;
                                } 
                                currentnode.son[x] = tot;
                                return tot++;
                        }
                        else {
                                if (maxV==-1) {

                                        return v;
                                }
                                v = maxV;
                        }
                }

        }

        public int DefaultPolicy(int v){	
                Node current = new Node(pool[v]);

                int turn = current.turn;

                while (current.finish==-2) {
                        int w = random.nextInt(current.avaidcnt);
                        current.set(current.avaid[w]);
                }
                if (ai_turn==0)
                    return -current.finish;
                else
                    return current.finish;
        }

        public void Backup(int v,int delta){
                while (v != -1) {
                        ++pool[v].N;
                        if (pool[v].turn==ai_turn)
                                pool[v].Q += delta;
                        else
                                pool[v].Q -= delta;
                        v = pool[v].fa;
                }
        }
        
        /*public double fixvalue(int w, int N){
            if (w==0 || w==7 || w==63 || w==56) return 1.0/N;
            int x = (w>>3);
            int y = (w&7);
            if (x==0 || x==8){
                int l = y;int r = y;
                while (state.flag[])
            }
            return 0;
        }*/

        public int BestChildTrue(int root){
                Node currentnode = pool[root];
                double maxP = -1e5;
                int maxV = -1;
                System.out.println("-----------------");
                int  i,x = 0;
                for (i = 0; i < currentnode.avaidcnt; ++ i){
                        x = currentnode.avaid[i];
                        int j = currentnode.son[x];
                        if (j !=-1){
                                double value = (double)pool[j].Q * inv[pool[j].N];
                                //value+=fixvalue(x,currentnode.avaidcnt);
                                System.out.println((x>>3) + " "  + (x&7)+  " "+value);
                                if (value > maxP) {
                                        maxP = value;
                                        maxV = x;
                                }
                        }
                }
                return maxV;
        }


}
