/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import ChessBoard.BasicBoard;
import ChessBoard.ChessState;
import ChessBoard.Clock;
import Listener.TCPListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import javafx.application.Platform;

/**
 *
 * @author meepo
 */
public class TCPUnit {
    DataOutputStream dosOutToother;
    ChessState state;
    int myturn;
    String strSocket;
    BufferedReader brInFromother;
    TCPListener listener;
    BasicBoard basicBoard;
    Clock clock;
    
    TCPUnit(BufferedReader brInFromother,DataOutputStream dosOutToother, ChessState state,int myturn,BasicBoard basicBoard,Clock clock){
        this.brInFromother = brInFromother;
        this.dosOutToother = dosOutToother;
        this.state = state;
        this.myturn = myturn;
        this.basicBoard = basicBoard;
        this.clock = clock;
        if(myturn==0) 
            clock.play();
    }

    public  void checkclock(){
        if (state.turn==myturn){
         System.out.println("setting clock");

            Platform.runLater(new  Runnable() {
            @Override
            public void run() {
     
                clock.restart();
            }
            });
         }      
    }
    
    public void work() throws IOException{
        checkclock();
        while (state.finish==-2){
                strSocket = brInFromother.readLine();
                System.out.println("Receive: " + strSocket);

                if (state.finish!=-2) break;
                if (strSocket==null) continue;
                String[] args = strSocket.split(" ");
                int lastturn = state.turn;
                if (args[0].equals("set")){
                    state.set(Integer.valueOf(args[1]),Integer.valueOf(args[2]));
                    checkclock();
                }
                else if (args[0].equals("hui")){
                    basicBoard.message_hui("对方请求悔棋，是否同意？");
                    basicBoard.messagekind = 1;
                } else if (args[0].equals("nohui")){
                    basicBoard.quxiao();
                } else if (args[0].equals("yeshui")){
                    basicBoard.quxiao();
                    state.backtohistory(myturn);
                    checkclock();
                } else if (args[0].equals("timeout")){
                    state.pass();
                    checkclock();
                }
                if (state.finish!=-2) break;
           
        }
    }
        public void sendmessage(int x,int y) {
            String string = "set " + x + " " + y + "\n";
            try{
                    dosOutToother.writeBytes(string);
            }catch (Exception e){
            }
    }

    public void sendmessage(String str) {
        String string = str+ "\n";
            try{
                    dosOutToother.writeBytes(string);
            }catch (Exception e){
            }
    }
}
