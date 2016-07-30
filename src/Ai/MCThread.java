package Ai;

import java.util.Random;
import javafx.concurrent.Task;


public class MCThread extends  Task<Integer>{
    MCMutiAi data;
    Random random;
    int times;
    int tot;
    Node wait;
    int waitset;
    Thread th;
    public MCThread(MCMutiAi data) {
            // TODO Auto-generated constructor stub
            this.data = data;
            random = data.random;
            //random = new Random(seed);
            th = new Thread(this);
            th.setDaemon(true);
    }

              @Override 
              protected Integer call() throws Exception {
            //public void run(){
                    //System.out.println("run");
                    for (times = 1; times < data.maxstep; ++times) {
                            if ((times & 32) == 0 && System.currentTimeMillis() - data.begin_time > data.limit_time)
                                    break;

                            int v = TreePolicy(0);
                            int delta = DefaultPolicy(v);
                            Backup(v, delta);
                    }	
                    return 0;
    }

    public int TreePolicy(int v){
            int tmpV;
            Node currentnode;
            while (true){
                    currentnode = data.pool[v];

                    double maxP = -1e5;
                    int maxV = -1;
                    int  i = 0,x = 0;
                    double c2 = 0;

                            if (currentnode.N>0)
                                    c2 = data.c * Math.sqrt(2 * Math.log(currentnode.N));


                    for (i = 0; i < currentnode.avaidcnt; ++ i){
                                    x = currentnode.avaid[i];
                                    int j = currentnode.son[x];
                                    if (j == -1){
                                            break;
                                    } else{
                                            double value = (double)data.pool[j].Q * data.inv[data.pool[j].N] + c2 * data.sqrtArray[data.pool[j].N];

                                            if (value > maxP) {
                                                    maxP = value;
                                                    maxV = j;
                                            }
                                    }
                    }


                    if (i < currentnode.avaidcnt){


                                            data.pool[tot] = new Node(currentnode);
                                            data.pool[tot].setfa(v);
                                            data.pool[tot].set(x);
                                            wait = currentnode;
                                            waitset = x;
                                            //currentnode.son[x] = tot;
                                            return tot;

                    }
                    else {
                            if (maxV==-1) {
                                    wait=null;
                                    return v;
                            }
                            v = maxV;
                    }


            }

    }

    public int DefaultPolicy(int v){	
            Node current;

            current = new Node(data.pool[v]);

            int turn = current.turn;

            while (current.finish==-2) {
                    int w = random.nextInt(current.avaidcnt);
                    current.set(current.avaid[w]);
            }

            return current.finish;
    }

    public void Backup(int v,int delta){
            while (v != -1) {
                    synchronized (data.pool[v]) {
                            Node current = data.pool[v];
                            ++current.N;
                            if (current.turn==data.state.turn)
                                    current.Q += delta;
                            else
                                    current.Q -= delta;
                            if(current==wait)
                            {
                                    wait.son[waitset] = tot;
                                    ++tot;
                            }
                            v = current.fa;
                    }
    }
    }
}
