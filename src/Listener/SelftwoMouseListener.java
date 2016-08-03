package Listener;

import ChessBoard.ChessState;
import ChessBoard.Clock;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class SelftwoMouseListener  extends FatherListener{
    Clock clock;
    public SelftwoMouseListener(ChessState state,Clock clock){
            super(state,0);
            this.clock = clock;
    }

    public void next(int rawx,int rawy) {
            // TODO Auto-generated method stub
            int x = (rawx-border);
            int y = (rawy-border);

       if (rawx>=maxsize+border || rawy>=maxsize+border) return;

            //System.out.println(y);
            if (x%gridsize==0 || y%gridsize==0) return;
            x /= gridsize;
            y /= gridsize;
        if (x<0 || y<0 || x>=maxn || y>=maxn) return;

            if (state.flag[x][y]!=2) return;
            int lastturn = state.turn;
            state.set(x,y);
            
            if (state.canback(lastturn))
                huiButton.setDisable(false);
           clock.restart();

    }

    @Override
    public void timeout() {
        state.pass();
        clock.restart();
    }
}
