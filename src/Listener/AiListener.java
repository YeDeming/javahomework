package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;

import Ai.*;
import ChessBoard.BasicBoard;
import ChessBoard.ChessState;

public class AiListener  extends FatherListener{

    public FatherAi ai;
    int kind;
    Thread th;
    public AiListener(ChessState state,int player_turn,int kind, BasicBoard basicBoard){
            super(state,player_turn);
            this.kind = kind;
            if (kind==1){
                ai = new RandomAi(state,player_turn^1,basicBoard);
                System.out.println("RandomAi selected");
            }
            else if (kind == 2){
                ai = new MCAi(state,player_turn^1,basicBoard);
                System.out.println("MCAi selected");
            } else{
                ai = new MCMutiAi(state,player_turn^1,basicBoard);
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
        if (kind==3){
            ((MCMutiAi)ai).stop();
        }
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
           huiButton.setDisable(false);

}

}
