package ChessBoard;

import java.awt.FileDialog;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JFrame;

public class ChessState extends SampleChessState{

    public int history_chess[][][],history_turn[],history_count[];
    public int backupwhite,backupblack,history_cnt;

    MyPanel panel;
    JFrame frame;
    public ChessState(JFrame frame){
            super();
            this.frame = frame;
            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++j)
                            flag[i][j] = 2;
            history_chess = new int [maxn*maxn][maxn][];
            history_turn = new int [maxn*maxn];
            history_count = new int [maxn*maxn];

            backupblack = 0;
            backupwhite = 0;

    }

    public void restart(){
            start = true;
            for (int i = 0; i < maxn; ++ i)
                    for (int j = 0; j < maxn; ++j)
                            flag[i][j] = 2;

            flag[3][4] = 0;
            flag[4][3] = 0;
            flag[3][3] = 1;
            flag[4][4] = 1;
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

    public void backtohistory(int backup_turn){
            int tmp = history_cnt-1;
            while (tmp>=0 && history_turn[tmp]!=backup_turn) tmp--;
            if (tmp<0) return;

            history_cnt = tmp+1;
            count = history_count[tmp];
            turn = history_turn[tmp] ;

            for (int i = 0; i < maxn; ++ i)
                    flag[i] = history_chess[tmp][i].clone();


            updateavaid();
            panel.repaint();
    }

    public void setPanel(MyPanel panel) {
            this.panel = panel;
    }

    public void set(int i,int j){
            if (!start) return;
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

            if (count==maxn*maxn || avaidcnt == 0){		
                    //panel.gameover(getwinner());
            }
    }


    public void pass(){
            turn ^= 1;
            savehistory();
            updateavaid();
    }

    public void loadchess() {
            FileDialog fd = new FileDialog(frame, "载入存档", FileDialog.LOAD);
            fd.setVisible(true);
    if (fd.getFile()==null) return;   //若点击取消则不进行载入

        File file = new File(fd.getDirectory()+fd.getFile());
            start = true;

        try{
            Scanner console = new Scanner(file);
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
    }

    public void savechess(){
            FileDialog fd = new FileDialog(frame, "保存存档", FileDialog.SAVE);
            fd.setVisible(true);
    if (fd.getFile()==null) return;   //若点击取消则不进行保存
        File file = new File(fd.getDirectory()+fd.getFile()+".chess");
            try{
                    PrintStream out = new PrintStream(file);
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
