package ChessBoard;

import Listener.TCPListener;
import java.util.Calendar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.util.Duration;

    /**
     * Clock made of 6 of the Digit classes for hours, minutes and seconds.
     */
    public  class Clock extends Parent {
        private Digit[] digits;
        private Timeline secondTimeline;
        int currentsecond;
        TCPListener listener;
        public Clock(Color onColor, Color offColor) {
            
            // create effect for on LEDs
            Glow onEffect = new Glow(1.7f);
            onEffect.setInput(new InnerShadow());
            // create effect for on dot LEDs
            Glow onDotEffect = new Glow(1.7f);
            onDotEffect.setInput(new InnerShadow(5,Color.BLACK));
            // create effect for off LEDs
            InnerShadow offEffect = new InnerShadow();
            // create digits
            digits = new Digit[4];
            for (int i = 0; i < 4; i++) {
                Digit digit = new Digit(onColor, offColor, onEffect, offEffect);
                digit.setLayoutX(i * 80 + ((i + 1) % 2) * 20);
                digits[i] = digit;
                getChildren().add(digit);
            }
            // create dots
            Group dots = new Group(
                    new Circle(80 + 54 + 20, 44, 6, onColor),
                    new Circle(80 + 54 + 17, 64, 6, onColor));
            dots.setEffect(onDotEffect);
            getChildren().add(dots);
            // update digits to current time and start timer to update every second
            setcurrent();
            //play();
        }
        public void setcurrent(){
            currentsecond = ConstRec.limitsecond;
            refreshClocks();
        }
        
        private void refreshClocks() {
            int minutes =  currentsecond/60;
            int seconds =  currentsecond%60;
            
            digits[0].showNumber(minutes / 10);
            digits[1].showNumber(minutes % 10);
            digits[2].showNumber(seconds / 10);
            digits[3].showNumber(seconds % 10);
        }

        public void play() {

            if (secondTimeline != null) {
                secondTimeline.stop();
            }
            secondTimeline = new Timeline();
            secondTimeline.setCycleCount(Timeline.INDEFINITE);
            secondTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            currentsecond--;
                            refreshClocks();
                            if (currentsecond<=0){
                                secondTimeline.stop();
                                listener.sendmessage("timeout");
                            }
                        }
                    }));
            secondTimeline.play();
        }

        public void restart(){
           stop();
           setcurrent();
           play();
        }

        public void stop(){
            //delayTimeline.stop();
            if (secondTimeline != null) {
                secondTimeline.stop();
            }
        }

    void setListener(TCPListener tcpListener) {
        this.listener = tcpListener;
    }
        
    }

   



