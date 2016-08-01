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
    public TCPServer(ChessState state,int port,boolean xianshou) throws Exception{
        this.state = state;
        this.port =port;
        
        if (xianshou) myturn = 0;
        else myturn = 1;
    }        

    @Override 
    protected Integer call() throws Exception {
        System.out.println(port);
        System.out.println("Server Start");
        ServerSocket ssocket = new ServerSocket(port);
        Socket socketServer = ssocket.accept();
        brInFromeClinet = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
        dosOutToClient = new DataOutputStream(socketServer.getOutputStream());
        System.out.println("connected!");

        dosOutToClient.writeBytes(myturn + "\n");

        while (state.finish==-2){
                strSocket = brInFromeClinet.readLine();
                if (state.finish!=-2) break;
                if (strSocket==null) continue;
                String[] args = strSocket.split(" ");
                state.set(Integer.valueOf(args[0]),Integer.valueOf(args[1]));				
        }
        return 0;
    }
    
    public void sendmessage(int x,int y) {
            String string = x + " " + y + "\n";
            try{
                    dosOutToClient.writeBytes(string);
            }catch (Exception e){

            }
    }
} 
