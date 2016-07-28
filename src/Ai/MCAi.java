package Ai;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.org.apache.xpath.internal.operations.And;

import ChessBoard.SampleChessState;
import ChessBoard.ChessState;
import ChessBoard.ConstRec;

public class MCAi extends FatherAi {
        Random random;
        final static int maxstep = ConstRec.maxstep;
        final static int limit_time = (int) (5000*0.9);
        final static double c = 1.414;
        double sqrtArray[] = new double[maxstep];
        double inv[] = new double[maxstep];
        Node pool[] = new Node[maxstep];

        int root,tot;
        long begin_time;
        public MCAi(ChessState state, int ai_turn) {
                super(state,ai_turn);
                // TODO Auto-generated constructor stub
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

                return current.finish;
        }

        public void Backup(int v,int delta){
                while (v != -1) {
                        ++pool[v].N;
                        if (pool[v].turn==state.turn)
                                pool[v].Q += delta;
                        else
                                pool[v].Q -= delta;
                        v = pool[v].fa;
                }
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
