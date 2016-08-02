package Ai;
import ChessBoard.BasicBoard;
import ChessBoard.ChessState;
import ChessBoard.MyPanel;
import javafx.concurrent.Task;

public abstract class FatherAi extends Task<Integer>{
    ChessState state;
    MyPanel panel;
    BasicBoard basicBoard;
    int ai_turn;
    public FatherAi(ChessState state, int ai_turn,BasicBoard basicBoard) {
            // TODO Auto-generated constructor stub
            this.state = state;
            this.ai_turn = ai_turn;
            this.basicBoard = basicBoard;
    }
     public abstract void strategy();
     
     
     
    public void setPanel(MyPanel panel) {
        this.panel = panel;
    }

    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }
     
    @Override 
     protected Integer call() throws Exception {
         while (state.finish == -2){
            while (ai_turn!=state.turn){
                try{
                    Thread.sleep(50);
                }catch (Exception e){
                }
            }
                     basicBoard.protect();
                     
            strategy();
                     basicBoard.deprotect();

         }
         System.out.println("Ai finish");

         return state.finish;
     }
}
