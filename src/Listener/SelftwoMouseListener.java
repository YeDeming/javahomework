package Listener;

import ChessBoard.ChessState;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class SelftwoMouseListener  extends FatherListener{
	
    public SelftwoMouseListener(ChessState state){
            super(state);
    }

    public void next(int rawx,int rawy) {
            // TODO Auto-generated method stub
            int x = (rawx-border);
            int y = (rawy-border);

            if (rawx>=maxsize || rawy>=maxsize) return;

            //System.out.println(y);
            if (x%gridsize==0 || y%gridsize==0) return;
            x /= gridsize;
            y /= gridsize;

            if (state.flag[x][y]!=2) return;
            state.set(x,y);

    }

 
}
