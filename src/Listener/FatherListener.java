package Listener;

import ChessBoard.ChessState;
import ChessBoard.ConstRec;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

abstract public class FatherListener{

    //final static int border = ConstRec.border;
    final static int maxn = ConstRec.maxn;;
    //final static int gridsize = ConstRec.gridsize;
    //final static int maxsize = 2*border+maxn*gridsize;
    public ChessState state;
    Button huiButton;
    public int player_turn;
    public FatherListener(ChessState state,int player_turn) {
        this.state = state;
        this.player_turn = player_turn;
    }
    
    public abstract void next(int rawx,int rawy);

    public void setHuiButton(Button huiButton) {
        this.huiButton = huiButton;
    }
   public abstract void timeout();

    
}
