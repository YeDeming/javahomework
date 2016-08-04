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


import java.awt.Panel;
import java.io.File;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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
     static int maxsize = ConstRec.stdmaxsize;
     static int border = ConstRec.border;
     FatherListener listener;

     Button yesButton,noButton,sureButton,load,save,back,backhome,sendbutton;
     Label messLabel,huiLabel = new Label();
     public MyPanel canvas;
     public int messagekind;
     VBox vb;//,liaotianBox;
     Homepage homepage;
     Clock clock;
     boolean bt1,bt2,bt3,loadclick,bt4;
     boolean firstset = true;
     public int surekind;
     public Music music ;
     ChoiceBox<String> cb;
     String[] list;
     String storepath = ConstRec.storepath;
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
        music = new Music("bgm.mp3");

    }
    public void addreceiveField(String string){
        receiveField.setText(receiveField.getText()+string+'\n');
    }
    public void resetsize(){
        int maxsizex = ConstRec.maxsizex;

        int maxsizey = ConstRec.maxsizey;

        messLabel.setFont(new Font("Tahoma",15));
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
        noButton.setLayoutX(maxsizex/2+border+ maxsizex/8);
        noButton.setLayoutY(maxsizey/3+border+maxsizey/4-maxsizey/15);
        noButton.setMinSize(maxsizex/8, maxsizey/25);
        load.setMinSize(maxsizex/6.25,maxsizey/16.67);
        save.setMinSize(maxsizex/6.25,maxsizey/16.67);
        backhome.setMinSize(maxsizex/6.25,maxsizey/16.67);
        back.setMinSize(maxsizex/6.25,maxsizey/16.67);

        vb.setLayoutX(ConstRec.maxsizex+ConstRec.border*2);
        vb.setLayoutY(ConstRec.maxsizey/5);
        vb.setSpacing(maxsizey/100);
        
        //liaotianBox.setLayoutX(maxsize);
        //liaotianBox.setLayoutY(maxsize*3/5)*
        sendField.setMaxWidth(maxsizex*7/5-(maxsizex+ConstRec.border)-border*5);
        receiveField.setMaxWidth(maxsizex*7/5-(maxsizex+ConstRec.border*3));
        receiveField.setMaxHeight(maxsizex*4/10);
        clock.setLayoutX(maxsizex+ConstRec.border*2);
        clock.setLayoutY(maxsizey/8);
        //cb.setLayoutX(load.getLayoutX());
        
        //cb.setLayoutY(load.getLayoutY());
    }
    
    @Override
    public void start(Stage primaryStage) { 
        music.start();
        //Thread mu = new Thread(music);
        //mu.setDaemon(true);
        //mu.run();
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Reversi");
        homepage = new Homepage(this);
        StackPane home = new StackPane(homepage);
        
        home.setPrefHeight(ConstRec.maxsizey);
        home.setPrefWidth(ConstRec.maxsizex*7/5);
        homescreen = new Scene(home,Color.WHITE);
        primaryStage.getIcons().add(new Image(BasicBoard.class.getResource( File.separator+"Resource"+File.separator+"icon.png").toExternalForm()));

        primaryStage.setScene(homescreen);

        
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (newValue.intValue()==0) return;
                   ConstRec.maxsizex = newValue.intValue()*5/7;
                   ConstRec.update();
                   homepage.resetsize();
                   home.setPrefHeight(ConstRec.maxsizey);
                    home.setPrefWidth(ConstRec.maxsizex*7/5);

            }
         });
        
         primaryStage.heightProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                   if (newValue.intValue()==0) return;
                   ConstRec.maxsizey = (int)(newValue.intValue()-28);
                   ConstRec.update();
                   homepage.resetsize();
        home.setPrefHeight(ConstRec.maxsizey);
        home.setPrefWidth(ConstRec.maxsizex*7/5);
            }
         });
        primaryStage.show();
    }
    
   public void message(String message){
       Platform.runLater(new  Runnable() {
            @Override
            public void run() {
                canvas.setCursor(Cursor.DEFAULT);
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
                canvas.setCursor(Cursor.DEFAULT);
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
         canvas.setCursor(Cursor.NONE);

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
        //primaryStage.hide();

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
            ((TCPListener)listener).setBasicBoard(this);
            ((TCPListener)listener).server.setBasicBoard(this);
        } else {
            listener = new TCPListener(state,kind,ipString,port,false,clock);
             ((TCPListener)listener).setBasicBoard(this);
            ((TCPListener)listener).client.setBasicBoard(this);
        }

        ClickState clickmove = new ClickState();
        int a1 = (int)(primaryStage.getWidth()/7*5);
        int a2 = (int) (primaryStage.getHeight()-28);
        ConstRec.maxsizex = a1;
        ConstRec.maxsizey = a2;
        ConstRec.update();
        canvas = new MyPanel(state,listener,a1,a2);

        
        canvas.setclick(clickmove);
        canvas.setBasicBoard(this);
        state.setPanel(canvas);
        Group root = new Group();
        

        
        root.getChildren().add(canvas);
        vb = new VBox();
        load = new Button("载入存档");
        loadclick = false;
        load.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 updatecb();
                 if (list.length==0) return;//load.setDisable(true);
                 if (!loadclick){
                        state.start = false;
                        cb.setVisible(true);
                        load.setText("确定");
                 } else{
                     state.start = true;
                     clock.restart();
                     cb.setVisible(false);
                     load.setText("载入存档");
                 }
                 loadclick^=true;
                     //state.loadchess();
             }
        });
                
        cb = new ChoiceBox<String>();  
        cb.setVisible(false);
       updatecb();
         cb.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{  
              
             int w = (newv.intValue());
             if (w<0) return;
             state.loadchess(storepath+list[w]);
         });  
          
        save = new Button("保存游戏");
        save.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 state.savechess();
                 updatecb();
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
                    primaryStage.setScene(homescreen);
                    if (state.kind>0){
                         ((AiListener)listener).stop();
                    } else if (state.kind<0){
                         ((TCPListener)listener).stop();
                    }
                    clock.stop();
                       //homepage.resetsize(ConstRec.maxsizex,ConstRec.maxsizey);
                 }
             }
        });
        
        sureButton.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                 if (surekind==0){
                 if (state.kind>0)
                     ((AiListener)listener).stop();
                 else if (state.kind<0){ 
                     ((TCPListener)listener).stop();
                 }
                 quxiao();
                 primaryStage.setScene(homescreen);
                 } else {
                     ConstRec.limitsecond = surekind;
                     homepage.limitseconds.setText(String.valueOf(surekind));
                     ((TCPListener)listener).sendmessage("restart");
                     state.restart();
                     quxiao();

                 }
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
                 

                 if (messagekind==1){
                        quxiao();
                        state.backtohistory(listener.player_turn^1);         
                        ((TCPListener)listener).sendmessage("yeshui");
                 } else if (messagekind == 0){
                      primaryStage.setScene(homescreen);
                     clock.stop();
                        if (state.kind>0)
                           ((AiListener)listener).stop();
                       else if (state.kind<0){ 
                           ((TCPListener)listener).stop();
                       }
                       quxiao();
                       
                 }
             }
        });
        
        vb.getChildren().addAll(load,cb,save,back,backhome);

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
        
               
        //liaotianBox = new VBox(1);
        HBox sendBox = new HBox(1);
        receiveField = new TextArea();
        receiveField.setEditable(false);
        
        sendField = new TextField();
        sendbutton = new Button("send");
        sendBox.getChildren().addAll(sendField,sendbutton);
        
        if (kind>=0) {
            sendBox.setVisible(false);
            receiveField.setVisible(false);
        } else{
            sendBox.setVisible(true);
            receiveField.setVisible(true);
        }
            
        vb.getChildren().addAll(receiveField,sendBox);
        sendbutton.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                ((TCPListener)listener).sendmessage("mail "+sendField.getText());
                 addreceiveField("me: "+sendField.getText());
                sendField.clear();
             }
        });
        root.getChildren().addAll(vb,sureButton,yesButton,noButton,messLabel,huiLabel);

       // root.getChildren().add(liaotianBox);
        root.getChildren().add(clickmove.circles);

        //StackPane rootpane = new StackPane(root);
        gamescreen = new Scene(root,Color.GREEN);
        canvas.setscreen(gamescreen);
        canvas.setCursor(Cursor.NONE);
        resetsize();
        clock.getTransforms().add(new Scale(0.25f, 0.25f, 0, 0));

        primaryStage.setScene(gamescreen);
        if (kind>0){
             ((AiListener)listener).ai.setPanel(canvas);
            ((AiListener)listener).start();
        }
        if (kind>=0)
            state.restart();
        if (kind == 0) clock.restart();

        if (!firstset){
           return;
        }

        
        firstset = false;
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (newValue.intValue()==0) return;   
                    clock.getTransforms().add(new Scale(1f,newValue.floatValue()/oldValue.floatValue(), 0, 0));

                    ConstRec.maxsizey = (int)(newValue.intValue()-28);//(double)newValue.intValue()/oldvalue*a2);
                    ConstRec.update();
                    canvas.resetsize();
                   resetsize();
            }
         });

        
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                   if (newValue.intValue()==0) return;
                    ConstRec.maxsizex = (int)((double)newValue.intValue()/7*5);
                           clock.getTransforms().add(new Scale(newValue.floatValue()/oldValue.floatValue(), 1f, 0, 0));

                    ConstRec.update();
                    canvas.resetsize();
                    resetsize();
            }
         });

        
    }

    public void setgame(int kind) {
        setgame(kind,"",0,false);
    }
    public void setgame(int kind,boolean xianshou) {
        setgame(kind,"",0,xianshou);
    }
    public void protect() {
         Platform.runLater(new  Runnable() {
            @Override
            public void run() {
                bt1 = load.disableProperty().getValue();   
                bt2 = save.disableProperty().getValue();
                bt3 = back.disableProperty().getValue();
                bt4 = backhome.disableProperty().getValue();
                load.setDisable(true);
               save.setDisable(true);
               back.setDisable(true);
               //backhome.setDisable(true);
              }
        });
    }
    public void deprotect() {
        Platform.runLater(new  Runnable() {
            @Override
            public void run() {
        load.setDisable(bt1);
        save.setDisable(bt2);
        back.setDisable(bt3);
        //backhome.setDisable(bt4);
                      }
        });
    }

    
    public void updatecb(){
        File Dir = new File(storepath);//Homepage.class.getResource("/record/23.chess").toExternalForm());
        //System.out.println(Dir.exists());
        list = Dir.list();	   //列出当前目录下的文件
        //System.out.println(list);
        if (list.length>0){
           /* for (int i =0 ; i < list.length; ++ i){
                System.out.println(list[i]);
            }*/

            cb.setItems(FXCollections.observableArrayList(list));
            load.setDisable(false);
        } else load.setDisable(true);
    }
}
