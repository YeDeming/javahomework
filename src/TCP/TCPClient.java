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
    int port;
    TCPListener listener;
    public TCPClient(ChessState state,String ip,int port,TCPListener listener) throws Exception{
        this.state = state;
        this.ip = ip;
        this.port = port;
        this.listener = listener;
    }        

    @Override 
    protected Integer call() throws Exception {
        System.out.println("Client Start");
        System.out.println(ip);
        System.out.println(port);
        socketClient = new Socket(ip, port);

        brInFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        dosOutToServer  = new DataOutputStream(socketClient.getOutputStream());
   
        strSocket = brInFromServer.readLine();
        myturn = 1-Integer.valueOf(strSocket);
        System.out.println("myturn " + myturn);
        listener.player_turn = myturn;
        while (state.finish==-2){
                strSocket = brInFromServer.readLine();
                if (state.finish!=-2) break;

                if (strSocket==null) continue;
                System.out.println("Client receive:" + strSocket);
                String[] args = strSocket.split(" ");

                state.set(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
        }

        socketClient.close();

        return 0;
    }
    
    public void sendmessage(int x,int y) {
        String string = x + " " + y + "\n";

        try{
                dosOutToServer.writeBytes(string);
        }catch (Exception e){

        }
    }
} 
