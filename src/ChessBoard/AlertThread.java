/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessBoard;

import javafx.scene.control.Alert;

/**
 *
 * @author meepo
 */
public class AlertThread extends Thread{
    int sum;
    public void run(){

        try{
                if (sum==0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "平局");
                    alert.showAndWait();
                }
                else if (sum>0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "黑棋胜");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "白棋胜");
                    alert.showAndWait();
                  }
        } catch (Exception e1) {
                 //TODO: handle exception

                 
       }
    }
    
}
