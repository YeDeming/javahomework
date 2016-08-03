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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
/**
 *
 * @author meepo
 */
public class BasicBoard extends Application {
     Scene gamescreen,homescreen;
     public Stage primaryStage;

     TextField sendField;
     TextArea receiveField;
     static int maxsize = ConstRec.maxsize;
     static int border = ConstRec.border;
     FatherListener listener;

     Button yesButton,noButton,sureButton,load,save,back,backhome,sendbutton;
     Label messLabel,huiLabel = new Label();
     public MyPanel canvas;
     public int messagekind;
     VBox vb,liaotianBox;
     Homepage homepage;
     Clock clock;
    public static void main(String[] args) {
        launch(args);
    }
    public BasicBoard(){
        yesButton = new Button("Yes");
        noButton = new Button("No");
        sureButton = new Button("确定");      
        messLabel = new Label();

        yesButton.setVisible(false);
        noButton.setVisible(false);
        sureButton.setVisible(false);
        messLabel.setVisible(false);
        huiLabel.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }
    public void resetsize(){
        int maxsizex = ConstRec.maxsizex;

        int maxsizey = ConstRec.maxsizey;

        messLabel.setFont(new Font("Tahoma",30));
        huiLabel.setFont(new Font("Tahoma",15));
        sureButton.setLayoutX(maxsizex/2+border-maxsizex/8);
        sureButton.setLayoutY(maxsizey/3+border+maxsizey/4-maxsizey/15);
        sureButton.setMinSize(maxsizex/4, maxsizey/25);
        messLabel.setLayoutX(maxsizex/2+border-maxsizex/10);
        messLabel.setLayoutY(maxsizey/3+border);
        messLabel.setMinSize(maxsizex/5, maxsizey/20);
        huiLabel.setLayoutX(maxsizex/2+border-maxsizex/5);
        huiLabel.setLayoutY(maxsizey/3+border);
        huiLabel.setMinSize(maxsizex/2, maxsizey/20);
        yesButton.setLayoutX(maxsizex/2+border-maxsizex/6);
        yesButton.setLayoutY(maxsizey/3+border+maxsizey/4-maxsizey/15);
        yesButton.setMinSize(maxsizex/8, maxsizey/25);
        noButton.setLayoutX(maxsizex/2+border+maxsizex/6-maxsizex/8);
        noButton.setLayoutY(maxsizey/3+border+maxsizey/4-maxsizey/15);
        noButton.setMinSize(maxsizex/8, maxsizey/25);
        load.setMinSize(maxsizex/6.25,maxsizey/16.67);
        save.setMinSize(maxsizex/6.25,maxsizey/16.67);
        backhome.setMinSize(maxsizex/6.25,maxsizey/16.67);
        back.setMinSize(maxsizex/6.25,maxsizey/16.67);

        vb.setLayoutX(ConstRec.maxsizex+ConstRec.border);
        vb.setLayoutY(ConstRec.maxsizey/5);
        vb.setSpacing(maxsizey/100);
        
        liaotianBox.setLayoutX(maxsize);
        liaotianBox.setLayoutY(maxsize*3/5);
        sendField.setMaxWidth(maxsize/10*3);
        receiveField.setMaxWidth(maxsize/5*2-5);
        receiveField.setMaxHeight(maxsize*3/10);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Reversi");
     
        homepage = new Homepage(this);
        homescreen = new Scene(homepage,ConstRec.maxsizex*7/5,ConstRec.maxsizey,Color.WHITE);
        
        primaryStage.setScene(homescreen);
        primaryStage.getIcons().add(new Image(BasicBoard.class.getResource( "/Resource/icon.png").toExternalForm()));
 
        homescreen.widthProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                    ConstRec.maxsizex = newValue.intValue()*5/6;
                   homepage.resetsize();
            }
         });
        
         homescreen.heightProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    ConstRec.maxsizey = newValue.intValue();
                   homepage.resetsize();

            }
         });
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
       public void message_hui(String LabelString){
             Platform.runLater(new  Runnable() {
            @Override
            public void run() {
                gamescreen.setCursor(Cursor.DEFAULT);
                canvas.dark = true;
                huiLabel.setText(LabelString);
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
        clock = new Clock(Color.RED, Color.DARKGREEN);

        ChessState state = new ChessState(primaryStage,kind);
        state.setBasicBoard(this);
        if (kind==0){
            listener = new SelftwoMouseListener(state,clock);
        } else if (kind >0){
            if (xianshou)
                listener  = new AiListener(state,0,kind,this);
            else
                listener  = new AiListener(state,1,kind,this);
        } else if (kind==-1){
            listener = new TCPListener(state,kind,"127.0.0.1",port,xianshou,clock);
            ((TCPListener)listener).server.setBasicBoard(this);
        } else {
            listener = new TCPListener(state,kind,ipString,port,false,clock);
            ((TCPListener)listener).client.setBasicBoard(this);
        }

        ClickState clickmove = new ClickState();
        canvas = new MyPanel(state,listener);
        canvas.setclick(clickmove);
        canvas.setBasicBoard(this);
        state.setPanel(canvas);
        Group root = new Group();
        
        root.getChildren().add(canvas);
        vb = new VBox();
        load = new Button("载入存档");
        load.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 state.loadchess();
             }
        });
        
        save = new Button("保存游戏");
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

        back.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {

                 
                 if (kind==0){
                    state.backtohistory(1-state.turn);
                    clock.restart();
                }
                else if (kind>0){
                 state.backtohistory(listener.player_turn);
                }else{
                 ((TCPListener)listener).huiqi();                     
                }
             }
        });
        listener.setHuiButton(back);
        backhome = new Button("主菜单");
        backhome.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 if (state.start && state.finish==-2) {
                     messagekind = 0;
                     message_hui("是否要放弃当前游戏?");
                 }
                 else{
                    if (state.kind>0){
                         ((AiListener)listener).stop();
                    } else if (state.kind<0){
                         ((TCPListener)listener).stop();
                    }
                    clock.stop();
                    homepage.resetsize();
                    primaryStage.setScene(homescreen);
                 }
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
                            quxiao();
                            if (messagekind==1){
                             canvas.repaint();
                             ((TCPListener)listener).sendmessage("nohui");
                            }
                        }
        });
        
        yesButton.setOnAction(new EventHandler<ActionEvent>(){
            
             public void handle(ActionEvent me) {       
                 
                 System.out.println(messagekind);
                 if (messagekind==1){
                        quxiao();
                        state.backtohistory(listener.player_turn^1);         
                        ((TCPListener)listener).sendmessage("yeshui");
                 } else if (messagekind == 0){
                     clock.stop();
                        if (state.kind>0)
                           ((AiListener)listener).stop();
                       else if (state.kind<0){ 
                           ((TCPListener)listener).stop();
                       }
                       quxiao();
                       homepage.resetsize();
                       primaryStage.setScene(homescreen);
                 
                 }
             }
        });
        
        vb.getChildren().addAll(load,save,back,backhome);
        root.getChildren().addAll(vb,sureButton,yesButton,noButton,messLabel,huiLabel);
        gamescreen = new Scene(root);
        canvas.setscreen(gamescreen);
        
        clock.setLayoutX(maxsize+border);
        clock.setLayoutY(maxsize/8);
        clock.getTransforms().add(new Scale(0.25f, 0.25f, 0, 0));
        clock.setFatherListener(listener);
        if (kind>0) clock.setVisible(false);
        else clock.setVisible(true);
        
        if (kind!=0) {
            save.setVisible(false);
            load.setVisible(false);
        } else{
            save.setVisible(true);
            load.setVisible(true);
        }
        root.getChildren().add(clock);
        
               
        liaotianBox = new VBox(1);
        HBox sendBox = new HBox(1);
        receiveField = new TextArea();
        receiveField.setEditable(false);
        
        sendField = new TextField();
        sendbutton = new Button("send");
        sendBox.getChildren().addAll(sendField,sendbutton);
        liaotianBox.getChildren().addAll(receiveField,sendBox);
            
        root.getChildren().add(liaotianBox);
        root.getChildren().add(clickmove.circles);

        
        gamescreen.setCursor(Cursor.NONE);
        primaryStage.setScene(gamescreen);
        if (kind>0){
             ((AiListener)listener).ai.setPanel(canvas);
            ((AiListener)listener).start();
        }
        if (kind!=-1)
            state.restart();
        if (kind == 0) clock.restart();
        resetsize();
        
        
        /*
        gamescreen.heightProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                   ConstRec.maxsizey = newValue.intValue();
                   ConstRec.gridsize = (ConstRec.maxsizey-2*ConstRec.border)/ConstRec.maxn;
                   clickmove.resetsize();
                   resetsize();
            }
         });*/

        primaryStage.show();

    }

    public void setgame(int kind) {
        setgame(kind,"",0,false);
    }
    public void setgame(int kind,boolean xianshou) {
        setgame(kind,"",0,xianshou);
    }
    public void protect() {
        
         load.setDisable(true);
        save.setDisable(true);
        back.setDisable(true);
        
    }
    public void deprotect() {
        
        load.setDisable(false);
        save.setDisable(false);
        back.setDisable(false);
        
    }

    

    
}
