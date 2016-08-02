package ChessBoard;

import Listener.FatherListener;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;

import javax.swing.JOptionPane;
import javafx.scene.paint.Color;  
import javafx.scene.shape.*;  
import javafx.event.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import Listener.*;

public class MyPanel extends Canvas {
    final static int border = ConstRec.border;
    final static int maxn = ConstRec.maxn;;
    final static int gridsize = ConstRec.gridsize;;
    final static int maxsize = 2*border+maxn*gridsize;
    final static int control_redpointsize = 25; 
    final static int radius = ConstRec.gridsize/2-2;
    
    ChessState state;
    int flag[][];
    int mousex = -1,mousey = 0;
    private GraphicsContext gc;  
    FatherListener listener;
    ClickState clickmove;
    Scene gamescene;
    int finish = -2;
   BasicBoard basicBoard;
   public boolean dark = false;
    public MyPanel(ChessState state, FatherListener listener){
            super(maxsize+maxsize/5,maxsize);
            this.state = state;
            this.flag = state.flag;
            this.listener = listener;
            
            gc = getGraphicsContext2D();
            
            setOnMouseMoved(new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent me) {

                        if (dark) return;
                        mousex = (int) Math.round(me.getX());
                        mousey = (int) Math.round(me.getY());
                        if (mousex>=maxsize || state.start==false){
                            gamescene.setCursor(Cursor.DEFAULT);
                            repaint();
                        } else{
                            gamescene.setCursor(Cursor.DISAPPEAR);
                            repaint();
                        }
                    }
            });
            
            setOnMouseClicked(new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent me) {
                        if (finish!=-2 || state.start==false) return;

                        int xx = (int) Math.round(me.getX());
                        int yy = (int) Math.round(me.getY());
                        listener.next(xx,yy);
                        clickmove.play(xx, yy);
                    }
            });            
    }
    
    public void repaint(){
            draw(gc);  
    }

    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }
 
    
    public void draw(GraphicsContext gc){      
            //gc.clearRect(0, 0, getWidth(), getHeight());
            if (dark)
                gc.setFill(Color.DARKGREEN);
            else
                gc.setFill(Color.GREEN);	
            gc.fillRect(0, 0, getWidth(),getHeight());
            
            gc.setStroke(Color.BLACK);  
            
            gc.setLineWidth(2); 
            for (int i = 0; i < maxn+1; ++ i){
                    int x = border+gridsize*i;
                    gc.strokeLine(x,border , x, maxsize-border);
                    gc.strokeLine(border , x, maxsize-border,x);
            }

            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++ j){
                            int circle_x = border + i*gridsize+1;
                            int circle_y = border + j*gridsize+1;

                            if (flag[i][j]==0){
                                gc.setFill(Color.BLACK);                                      
                                gc.fillOval(circle_x,circle_y,gridsize-2,gridsize-2);
                            } else if (flag[i][j]==1){
                                    if (dark)
                                        gc.setFill(Color.LIGHTGRAY);
                                    else
                                    gc.setFill(Color.WHITE);		
                                    gc.fillOval(circle_x,circle_y,gridsize-2,gridsize-2);
                            }
                    }

            for (int i = 0; i < state.avaidcnt; ++ i){

                    int x = state.avaid[i]>>3;
                    int y = state.avaid[i] & 7;

                    int circle_x = border + x*gridsize+control_redpointsize;
                    int circle_y = border + y*gridsize+control_redpointsize;

                    gc.setFill(Color.RED);		
                    gc.fillOval(circle_x,circle_y,gridsize-control_redpointsize*2,gridsize-control_redpointsize*2);

            }
            if (dark){
                gc.setFill(Color.WHITE);
                gc.fillRect(maxsize/4+border, maxsize/3+border, maxsize/2, maxsize/4);
            } else
            if( state.start && mousex!=-1){

                    if (state.turn==0){
                            gc.setStroke(Color.BLACK);
                            gc.setFill(Color.BLACK);		

                    }
                    else {
                            gc.setStroke(Color.WHITE);
                            gc.setFill(Color.WHITE);		
                    }
                    if (mousex>=maxsize) return;
                    gc.setLineWidth(5);
                    gc.strokeOval(mousex-radius, mousey-radius, radius*2, radius*2);
   
                            
                    int a=radius/3*2;

                    gc.fillRoundRect(mousex-12, mousey-3, 24, 6, 12, 3);

                    gc.fillRoundRect(mousex-3, mousey-12, 6, 24, 3, 12);

                    
                    double aa = mousex - maxsize/2;
                    double bb = mousey - maxsize/2;
                    if (aa==0 && bb==0) return; 
                    double tmp = Math.sqrt(aa*aa+bb*bb)/22;
                    aa/=tmp;
                    bb/=tmp;
                    int mx = mousex+(int)Math.round(aa);
                    int my = mousey+(int)Math.round(bb);
                    gc.setFill(Color.PINK);		
                    gc.fillOval(mx-6,my-6, 12, 12);
                    
            }
    }

    public void gameover(int sum){
        this.finish = sum;

        if (sum==0){
            basicBoard.message("平局!");
        }else if (sum>0){
            basicBoard.message("黑棋胜");
        } else{
            basicBoard.message("白棋胜"); 
        }

        /* 
        try{
                if (sum==0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "平局");
                    alert.showAndWait();
                }
                else if (sum>0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "黑棋胜");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "白棋胜");
                    alert.showAndWait();
                  }
        } catch (Exception e1) {
                 //TODO: handle exception
       }*/
    }

    void setclick(ClickState clickmove) {
        this.clickmove = clickmove;
    }

    void setscreen(Scene gamescreen) {
        this.gamescene = gamescreen;
    }

}