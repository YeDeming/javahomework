/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.concurrent.Task;/*
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;*/
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  /**
 *
 * @author meepo
 */

public class Music{
     //ContinuousAudioDataStream gg;
     //AudioStream as;
    MediaPlayer player;
    public Music(String Filename) { 
       // try{ //File.separator+"Resource"+File.separator+Filename
            Media media=new Media(Music.class.getResource(File.separator+"Resource"+File.separator+Filename).toExternalForm());
            player = new MediaPlayer(media);
/*            as = new AudioStream(Music.class.getResource(File.separator+"Resource"+File.separator+Filename).openStream()); 
            
            AudioData data = as.getData(); 
           gg= new ContinuousAudioDataStream(data); 
            AudioPlayer.player.start(gg);*/
            
     /*   } catch(FileNotFoundException e){
            System.out.print("FileNotFoundException "); 
        }catch(IOException e){
            System.out.print("有错误!"); 
        }*/
        }
    public void stop(){
            player.stop();
    }
    
    public void start(){

            player.play();
            player.setCycleCount(10);    }
   /* @Override
    protected Integer call() throws Exception {
            // AudioPlayer.player.start(gg);
          //    AudioPlayer.player.start(as);
             //AudioPlayer.player.start(gg);
             //start();
             return 0;
    }*/
}

