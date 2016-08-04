package Listener;

import ChessBoard.BasicBoard;
import ChessBoard.ChessState;
import ChessBoard.Clock;
import ChessBoard.ConstRec;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import TCP.*;
import javax.print.attribute.standard.Severity;

public class TCPListener  extends FatherListener{

    public TCPServer server;
    public TCPClient client;
    int mykind;
    public Thread th;
    Clock clock;
   boolean webisalive = true;
    BasicBoard  basicBoard;
    public TCPListener(ChessState state,int mykind,String ipString,int port,boolean xianshou,Clock clock){
            super(state,0);
            this.clock = clock;
            if (xianshou)
                this.player_turn = 0;
            else
                this.player_turn = 1;
            
            this.mykind = mykind;
            try{
                if (mykind == -1){
                    server = new TCPServer(state,port,xianshou,this,clock);
                    th = new Thread(server);
                    th.setDaemon(true);
                } else {
                    client = new TCPClient(state,ipString,port,this,clock);    
                    th = new Thread(client);
                    th.setDaemon(true);
                }
            } catch (Exception e){
                System.err.println("TCP err");
            }
            th.start();
    }
    public void stop(){
        try{
        if (mykind==-1){
            
                server.stop();
            } else{
                client.stop();
           }
        }catch(Exception e){}
        if (th!=null)
        th.stop();
           
    }

    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }
    
    public void next(int rawx,int rawy) {
            // TODO Auto-generated method stub
            int border = ConstRec.border;
            int gridsize = ConstRec.gridsize;
            int maxsize = ConstRec.maxsize;
            if (player_turn!=state.turn)
                return;
            int x = (rawx-border);
            int y = (rawy-border);
            if (rawx>=maxsize+border || rawy>=maxsize+border) return;

            if (x%gridsize==0 || y%gridsize==0) return;
            x /= gridsize;
            y /= gridsize;
        if (x<0 || y<0 || x>=maxn || y>=maxn) return;

            if (state.flag[x][y]!=2) return;
            state.set(x,y);
            if (mykind==-1){
                server.unit.sendmessage(x, y);
            } else{
                client.unit.sendmessage(x, y);
            }
           huiButton.setDisable(false);
           clock.stop();
           clock.setcurrent();

    }

    public void huiqi() {

            if (mykind==-1){
                server.unit.sendmessage("hui");
            } else{
                client.unit.sendmessage("hui");
            }
    }

    public void sendmessage(String string) {
            if (mykind==-1){
                server.unit.sendmessage(string);
                if (string.equals("yeshui"))
                    server.unit.checkclock();
            
            } else{
                client.unit.sendmessage(string);
                if (string.equals("yeshui"))
                    client.unit.checkclock();
            }
    }

    public void timeout(){
        sendmessage("timeout");
        state.pass();
          if (mykind==-1){  
                    server.unit.checkclock();   
            } else{
                    client.unit.checkclock();
            }
    }
    
    public void webinterrupt(){
        System.out.println("web");
        if (webisalive){

            
            //stop();

            
            webisalive = false;

            
            basicBoard.messagekind = 0;

            
            basicBoard.message("连接已断开");
            
        }
    }
    
 
}
