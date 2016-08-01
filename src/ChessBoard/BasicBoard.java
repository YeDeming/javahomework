/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;
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
import javafx.scene.control.Alert;
/**
 *
 * @author meepo
 */
public class BasicBoard extends Application {
     Scene gamescreen,homescreen;
     Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Drawing Operations Test");
        primaryStage.setTitle("Reversi");
     
        homescreen = new Scene(new Homepage(this),600,500,Color.WHITE);
        
        primaryStage.setScene(homescreen);
        primaryStage.getIcons().add(new Image(BasicBoard.class.getResource( "/Resource/icon.png").toExternalForm()));

        primaryStage.show();
    }
   public void gameover(int sum){
        System.out.println("gameover");
    
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
       }
}
    public void setgame(int kind,String ipString,int port,boolean xianshou){
        primaryStage.hide();
        
        ChessState state = new ChessState(primaryStage);
        state.setBasicBoard(this);
        FatherListener listener;
        if (kind==0){
            listener = new SelftwoMouseListener(state);
        } else if (kind >0){
             listener  = new AiListener(state,0,kind);
        } else if (kind==-1){
            listener = new TCPListener(state,kind,"127.0.0.1",port,xianshou);
        } else {
            listener = new TCPListener(state,kind,ipString,port,false);
        }
        
        ClickState clickmove = new ClickState();
        MyPanel canvas = new MyPanel(state,listener);
        canvas.setclick(clickmove);
        state.setPanel(canvas);
        Group root = new Group();
        
        root.getChildren().add(canvas);

        Button load = new Button("载入存档");
        load.setTranslateX(510);
        load.setTranslateY(100);        
        load.setMinSize(80, 30);
        load.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 //if state.kind!=0)
                 state.loadchess();
             }
        });
        
        Button save = new Button("保存游戏");
        save.setTranslateX(510);
        save.setTranslateY(150);        
        save.setMinSize(80, 30);
        save.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 state.savechess();

             }
        });
        
        Button back = new Button("悔棋");
        back.setTranslateX(510);
        back.setTranslateY(200);        
        back.setMinSize(80, 30);
        back.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                if (kind==0)
                 state.backtohistory(1-state.turn);
                else if (kind>0){
                 //state.backtohistory(1.turn);
                }else{
                    
                }
             }
        });
        
        Button backhome = new Button("返回主菜单");
        backhome.setTranslateX(510);
        backhome.setTranslateY(250);        
        backhome.setMinSize(80, 30);
        backhome.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 if (state.kind>0){
                     ((AiListener)listener).stop();
                 }
                 primaryStage.setScene(homescreen);
                 
             }
        });
        root.getChildren().add(load);
        root.getChildren().add(save);
        root.getChildren().add(back);
        root.getChildren().add(backhome);
        
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
