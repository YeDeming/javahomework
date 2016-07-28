package ChessBoard;

import javafx.event.*;
import javafx.scene.input.MouseEvent;

abstract public class FatherListener{

    final static int border = ConstRec.border;
    final static int maxn = ConstRec.maxn;;
    final static int gridsize = ConstRec.gridsize;;
    final static int maxsize = 2*border+maxn*gridsize;
    ChessState state;

    public FatherListener(ChessState state) {
        this.state = state;
    }
    
    abstract void next(int rawx,int rawy);
}
