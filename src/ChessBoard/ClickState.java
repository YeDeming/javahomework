/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author meepo
 */
public class ClickState {
    final static int border = ConstRec.border;
    final static int maxn = ConstRec.maxn;;
    final static int gridsize = ConstRec.gridsize;;
    final static int maxsize = 2*border+maxn*gridsize;
    final int n = 8;

    Group circles;
    
    public ClickState() {
        
        circles = new Group();
        for (int i = 0; i < n; i++) {
            Circle circle = new Circle(gridsize/2, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }

        circles.setEffect(new BoxBlur(10, 10, 3));
        circles.setVisible(false);
 
    }
    
    void play(int startx,int starty){
        System.out.println("ChessBoard.ClickState.play()");
        circles.setVisible(true);
        Timeline timeline = new Timeline();
        int j = 0;
        for (Node circle : circles.getChildren()) {
            ++j;
            double wx = Math.cos((double)j/n*2*Math.PI)*1000+startx;
            double wy = Math.sin((double)j/n*2*Math.PI)*720+starty;
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                    new KeyValue(circle.translateXProperty(), startx),
                    new KeyValue(circle.translateYProperty(), starty)),
                    new KeyFrame(new Duration(1000), // set end position at 40s
                    new KeyValue(circle.translateXProperty(), wx),
                    new KeyValue(circle.translateYProperty(), wy)));
        }

        timeline.play();
    }
}
