package Ai;

import ChessBoard.BasicBoard;
import java.util.ArrayList;
import java.util.Random;

import ChessBoard.SampleChessState;

import ChessBoard.ChessState;
import ChessBoard.ConstRec;

public class MCMutiAi extends FatherAi {
        Random random;
        final static int limit_time = (int) (2000*0.9);
        final static int maxstep = ConstRec.maxstep;
        final static int thread_num = 4;
        final static int maxstep_thread = maxstep * thread_num;
        final static double c = 1.414;
        double sqrtArray[] = new double[maxstep_thread];
        double inv[] = new double[maxstep_thread];
        Node pool[] = new Node[maxstep_thread];
        ArrayList<MCThread> threads;

        int root;
        long begin_time;

        boolean working = false;

        public MCMutiAi(ChessState state,int ai_turn,BasicBoard basicBoard) {
                super(state,ai_turn,basicBoard);
                // TODO Auto-generated constructor stub
                random = new Random();
                for (int i = 1; i < maxstep_thread; ++i) {
                        sqrtArray[i] = 1.0 / Math.sqrt(i);
                        inv[i] = 1.0 / i;
                }
        }

        @Override
        public void strategy() {
                // TODO Auto-generated method stub
                if (working) return;
                
                working = true;
                begin_time = System.currentTimeMillis(); //获取开始时间
                threads = new ArrayList<MCThread>();
                for (int i = 0; i < thread_num; ++ i){
                        threads.add(new MCThread(this)); //1+random.nextInt(65535)));
                }
                int ans = UCTSearch();
                state.set(ans>>3,ans & 7);
                working = false;
        }

        public int  UCTSearch(){	
                root = 0;
                pool[0] = new Node(state);

                for (int i = 0; i < thread_num; ++ i){
                        threads.get(i).tot = i*maxstep+1;
                        threads.get(i).th.start();
                }

                int alltimes = 0;
                for (int i = 0; i < thread_num; ++ i){
                        try{
                                threads.get(i).th.join();
                        } catch(Exception ee){

                        }
                                alltimes += threads.get(i).times;

                }
                System.out.println(alltimes + "steps");
                int tmp = BestChildTrue(root);
                if (tmp == -1)
                        return 0;
                else
                        return tmp;
        }


        public int BestChildTrue(int root){
                Node currentnode = pool[root];
                double maxP = -1e5;
                int maxV = -1;

                int  i,x = 0;
                for (i = 0; i < currentnode.avaidcnt; ++ i){
                        x = currentnode.avaid[i];
                        int j = currentnode.son[x];
                        if (j !=-1){
                                double value = (double)pool[j].Q * inv[pool[j].N];
                                if (value > maxP) {
                                        maxP = value;
                                        maxV = x;
                                }
                        }
                }
                return maxV;
        }
        
}
