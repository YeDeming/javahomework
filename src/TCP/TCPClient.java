/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;
import ChessBoard.*;
import Listener.TCPListener;
import javafx.concurrent.Task;
import java.net.*;
import java.io.*;
/**
 *
 * @author meepo
 */
public class TCPClient extends Task<Integer>{
    DataOutputStream dosOutToServer;
    ChessState state;
    int myturn;

    String strSocket,strLocal;
    BufferedReader brInFromServer;
    Socket socketClient;
    String ip;
    int port,time;
    TCPListener listener;
    BasicBoard basicBoard;
    Clock clock;
    public TCPUnit unit;

    public TCPClient(ChessState state,String ip,int port,TCPListener listener,Clock clock) throws Exception{
        this.state = state;
        this.ip = ip;
        this.port = port;
        this.listener = listener;
        this.clock = clock;
    }        

    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }

    @Override 
    protected Integer call() throws  Exception{
        System.out.println("Client Start");
        System.out.println(ip);
        System.out.println(port);
        socketClient = new Socket(ip, port);

        brInFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        dosOutToServer  = new DataOutputStream(socketClient.getOutputStream());
        try{
        strSocket = brInFromServer.readLine();

        
        myturn = 1-Integer.valueOf(strSocket);
        System.out.println("myturn " + myturn);
        listener.player_turn = myturn;
        
        strSocket = brInFromServer.readLine();
        time = Integer.valueOf(strSocket);
        }catch (Exception e){
                                     System.out.println("连接已断开");

            if (listener!=null)
                listener.webinterrupt();
        }
        basicBoard.protect();
        basicBoard.surekind = time;
        basicBoard.message("将思考时间设置为"+strSocket+"s");
        basicBoard.deprotect();
        
        unit = new TCPUnit(brInFromServer, dosOutToServer, state,myturn,basicBoard,clock,listener);
        unit.work();
        socketClient.close();

        return 0;
    }
        public void stop() throws IOException{
        if (socketClient!=null)
            socketClient.close();
        }

} 
