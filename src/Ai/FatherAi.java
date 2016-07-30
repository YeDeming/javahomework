package Ai;
import ChessBoard.ChessState;
import ChessBoard.MyPanel;
import javafx.concurrent.Task;

public abstract class FatherAi extends Task<Integer>{
    ChessState state;
    MyPanel panel;
    int ai_turn;
    public FatherAi(ChessState state, int ai_turn) {
            // TODO Auto-generated constructor stub
            this.state = state;
            this.ai_turn = ai_turn;
    }
     public abstract void strategy();

    public void setPanel(MyPanel panel) {
        this.panel = panel;
    }
     
     //@Override
     //public void run(){
    @Override 
     protected Integer call() throws Exception {
         while (state.finish == -2){
            while (ai_turn!=state.turn){
                try{
                    Thread.sleep(50);
                }catch (Exception e){
                }
            }
            strategy();
         }
         System.out.println("Ai finish");
         return state.finish;
         //panel.gameover(state.finish);
     }
}
