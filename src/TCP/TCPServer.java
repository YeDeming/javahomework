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
public class TCPServer extends Task<Integer>{
    DataOutputStream dosOutToClient;
    ChessState state;
    int port,myturn;

    String strSocket,strLocal;
    BufferedReader brInFromeClinet;
    boolean xianshou;
    TCPListener listener;
    BasicBoard basicBoard;
    Clock clock;
    public TCPUnit unit;
    Socket socketServer ;
    ServerSocket ssocket ;
    public TCPServer(ChessState state,int port,boolean xianshou,TCPListener listener,Clock clock) throws Exception{
        this.state = state;
        this.port =port;
        this.clock = clock;
        this.listener = listener;
        if (xianshou) myturn = 0;
        else myturn = 1;
    }        

    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }

    @Override 
    protected Integer call() throws Exception {
        System.out.println(port);
        System.out.println("Server Start");
        ssocket = new ServerSocket(port);
        socketServer = ssocket.accept();
        brInFromeClinet = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
        dosOutToClient = new DataOutputStream(socketServer.getOutputStream());
        System.out.println("connected!");
        //state.restart();
        try{
        dosOutToClient.writeBytes(myturn + "\n");
        dosOutToClient.writeBytes(ConstRec.limitsecond + "\n");
        } catch (Exception e){
                                     System.out.println("连接已断开");

            if (listener!=null)
                listener.webinterrupt();
        }
        unit = new TCPUnit(brInFromeClinet,dosOutToClient,state,myturn,basicBoard,clock,listener);
        unit.work();
        
        socketServer.close();
        return 0;
    }
    
    public void stop() throws IOException{
        if (ssocket!=null)
            ssocket.close();
        if (socketServer!=null)
                socketServer.close();
    }
} 
