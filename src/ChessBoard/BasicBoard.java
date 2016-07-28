/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JFrame;
/**
 *
 * @author meepo
 */
public class BasicBoard extends Application {
     Scene gamescreen;
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        JFrame frame = new JFrame();

        ChessState state = new ChessState(frame);
        FatherListener listener = new SelftwoMouseListener(state);
        MyPanel canvas = new MyPanel(state,listener);

        state.setPanel(canvas);


        root.getChildren().add(canvas);
        gamescreen = new Scene(root,Color.GREEN);

        gamescreen.setCursor(Cursor.NONE);
        primaryStage.setScene(gamescreen);
        primaryStage.setTitle("Reversi");
        //primaryStage.getIcons().add(new Image("/home/meepo/NetBeansProjects/BasicBoard/resource/icon.png"));
                //BasicBoard.class.getResourceAsStream("/resource/icon.png")));   
        primaryStage.show();
        //state.restart();
        //((AiListener)listener).start();

    }

    private void SelftwoMouseListener(ChessState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
