package ChessBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;

import Ai.*;


public class AiListener  extends FatherListener{
    int player_turn;
    FatherAi ai;

    public AiListener(ChessState state,int player_turn){
            super(state);
            this.player_turn = player_turn;
            ai = new MCMutiAi(state,player_turn^1);
    }

    public void start(){
        ai.start();
    }
    public void stop(){
        ai.stop();
    }
    public void next(int rawx, int rawy) {
        // TODO Auto-generated method stub
        if (player_turn!=state.turn)
            return;

        int x = (rawx-border);
        int y = (rawy-border);

        if (x%gridsize==0 || y%gridsize==0) return;
        x /= gridsize;
        y /= gridsize;

        if (state.flag[x][y]!=2) return;
        state.set(x,y);

}

}
