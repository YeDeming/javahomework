package ChessBoard;

import Listener.AiListener;
import java.awt.FileDialog;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


import javafx.stage.Stage;
import javax.swing.JFrame;

public class ChessState extends SampleChessState{

    public int history_chess[][][],history_turn[],history_count[];
    public int history_cnt;
    public Stage primaryStage;
    public int backup[] = new int[2];
    MyPanel panel;
    FileChooser fileChooser;
    public int kind;
   BasicBoard basicBoard;

    public ChessState(Stage primaryStage,int kind){
            super();
            this.kind = kind;
            fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().add(new ExtensionFilter("存档文件", "*.chess"));

            this.primaryStage = primaryStage;
            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++j)
                            flag[i][j] = 2;
            history_chess = new int [maxn*maxn][maxn][];
            history_turn = new int [maxn*maxn];
            history_count = new int [maxn*maxn];

    }

    public void setBasicBoard(BasicBoard basicBoard) {
        this.basicBoard = basicBoard;
    }

    
    public void setKind(int kind) {
        this.kind = kind;
    }
    
    public void restart(){
        backup[0] = backup[1] = 0;
            start = true;
            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++j)
                            flag[i][j] = 2;

            flag[3][4] = 0;
            flag[4][3] = 0;
            flag[3][3] = 1;
            flag[4][4] = 1;
            flag[5][5] = 0;
            turn = 0;
            count = 4;
            history_cnt = 0;
            savehistory();
            updateavaid();

            panel.repaint();

    }

    public void savehistory(){
            history_count[history_cnt] = count;
            history_turn[history_cnt] = turn;
            for (int i = 0; i < maxn; ++ i)
                    history_chess[history_cnt][i] = flag[i].clone();
            history_cnt++;

    }
    public boolean canback(int backup_turn){
        if (kind==0){
             if (backup[backup_turn]>=2) return false;  
        }
         int tmp = history_cnt-1;
        if (history_turn[tmp]==backup_turn) tmp--;
        while (tmp>=0 && history_turn[tmp]!=backup_turn) tmp--;
        if (tmp<0) return false;
        return true;
    }
    
    public void backtohistory(int backup_turn){
        System.out.println("backto " +backup_turn);
        if (kind==0){
             if (++backup[backup_turn]>2) return;  
        }
        int tmp = history_cnt-1;
        if (history_turn[tmp]==backup_turn) tmp--;
        while (tmp>=0 && history_turn[tmp]!=backup_turn) tmp--;
        if (tmp<0) return;

        history_cnt = tmp+1;
        count = history_count[tmp];
        turn = history_turn[tmp] ;

        for (int i = 0; i < maxn; ++ i)
                flag[i] = history_chess[tmp][i].clone();


        updateavaid();
        panel.repaint();
        
        if (kind==0 && !canback(turn)){
            basicBoard.back.setDisable(true);
        } else 
            if ( !canback(basicBoard.listener.player_turn)){
                basicBoard.back.setDisable(true);
            }
    }

    public void setPanel(MyPanel panel) {
            this.panel = panel;
    }

    public void set(int i,int j){
            if (!start) return;
            if (finish !=-2) return;
            int tmp = (i<<3) |j;
            boolean candrop = false;
            for (int k = 0 ; k < avaidcnt; ++ k)
                    if (avaid[k]==tmp){
                            candrop = true;
                            break;
                    }
            if (!candrop) return;
            int opp = turn^1;

            for (int k = 0; k < 8; ++ k){
                    int x = i + fx[k][0];
                    int y = j + fx[k][1];
                    if (x<0 || y<0 || x>=maxn || y>=maxn || flag[x][y] == turn) continue;

                    while (flag[x][y]==opp){
                            x += fx[k][0];
                            y += fx[k][1];
                            if (x<0 || y<0 || x>=maxn || y>=maxn) break;
                    }
                    if (x<0 || y<0 || x>=maxn || y>=maxn) continue;

                    if (flag[x][y]==turn){
                            x = i + fx[k][0];
                            y = j + fx[k][1];
                            while  (flag[x][y]==opp){
                                    flag[x][y] = turn;
                                    x += fx[k][0];
                                    y += fx[k][1];
                            }
                    }
            }
            flag[i][j] = turn;
            turn = turn^1;
            count ++;

            savehistory();
            updateavaid();

            panel.repaint();
            
            if (avaidcnt==0) pass();

            
            if (count==maxnsqr || avaidcnt == 0){	
                finish = getwinner();

                panel.gameover(finish);
            }
    }


    public void pass(){
            turn ^= 1;
            savehistory();
            updateavaid();
    }

    public void loadchess() {
        File file = fileChooser.showOpenDialog(primaryStage.getOwner());
        if (file == null) return;

        try{
            Scanner console = new Scanner(file);
            kind = console.nextInt();
            turn = console.nextInt();
            for (int i = 0; i < maxn; ++ i)
            {
                    for (int j = 0 ;j < maxn; ++ j)
                    {
                            flag[i][j] = console.nextInt();
                    }
            }
        }
        catch (Exception e){
            return;
        }

        history_cnt = 0;
        savehistory();
        updateavaid();
        panel.repaint();
        basicBoard.clock.restart();
    }

    public void savechess(){
         
        File file = fileChooser.showSaveDialog(primaryStage.getOwner());
        if (file == null) return;

            try{
                    PrintStream out = new PrintStream(file);
                    out.println(kind);
                    out.println(turn);
                    for (int i = 0; i < maxn; ++ i)
                    {
                            for (int j = 0 ;j < maxn; ++ j)
                            {
                                    out.print(flag[i][j]+" ");
                            }
                            out.println();
                    }
                    out.close();
            } catch (FileNotFoundException e1){
                    e1.printStackTrace();
            }
    }
}
