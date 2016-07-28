package ChessBoard;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MyMenuBar extends JMenuBar implements ActionListener{
	JMenu mainMenu;
	JMenuItem newmenu,openmenu,savemenu,uodomenu,settingmenu;
	ChessState state;
	public MyMenuBar(ChessState state){
		super();
		mainMenu = new JMenu("Main menu");
		newmenu = new JMenuItem("New Game");
		newmenu.addActionListener(this);
		
		openmenu = new JMenuItem("Open File");
		openmenu.addActionListener(this);

		savemenu = new JMenuItem("Save Game");
		savemenu.addActionListener(this);

		uodomenu = new JMenuItem("Undo Move");
		uodomenu.addActionListener(this);
		
		settingmenu = new JMenuItem("Settings");
		settingmenu.addActionListener(this);

		add(mainMenu);
		mainMenu.add(newmenu);
		mainMenu.add(openmenu);
		mainMenu.add(savemenu);
		mainMenu.add(uodomenu);
		mainMenu.add(settingmenu);
		
		this.state = state;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==(Object)newmenu){
			state.restart();
		} else if (e.getSource()==(Object)openmenu){
			state.loadchess();
		} else if (e.getSource()==(Object)savemenu){
			state.savechess();
		} else if (e.getSource()==(Object)uodomenu){
			state.backtohistory(1-state.turn);
		} else if (e.getSource()==(Object)settingmenu){
		}
	}
}
