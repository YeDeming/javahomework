package Ai;
import ChessBoard.ChessState;

public abstract class FatherAi extends Thread{
    ChessState state;
    int ai_turn;
    public FatherAi(ChessState state, int ai_turn) {
            // TODO Auto-generated constructor stub
            this.state = state;
            this.ai_turn = ai_turn;
    }
     public abstract void strategy();
     
     @Override
     public void run(){
         while (true){
            while (ai_turn!=state.turn){
                try{
                    Thread.sleep(50);
                }catch (Exception e){
                }
            }
            strategy();
         }
     }
}
