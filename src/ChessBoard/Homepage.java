package ChessBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;

import javax.swing.*;

import com.sun.corba.se.impl.naming.namingutil.CorbalocURL;

import java.awt.*;


public class Homepage extends JPanel{
	final static int border = ConstRec.border;
	final static int maxn = ConstRec.maxn;;
	final static int gridsize = ConstRec.gridsize;;
	final static int maxsize = 2*border+maxn*gridsize;
	final static int control_redpointsize = 25; 
	final static int radius = ConstRec.gridsize/2;
	
	final static int alpha = 128;
	MyButton button1,button2,button3,button4;
	int flag[][] = new int[maxn][maxn];
	int turn = 0;
	int count;
	public Homepage() {
		// TODO Auto-generated constructor stub
		//setLayout(new BorderLayout());
		setLayout(null);

		setOpaque(false);
		button1 = new MyButton("人机对战");

		button1.setBounds(10, 10, button1.getWidth(), button1.getHeight());
		add(button1);
/*		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4, 1));
		button1 = new MyButton("人机对战");
		button2 = new MyButton("单机人人游戏");
		button3 = new MyButton("网络对战");
		button4 = new MyButton("Settings");
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		add("Center",buttons);*/
		
		//JLabel title = new JLabel("Reversi");
		//add("North",title);
		
		for (int i = 0; i < maxn; ++ i)
			for (int j = 0; j < maxn; ++j)
				flag[i][j] = 2;
		flag[3][4] = 0;
		flag[4][3] = 0;
		flag[3][3] = 1;
		flag[4][4] = 1;
		turn = 0;
		count = 4;
	}

	@Override
	public void paint(Graphics g) {
		  //AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		  //g.setColor(Color.blue);//这里设置背景颜色

		//g.fillRect(0, 0, this.getWidth(), this.getHeight());//这里填充背景颜色
		// TODO Auto-generated method stub
		//g.clearRect(0, 0, getWidth(),getHeight());
		//ImageIcon bg = new ImageIcon("background.jpg");
		//Image ima=Toolkit.getDefaultToolkit().getImage("background.jpg"); 
		//System.out.println(ima.getWidth("background.jpg"));
       //g.drawImage(ima, 0,0,null);
		super.paintComponent(g);
		return;

/*		setBackground(Color.yellow);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(getBackground());
        g2d.fill(new Rectangle(0,0,maxsize,maxsize));

        
        g2d.setColor(Color.black);
		for (int i = 0; i < maxn+1; ++ i){
			int x = border+gridsize*i;
			g2d.drawLine(x,border , x, maxsize-border);
			g2d.drawLine(border , x, maxsize-border,x);
		}
		
		for (int i = 0; i < maxn; ++ i)
			for (int j = 0; j < maxn; ++ j){
				int circle_x = border + i*gridsize+1;
				int circle_y = border + j*gridsize+1;
				
				if (flag[i][j]==0){
					g2d.setColor(new Color(0,0,0));
					g2d.fillOval(circle_x,circle_y,gridsize-2,gridsize-2);
				} else if (flag[i][j]==1){
					g2d.setColor(new Color(255,255,255));		
					g2d.fillOval(circle_x,circle_y,gridsize-2,gridsize-2);
				}
			}
		*/
	}
	@Override
	public Dimension getPreferredSize()
	{
		 return new Dimension(maxsize, maxsize);
		
	}
}
