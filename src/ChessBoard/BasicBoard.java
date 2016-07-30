/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;
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
        //primaryStage.getIcons().add(new Image("/home/meepo/NetBeansProjects/BasicBoard/resource/icon.png"));
                //BasicBoard.class.getResourceAsStream("/resource/icon.png")));   

        homescreen = new Scene(new Homepage(this),600,500);
        
        primaryStage.setScene(homescreen);
        

        primaryStage.show();

    }
    
    public void setgame(int kind){
        primaryStage.hide();
        
        ChessState state = new ChessState(primaryStage);
        FatherListener listener;
        if (kind==0){
            listener = new SelftwoMouseListener(state);
        } else{
             listener  = new AiListener(state,0,kind);
        }
        
        ClickState clickmove = new ClickState();
        MyPanel canvas = new MyPanel(state,listener);
        canvas.setclick(clickmove);
        state.setPanel(canvas);
        Group root = new Group();//clickmove.circles,canvas);
       // root.getChildren().add(new Group(canvas,clickmove.circles));
        //StackPane root = new StackPane();
        root.getChildren().add(canvas);

        //HBox all = new HBox();  
        //all.getChildren().add(canvas);
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
                state.backtohistory(1-state.turn);
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
    

    
}
