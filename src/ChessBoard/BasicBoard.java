/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;
import static ChessBoard.MyPanel.maxsize;
import Listener.AiListener;
import Listener.FatherListener;
import Listener.SelftwoMouseListener;
import Homepage.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Listener.*;

import java.awt.Panel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
/**
 *
 * @author meepo
 */
public class BasicBoard extends Application {
     Scene gamescreen,homescreen;
     Stage primaryStage;
     static int maxsize = ConstRec.maxsize;
     static int border = ConstRec.border;
     FatherListener listener;

     Button yesButton,noButton,sureButton,load,save,back,backhome;
     Label messLabel,huiLabel = new Label("对方请求悔棋，是否同意？");
     public MyPanel canvas;
    public static void main(String[] args) {
        launch(args);
    }
    public BasicBoard(){
        yesButton = new Button("Yes");
        noButton = new Button("No");
        sureButton = new Button("确定");      
        messLabel = new Label();
        messLabel.setFont(new Font("Tahoma",30));

        yesButton.setVisible(false);
        noButton.setVisible(false);
        sureButton.setVisible(false);
        sureButton.setLayoutX(maxsize/2+border-maxsize/8);
        sureButton.setLayoutY(maxsize/3+border+maxsize/4-maxsize/15);
        sureButton.setMinSize(maxsize/4, maxsize/25);
        
        messLabel.setLayoutX(maxsize/2+border-maxsize/10);
        messLabel.setLayoutY(maxsize/3+border);
        messLabel.setMinSize(maxsize/5, maxsize/20);
        messLabel.setVisible(false);
        huiLabel.setFont(new Font("Tahoma",15));
        huiLabel.setLayoutX(maxsize/2+border-maxsize/5);
        huiLabel.setLayoutY(maxsize/3+border);
        huiLabel.setMinSize(maxsize/2, maxsize/20);
        
        huiLabel.setVisible(false);
        yesButton.setVisible(false);
        yesButton.setLayoutX(maxsize/2+border-maxsize/6);
        yesButton.setLayoutY(maxsize/3+border+maxsize/4-maxsize/15);
        yesButton.setMinSize(maxsize/8, maxsize/25);
        noButton.setVisible(false);
        noButton.setLayoutX(maxsize/2+border+maxsize/6-maxsize/8);
        noButton.setLayoutY(maxsize/3+border+maxsize/4-maxsize/15);
        noButton.setMinSize(maxsize/8, maxsize/25);

    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Reversi");
     
        homescreen = new Scene(new Homepage(this),600,500,Color.WHITE);
        
        primaryStage.setScene(homescreen);
        primaryStage.getIcons().add(new Image(BasicBoard.class.getResource( "/Resource/icon.png").toExternalForm()));

        primaryStage.show();
    }
    
   public void message(String message){
       Platform.runLater(new  Runnable() {
            @Override
            public void run() {
                gamescreen.setCursor(Cursor.DEFAULT);
                canvas.dark = true;

                messLabel.setVisible(true);
                messLabel.setText(message);
                sureButton.setVisible(true);

                load.setDisable(true);
                save.setDisable(true);
                back.setDisable(true);
                backhome.setDisable(true);
                canvas.repaint();                        
            }
        });
    }   
       public void message_hui(){
             Platform.runLater(new  Runnable() {
            @Override
            public void run() {
                gamescreen.setCursor(Cursor.DEFAULT);
                canvas.dark = true;
                huiLabel.setVisible(true);
                yesButton.setVisible(true);
                noButton.setVisible(true);
                
                load.setDisable(true);
                save.setDisable(true);
                back.setDisable(true);
                backhome.setDisable(true);
                canvas.repaint();
            }
        });
       }
       
    public void quxiao() {
        messLabel.setVisible(false);
        sureButton.setVisible(false);
        huiLabel.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        load.setDisable(false);
        save.setDisable(false);
        back.setDisable(false);
        backhome.setDisable(false);
        canvas.dark = false;
    }
    
   
    public void setgame(int kind,String ipString,int port,boolean xianshou){
        primaryStage.hide();
        
        ChessState state = new ChessState(primaryStage,kind);
        state.setBasicBoard(this);
        if (kind==0){
            listener = new SelftwoMouseListener(state);
        } else if (kind >0){
             listener  = new AiListener(state,0,kind);
        } else if (kind==-1){
            listener = new TCPListener(state,kind,"127.0.0.1",port,xianshou);
            ((TCPListener)listener).server.setBasicBoard(this);
        } else {
            listener = new TCPListener(state,kind,ipString,port,false);
            ((TCPListener)listener).client.setBasicBoard(this);
        }

        ClickState clickmove = new ClickState();
        canvas = new MyPanel(state,listener);
        canvas.setclick(clickmove);
        canvas.setBasicBoard(this);
        state.setPanel(canvas);
        Group root = new Group();
        
        root.getChildren().add(canvas);
        VBox vb = new VBox();
        vb.setLayoutX(ConstRec.maxsize+ConstRec.border);
        vb.setLayoutY(100);
        load = new Button("载入存档");
        load.setMinSize(80, 30);
        load.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 state.loadchess();
             }
        });
        
        save = new Button("保存游戏");
        save.setMinSize(80, 30);
        save.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 state.savechess();
             }
        });
        
        if (kind<0){
            load.setDisable(true);
            save.setDisable(true);
        }
        back = new Button("悔棋");
                back.setDisable(true);

        back.setMinSize(80, 30);
        back.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                if (kind==0)
                 state.backtohistory(1-state.turn);
                else if (kind>0){
                 state.backtohistory(listener.player_turn);
                }else{
                 ((TCPListener)listener).huiqi();                     
                }
             }
        });
        listener.setHuiButton(back);
        backhome = new Button("主菜单");
        backhome.setMinSize(80, 30);
        backhome.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 if (state.kind>0){
                     ((AiListener)listener).stop();
                 } else if (state.kind<0){
                     ((TCPListener)listener).stop();
                 }
                 primaryStage.setScene(homescreen);
                 
             }
        });
        
        sureButton.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 if (state.kind>0)
                     ((AiListener)listener).stop();
                 else if (state.kind<0){ 
                     ((TCPListener)listener).stop();
                 }
                 quxiao();
                 primaryStage.setScene(homescreen);
                 
             }
        });
        
        
        noButton.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {                 
                    //Platform.runLater(new  Runnable() {
                      //   @Override
                         //public void run() {
                                quxiao();
                                canvas.repaint();
                                ((TCPListener)listener).sendmessage("nohui");
                        }
                    //});
            // }
        });
        
        yesButton.setOnAction(new EventHandler<ActionEvent>(){
            
             public void handle(ActionEvent me) {
                 
                    /*Platform.runLater(new  Runnable() {
                         @Override
                         public void run() {*/
                        quxiao();
                        state.backtohistory(listener.player_turn^1);         
                        ((TCPListener)listener).sendmessage("yeshui");
                  //}
                   // });
             }
        });
        
        vb.setSpacing(10);
        vb.getChildren().addAll(load,save,back,backhome);
        root.getChildren().addAll(vb,sureButton,yesButton,noButton,messLabel,huiLabel);
        gamescreen = new Scene(root);
        canvas.setscreen(gamescreen);
        root.getChildren().add(clickmove.circles);

        gamescreen.setCursor(Cursor.NONE);
        primaryStage.setScene(gamescreen);
        if (kind>0){
             ((AiListener)listener).ai.setPanel(canvas);
            ((AiListener)listener).start();
        }
        state.restart();
        primaryStage.show();


    }

    public void setgame(int kind) {
        setgame(kind,"",0,false);
    }

    

    
}
