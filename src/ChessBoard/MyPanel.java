package ChessBoard;

import static ChessBoard.BasicBoard.border;
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
import javafx.application.Platform;

public class MyPanel extends Canvas {
    final static int border = ConstRec.border;
    final static int maxn = ConstRec.maxn;
    
    static int gridsize = ConstRec.gridsize;
     int maxsizex,maxsizey,maxsize;
    int control_redpointsize = 25; 
    int radius = ConstRec.gridsize/2-2;
    
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
    public MyPanel(ChessState state, FatherListener listener,int maxsizex,int maxsizey){
            super(maxsizex*7/5,maxsizey);
            this.maxsizex = maxsizex;
            this.maxsizey = maxsizey;
            //System.out.println(maxsizex + " "+ maxsizey);

            this.setHeight(maxsizey);
            this.setWidth(maxsizex+maxsizex*2/5);
            //System.out.println("wid:" + getWidth());
            gridsize = ConstRec.gridsize;
              this.maxsize = ConstRec.gridsize*maxn+2*border;

            radius = ConstRec.gridsize/2-gridsize/25;
            control_redpointsize = 25*gridsize/ConstRec.stdgridsize;

            
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
                            setCursor(Cursor.DEFAULT);
                            repaint();
                        } else{
                            
                            setCursor(Cursor.NONE);
                            repaint();
                        }
                    }
            });
            
            setOnMouseClicked(new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent me) {
                        if (finish!=-2 || state.start==false) return;
                        if (dark) return;

                        int xx = (int) Math.round(me.getX());
                        int yy = (int) Math.round(me.getY());
                        listener.next(xx,yy);
                        clickmove.play(xx, yy);
                    }
            });            
    }
    
    public void repaint(){
        
             Platform.runLater(new  Runnable() {
            @Override
            public void run() {
            draw(gc);  
            }});
    }

    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }
 
    
    public void draw(GraphicsContext gc){      

        if (dark){
                gc.setFill(Color.DARKGREEN);
                basicBoard.gamescreen.fillProperty().set(Color.DARKGREEN);
        }else
        {       gc.setFill(Color.GREEN);	
                        basicBoard.gamescreen.fillProperty().set(Color.GREEN);

        }   
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
                gc.fillRect(maxsizex/2-maxsizex/4, maxsize/3+border,  maxsizex/2+3*border, maxsize/4);
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

                    gc.fillRoundRect(mousex-gridsize/5, mousey-gridsize/20, gridsize*2/5, gridsize/10, gridsize/5, gridsize/20);

                    gc.fillRoundRect(mousex-gridsize/20, mousey-gridsize/5, gridsize/10, gridsize*2/5, gridsize/20, gridsize/5);

                    
                    double aa = mousex - maxsize/2;
                    double bb = mousey - maxsize/2;
                    if (aa==0 && bb==0) return; 
                    double tmp = Math.sqrt(aa*aa+bb*bb)/(gridsize/2.6);
                    aa/=tmp;
                    bb/=tmp;
                    int mx = mousex+(int)Math.round(aa);
                    int my = mousey+(int)Math.round(bb);
                    gc.setFill(Color.PINK);		
                    gc.fillOval(mx-gridsize/10,my-gridsize/10, gridsize/5, gridsize/5);
                    
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

        
    }

    void setclick(ClickState clickmove) {
        this.clickmove = clickmove;
    }

    void setscreen(Scene gamescreen) {
        this.gamescene = gamescreen;
    }

    void resetsize() {
        maxsizex = ConstRec.maxsizex;
        maxsizey = ConstRec.maxsizey;
        gridsize = ConstRec.gridsize;
        radius = ConstRec.gridsize/2-gridsize/25;
        control_redpointsize = 25*gridsize/ConstRec.stdgridsize; 
       this.maxsize = ConstRec.maxsize;

        this.setHeight(maxsizey);
        this.setWidth(maxsizex+maxsizex*2/5);
         //           System.out.println("wid:" + getWidth());

        repaint();
    }
    
}