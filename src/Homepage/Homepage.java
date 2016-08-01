/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Homepage;

import ChessBoard.BasicBoard;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author meepo
 */
public class Homepage extends Group{
    public Button b1,b2,b3,b4,b5,b6,b7,b9,b10,connect;
    public Label titleLabel;
    public BasicBoard basicBoard;
    //HomepageThread thread;
    boolean b1click = false;
    boolean b3click = false;
    
    boolean b9click = false;        //作为服务器按钮
    boolean b10click = false;      //作为客户端按钮
    
    public TextField ip,port;
    
    public Homepage(BasicBoard basicBoard){

        this.basicBoard = basicBoard;
        titleLabel = new Label("Reversi");
        titleLabel.setTranslateX(175);
        titleLabel.setTranslateY(100);
        titleLabel.setFont(new Font("Tahoma",45));
        VBox buttons = new VBox(10);
        buttons.setLayoutX(150);
        buttons.setLayoutY(240);
        b1 = new Button("人机对战");
        b1.setMinSize(200, 50);

        HBox aibuttons = new HBox(5);
        aibuttons.setLayoutX(410);
        aibuttons.setLayoutY(250);
        b5 = new Button("简单");
        b5.setMinSize(40, 30);

        b6 = new Button("中等");
        b6.setMinSize(40, 30);

        b7 = new Button("困难");
        b7.setMinSize(40, 30);

        b2 = new Button("本地对战");
        b2.setMinSize(200, 50);
        
        b3 = new Button("网络对战");
        b3.setMinSize(200, 50);
        
        b9 = new Button("开启服务");
        b9.setTranslateX(410);
        b9.setTranslateY(350);
        b9.setMinSize(40, 30);

        b10 = new Button("连接服务");
        b10.setMinSize(40, 30);
        b10.setTranslateX(490);
        b10.setTranslateY(350);

        b4 = new Button("Settings");
        b4.setMinSize(200, 50);
        
        Image image = new Image(Homepage.class.getResource( "/Resource/1.png").toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setX(355);
        imageView.setY(245);
        
        ImageView imageView2 = new ImageView(image);
        imageView2.setX(355);
        imageView2.setY(365);
        
        getChildren().add(titleLabel);
        
        buttons.getChildren().addAll(b1,b2,b3,b4);
        aibuttons.getChildren().addAll(b5,b6,b7);
        
        getChildren().add(imageView);
        getChildren().add(b9);
        getChildren().add(b10);
        getChildren().add(imageView2);
        getChildren().add(buttons);
        getChildren().add(aibuttons);
        
        VBox vbip = new VBox();
        HBox hb = new HBox();
        ip = new TextField("127.0.0.1");
        hb.getChildren().add(new Label("IP:    "));
        hb.getChildren().add(ip);
        
        HBox hb2 = new HBox();
        port = new TextField("6789");
        hb2.getChildren().add(new Label("Port:"));
        hb2.getChildren().add(port);
        hb2.setSpacing(10);
 
        HBox hb3 = new HBox();
        connect = new Button("开始游戏");
        RadioButton xianshou = new RadioButton("先手");
        hb3.getChildren().addAll(connect,xianshou);
        vbip.setLayoutX(360);
        vbip.setLayoutY(400);
        vbip.getChildren().addAll(hb,hb2,hb3);
        
        
        getChildren().add(vbip);
        
        
        aibuttons.setVisible(false);
        FadeTransition ftai = new FadeTransition(Duration.millis(500), aibuttons);  
        ftai.setFromValue(0);  
        ftai.setToValue(1);  
        
        imageView.setVisible(false);
        FadeTransition ft8 = new FadeTransition(Duration.millis(100), imageView);  
        ft8.setFromValue(0);  
        ft8.setToValue(1);  

        b9.setVisible(false);
        FadeTransition ft9 = new FadeTransition(Duration.millis(500), b9);  
        ft9.setFromValue(0);  
        ft9.setToValue(1);  
        
        b10.setVisible(false);
        FadeTransition ft10 = new FadeTransition(Duration.millis(500), b10);  
        ft10.setFromValue(0);  
        ft10.setToValue(1);  

        imageView2.setVisible(false);
        FadeTransition ft11 = new FadeTransition(Duration.millis(100), imageView2);  
        ft11.setFromValue(0);  
        ft11.setToValue(1);  

        vbip.setVisible(false);
        FadeTransition ftvbip = new FadeTransition(Duration.millis(100), vbip);  
        ftvbip.setFromValue(0);  
        ftvbip.setToValue(1);  

        
        b1.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button1");
                if (!b1click){
                    aibuttons.setVisible(true);
                    imageView.setVisible(true);
                    ft8.play();
                    ftai.play();
                } else{
                    aibuttons.setVisible(false);
                    imageView.setVisible(false);
                }
                b1click^=true;
             }
        });
         b2.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button2");
                basicBoard.setgame(0);
             }
        });        
         
         b5.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button5");
                basicBoard.setgame(1);
             }
        });
         
        b6.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button6");
                basicBoard.setgame(2);
             }
        });
                  
        b7.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button7");
                basicBoard.setgame(3);
             }
        });
        
         b9.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button9"); 
                b10click = false;
                if (!b9click){
                    vbip.setVisible(true);
                    ip.setEditable(false);
                    //connect.setText("创建");
                    ftvbip.play();
                } else{
                    vbip.setVisible(false);
                }
                b9click ^= true;
             }
        });
         
         b10.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button10");
                xianshou.setVisible(true);
                b9click = false;
                if (!b10click){
                    vbip.setVisible(true);     
                    ip.setEditable(true);
                    ftvbip.play();
                    //connect.setVisible(false);
                    //connect.setText("连接");
                    //connect.setVisible(true);
                } else{
                    vbip.setVisible(false);
                }
                b10click ^= true;
             
             }
        });
                 
         b10.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button10");
                xianshou.setVisible(false);
                b9click = false;
                if (!b10click){
                    vbip.setVisible(true);     
                    ip.setEditable(true);
                    ftvbip.play();
                } else{
                    vbip.setVisible(false);
                }
                b10click ^= true;
             
             }
        });
         
        b3.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button3");
                if (!b3click){
                    b9.setVisible(true);
                    ft9.play();
                    b10.setVisible(true);
                    ft10.play();
                    imageView2.setVisible(true);
                    ft11.play();
                } else{
                    b9.setVisible(false);
                    b10.setVisible(false);
                    imageView.setVisible(false);
                }
                b3click^=true;}
        });
        
        connect.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("go to connect");
                if (b9click){
                    basicBoard.setgame(-1,"",Integer.valueOf(port.getText()),xianshou.isSelected());
                } else if (b10click){
                    basicBoard.setgame(-2,ip.getText(),Integer.valueOf(port.getText()),false);
                }
             }
       });
        
    }
}
