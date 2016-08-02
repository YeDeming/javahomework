package Listener;

import ChessBoard.ChessState;
import ChessBoard.Clock;
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
    public TCPListener(ChessState state,int mykind,String ipString,int port,boolean xianshou){
            super(state,0);
            if (xianshou)
                this.player_turn = 0;
            else
                this.player_turn = 1;
            
            this.mykind = mykind;
            try{
                if (mykind == -1){
                    server = new TCPServer(state,port,xianshou);
                    th = new Thread(server);
                    th.setDaemon(true);
                } else {
                    client = new TCPClient(state,ipString,port,this);    
                    th = new Thread(client);
                    th.setDaemon(true);
                }
            } catch (Exception e){
                System.err.println("TCP err");
            }
            th.start();
    }
    public void stop(){
        th.stop();
    }
    
    public void next(int rawx,int rawy) {
            // TODO Auto-generated method stub
            if (player_turn!=state.turn)
                return;
            int x = (rawx-border);
            int y = (rawy-border);
            if (rawx>=maxsize || rawy>=maxsize) return;

            if (x%gridsize==0 || y%gridsize==0) return;
            x /= gridsize;
            y /= gridsize;

            if (state.flag[x][y]!=2) return;
            state.set(x,y);
            if (mykind==-1){
                server.sendmessage(x, y);
            } else{
                client.sendmessage(x, y);
            }
           huiButton.setDisable(false);
           clock.setcurrent();

    }

    public void huiqi() {

            if (mykind==-1){
                server.sendmessage("hui");
            } else{
                client.sendmessage("hui");
            }
    }

    public void sendmessage(String string) {
            if (mykind==-1){
                server.sendmessage(string);
            } else{
                client.sendmessage(string);
            }
    }

    public void setclock(Clock clock) {
        this.clock = clock;
             if (mykind==-1){
                server.setClock(clock);
            } else{
                client.setClock(clock);
            }
    }

 
}
