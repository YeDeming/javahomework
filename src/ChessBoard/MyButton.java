package ChessBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.Icon;
import javax.swing.JButton;

public class MyButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private Color pressColor;
	private Color backColor;
	private Color borderColor;
	private Color rollOverColor=Color.white;
	
	public MyButton(){
		super();
	}
	public MyButton(String str){
		super(str);
	    Dimension size = this.getPreferredSize();
	    size.width=35;
	    size.height=15;
	    this.setPreferredSize(size);
	    
	    this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		//this.setBorder(new RoundBorder());

	}
	public MyButton(Icon icon){
			super(icon);
			Dimension size = this.getPreferredSize();
		  	size.width=70;
		    size.height=30;
		    this.setPreferredSize(size);
			this.setContentAreaFilled(false);
	}
	public MyButton(String str,Icon icon){
			super(str,icon);
		  	Dimension size = this.getPreferredSize();
		    size.width=70;
		    size.height=30;
		    this.setIcon(icon);
		    this.setPreferredSize(size);
			this.setContentAreaFilled(false);
	}
	
	
	public void setBackground(Color backColor){
		this.backColor=backColor;
	}
	public void setBorderColor(Color borderColor){
		this.borderColor=borderColor;
	}
	public void setPressColor(Color pressColor){
		this.pressColor=pressColor;
	}
	public void setRollOverColor(Color rollOverColor){
		this.rollOverColor=rollOverColor;
	}
	
	
	//画按钮
	public void paintComponent(Graphics g){
		if(getModel().isRollover()){
			g.setColor(rollOverColor);
		}else{
			g.setColor(backColor);
		}
		if(getModel().isPressed()){
			g.setColor(pressColor);
		}
		g.fill3DRect(0, 0,getSize().width-1,getSize().height-1,true);
		super.paintComponent(g);
	}
	
	//画按钮边框
	public void paintBorder(Graphics g){
		//g.setColor(borderColor);
		g.setColor(Color.BLACK);
		g.drawRoundRect(3, 3, this.getWidth()-4, this.getHeight()-4, 11, 11);
		//g.draw3DRect(0, 0,getSize().width-1,getSize().height-1,true);
	}

	public boolean contains(int x,int y){
	    if(shape == null || (!shape.getBounds().equals(this.getBounds()))){
	      shape = new Ellipse2D.Float(0,0,getWidth(),getHeight());
	    }
	    return shape.contains(x,y);
	  }

}

	
