package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;

import Ai.*;
import ChessBoard.ChessState;

public class AiListener  extends FatherListener{
    int player_turn;
    public FatherAi ai;
    Thread th;
    public AiListener(ChessState state,int player_turn,int kind){
            super(state);
            this.player_turn = player_turn;
            if (kind==1){
                ai = new RandomAi(state,player_turn^1);
                System.out.println("RandomAi selected");
            }
            else if (kind == 2){
                ai = new MCAi(state,player_turn^1);
                System.out.println("MCAi selected");
            } else{
                ai = new MCMutiAi(state,player_turn^1);
                System.out.println("MCMutiAi selected");
            }
            th = new Thread(ai);
            th.setDaemon(true);
    }

    public void start(){
        th.start();
        //ai.start();
    }
    public void stop(){
        th.stop();
    }
    public void next(int rawx, int rawy) {
        // TODO Auto-generated method stub
        if (player_turn!=state.turn)
            return;
       if (rawx>=maxsize || rawy>=maxsize) return;

        int x = (rawx-border);
        int y = (rawy-border);

        if (x%gridsize==0 || y%gridsize==0) return;
        x /= gridsize;
        y /= gridsize;

        if (state.flag[x][y]!=2) return;
        state.set(x,y);

}

}
