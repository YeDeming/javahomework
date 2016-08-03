/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Homepage;

import ChessBoard.BasicBoard;
import ChessBoard.ConstRec;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    public Label l1,l2;
    public Text titleText;
    public BasicBoard basicBoard;
    //HomepageThread thread;
    boolean b1click = false;
    boolean b3click = false;
    boolean b4click = false;
    boolean b9click = false;        //作为服务器按钮
    boolean b10click = false;      //作为客户端按钮
    
    public TextField ip,port,limitseconds;

    VBox aibuttons;
    VBox buttons,vbip;
    HBox limittime;
    ImageView imageView,imageView2;
    Image image;
    public Homepage(BasicBoard basicBoard){

        this.basicBoard = basicBoard;
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        titleText = new Text();
        titleText.setEffect(ds);
        titleText.setCache(true);
        titleText.setFill(Color.RED);
        titleText.setText("Reversi");
        //titleLabel = new Label("Reversi");
        
        //titleLabel.setFont(new Font("Tahoma",45));
        buttons = new VBox();
        b1 = new Button("人机对战");
        aibuttons = new VBox();
        HBox aibuttons_part = new HBox(5);
        b5 = new Button("简单");
        b6 = new Button("中等");
        b7 = new Button("困难");
        b2 = new Button("本地对战");
        b3 = new Button("网络对战");
        b9 = new Button("开启服务");
        b10 = new Button("连接服务");
        b4 = new Button("Settings");

        Image image = new Image(Homepage.class.getResource( "/Resource/jiantou2.png").toExternalForm());
        imageView = new ImageView(image);
        //imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(basicBoard.primaryStage.heightProperty().divide(10));
        imageView.fitWidthProperty().bind(basicBoard.primaryStage.widthProperty().divide(10));
        
        imageView2 = new ImageView(image);
        //imageView2.setPreserveRatio(true);
        imageView2.fitHeightProperty().bind(basicBoard.primaryStage.heightProperty().divide(10));
        imageView2.fitWidthProperty().bind(basicBoard.primaryStage.widthProperty().divide(10));

        getChildren().add(titleText);
        
        buttons.getChildren().addAll(b1,b2,b3,b4);
        RadioButton aixianshou = new RadioButton("先手");

        aibuttons_part.getChildren().addAll(b5,b6,b7);
        aibuttons.getChildren().addAll(aibuttons_part,aixianshou);
        
        getChildren().add(imageView);
        getChildren().add(b9);
        getChildren().add(b10);
        getChildren().add(imageView2);
        getChildren().add(buttons);
        getChildren().add(aibuttons);
        
        vbip = new VBox();
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

        vbip.getChildren().addAll(hb,hb2,hb3);
        
        getChildren().add(vbip);
        
        limittime = new HBox();
        limitseconds = new TextField("10");
        l1 = new Label("Think Time(s):");

        limittime.getChildren().addAll(l1,limitseconds);
        getChildren().add(limittime);
        aibuttons.setVisible(false);
        FadeTransition ftai = new FadeTransition(Duration.millis(300), aibuttons);  
        ftai.setFromValue(0);  
        ftai.setToValue(1);  
        
        imageView.setVisible(false);
        FadeTransition ft8 = new FadeTransition(Duration.millis(100), imageView);  
        ft8.setFromValue(0);  
        ft8.setToValue(1);  

        b9.setVisible(false);
        FadeTransition ft9 = new FadeTransition(Duration.millis(300), b9);  
        ft9.setFromValue(0);  
        ft9.setToValue(1);  
        
        b10.setVisible(false);
        FadeTransition ft10 = new FadeTransition(Duration.millis(300), b10);  
        ft10.setFromValue(0);  
        ft10.setToValue(1);  

        imageView2.setVisible(false);
        FadeTransition ft11 = new FadeTransition(Duration.millis(100), imageView2);  
        ft11.setFromValue(0);  
        ft11.setToValue(1);  

        vbip.setVisible(false);
        FadeTransition ftvbip = new FadeTransition(Duration.millis(300), vbip);  
        ftvbip.setFromValue(0);  
        ftvbip.setToValue(1);  
        
        limittime.setVisible(false);
        FadeTransition ftlimittime = new FadeTransition(Duration.millis(300), limittime);  
        ftlimittime.setFromValue(0);  
        ftlimittime.setToValue(1);  
        
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
                basicBoard.setgame(1,aixianshou.isSelected());
             }
        });
         
        b6.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button6");
                basicBoard.setgame(2,aixianshou.isSelected());
             }
        });
                  
        b7.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button7");
                basicBoard.setgame(3,aixianshou.isSelected());
             }
        });
        
         b9.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button9"); 
                b10click = false;
                if (!b9click){
                    vbip.setVisible(true);
                    ip.setEditable(false);
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
                   vbip.setVisible(false);
                   b9click = false;
                   b10click = false;
                }
                b3click^=true;}
        });
         b4.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button4");
                if (!b4click){
                        b4.setText("确定");
                        limittime.setVisible(true);
                        ftlimittime.play();
                } else{
                    b4.setText("Settings");

                    limittime.setVisible(false);
                    int tmp = 0;
                    try{
                        tmp =  Integer.valueOf(limitseconds.getText());
                    }catch(Exception e){
                        System.out.println("不是正整数");
                    }
                    if (tmp>0)
                        ConstRec.limitsecond = tmp;
                    
                }
                b4click^=true;}
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
        resetsize();
    }
    
    public void resetsize(){
        int maxsizex = ConstRec.maxsizex;
        int maxsizey = ConstRec.maxsizey;
        //titleText.setFont(new Font("Tahoma",45));
        
        titleText.setFont(Font.font(null, FontWeight.BOLD, 70));
        titleText.setTranslateX(maxsizex/3.1);
        titleText.setTranslateY(maxsizey/3.5);
        buttons.setSpacing(maxsizey/50);
        buttons.setLayoutX(maxsizex/3);
        buttons.setLayoutY(maxsizey/2);
        b1.setMinSize(maxsizex/5*2, maxsizey/10);
        b2.setMinSize(maxsizex/2.5, maxsizey/10);
        b3.setMinSize(maxsizex/2.5, maxsizey/10);
        b4.setMinSize(maxsizex/2.5, maxsizey/10);

        aibuttons.setSpacing(maxsizex/100);
        aibuttons.setLayoutX(maxsizex/5*4+maxsizex/50);
        aibuttons.setLayoutY(maxsizey/2);

        b5.setMinSize(maxsizex/12.5, maxsizey/16.67);
        b6.setMinSize(maxsizex/12.5, maxsizey/16.67);
        b7.setMinSize(maxsizex/12.5, maxsizey/16.67);
        
        b9.setTranslateX(maxsizex*4/5+maxsizex/50);
        b9.setTranslateY(maxsizey*7/10);
        b9.setMinSize(maxsizex/12.5, maxsizey/16.67);

        b10.setTranslateX(maxsizex-maxsizex/20+maxsizex/50);
        b10.setTranslateY(maxsizey*7/10);
        b10.setMinSize(maxsizex/12.5, maxsizey/16.67);
                

        imageView.setX(maxsizex*29/40);
        imageView.setY(maxsizey/2);

        imageView2.setX(maxsizex*29/40);
        imageView2.setY(maxsizey*7/10);
        
        vbip.setLayoutX(maxsizex*39/50);
        vbip.setLayoutY(maxsizey*4/5);        
        limitseconds.setMaxWidth(maxsizex/10);
        limitseconds.setMaxHeight(maxsizey/100);
        Font fontseconds = new Font("Tahoma",15);
        l1.setFont(fontseconds);limitseconds.setFont(fontseconds);
        limittime.setLayoutX(maxsizex/3-maxsizex/4-maxsizex/20);
        limittime.setLayoutY(maxsizey*7/8);
               
    }
}
