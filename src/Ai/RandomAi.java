package Ai;
import ChessBoard.BasicBoard;
import java.util.Random;

import ChessBoard.ChessState;
import javafx.application.Platform;

public class RandomAi extends FatherAi{
    Random random;
    public RandomAi(ChessState state,int ai_turn,BasicBoard basicBoard) {
            // TODO Auto-generated constructor stub
            super(state,ai_turn,basicBoard);
            random = new Random();
    }
    @Override
    public void strategy() {
            // TODO Auto-generated method stub
            int r = random.nextInt(state.avaidcnt);
            
            state.set(state.avaid[r]>>3,state.avaid[r] & 7);
                        
     
    }

}
