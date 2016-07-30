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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author meepo
 */
public class Homepage extends Group{
    public Button b1,b2,b3,b4,b5,b6,b7;
    public Label titleLabel;
    public BasicBoard basicBoard;
    //HomepageThread thread;
    boolean b1click = false;
    public Homepage(BasicBoard basicBoard){
       // thread = new HomepageThread(basicBoard);
        this.basicBoard = basicBoard;
        titleLabel = new Label("Reversi");
        titleLabel.setTranslateX(175);
        titleLabel.setTranslateY(100);
        //titleLabel.setMinSize(200, 72);
        titleLabel.setFont(new Font("Tahoma",45));

        b1 = new Button("人机对战");
        b1.setTranslateX(150);
        b1.setTranslateY(240);
        b1.setMinSize(200, 50);

        b5 = new Button("简单");
        b5.setTranslateX(410);
        b5.setTranslateY(250);
        b5.setMinSize(40, 30);

        b6 = new Button("中等");
        b6.setTranslateX(460);
        b6.setTranslateY(250);
        b6.setMinSize(40, 30);

        b7 = new Button("困难");
        b7.setTranslateX(510);
        b7.setTranslateY(250);
        b7.setMinSize(40, 30);

        b2 = new Button("本地对战");
        b2.setTranslateX(150);
        b2.setTranslateY(300);
        b2.setMinSize(200, 50);
        
        b3 = new Button("网络对战");
        b3.setTranslateX(150);
        b3.setTranslateY(360);
        b3.setMinSize(200, 50);
        
        b4 = new Button("Settings");
        b4.setTranslateX(150);
        b4.setTranslateY(420);
        b4.setMinSize(200, 50);
        
        Image image = new Image(Homepage.class.getResource( "/Resource/1.png").toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setX(355);
        imageView.setY(245);
        
        getChildren().add(titleLabel);
        getChildren().add(b1);
        getChildren().add(b2);
        getChildren().add(b3);
        getChildren().add(b4);
        getChildren().add(b5);
        getChildren().add(b6);
        getChildren().add(b7);
        getChildren().add(imageView);

        b5.setVisible(false);
        FadeTransition ft5 = new FadeTransition(Duration.millis(500), b5);  
        ft5.setFromValue(0);  
        ft5.setToValue(1);  
        
        b6.setVisible(false);
        FadeTransition ft6 = new FadeTransition(Duration.millis(500), b6);  
        ft6.setFromValue(0);  
        ft6.setToValue(1);  
        
        b7.setVisible(false);
        FadeTransition ft7 = new FadeTransition(Duration.millis(500), b7);  
        ft7.setFromValue(0);  
        ft7.setToValue(1);  

        imageView.setVisible(false);
        FadeTransition ft8 = new FadeTransition(Duration.millis(100), imageView);  
        ft8.setFromValue(0);  
        ft8.setToValue(1);  

        b1.setOnAction(new EventHandler<ActionEvent>(){
             public void handle(ActionEvent me) {
                System.out.println("button1");
                if (!b1click){
                b5.setVisible(true);
                ft5.play();
                b6.setVisible(true);
                ft6.play();                
                b7.setVisible(true);
                ft7.play();           
                
                imageView.setVisible(true);
                ft8.play();
                } else{
                    b5.setVisible(false);
                    b6.setVisible(false);
                    b7.setVisible(false);
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
    }
}
