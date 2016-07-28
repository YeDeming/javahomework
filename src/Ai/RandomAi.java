package Ai;
import java.util.Random;

import ChessBoard.ChessState;

public class RandomAi extends FatherAi{
    Random random;
    public RandomAi(ChessState state,int ai_turn) {
            // TODO Auto-generated constructor stub
            super(state,ai_turn);
            random = new Random();
    }
    @Override
    public void strategy() {
            // TODO Auto-generated method stub
            int r = random.nextInt(state.avaidcnt);
            state.set(state.avaid[r]>>3,state.avaid[r] & 7);
    }

}
