package br.ufrn.imd0040.insiderthreat.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JButton b1 ,b2,b3,b4;
	String TimeBegin, TimeEnd;
	String UserId, OtherUserId;
	
	public Window(){
	
		super("Insider Threat");
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(4,1));
			
		b1 = new JButton("Buscar perfil de um usuário");
		b2 = new JButton("Emitir arquivo com os perfis");
		b3 = new JButton("Comparar dois usuários");
		b4 = new JButton("Procurar usuários anômalos");
		
		c.add(b1);
		c.add(b2);
		c.add(b3);
		c.add(b4);	
			
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
				
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		
		if (action.getSource() == b1) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (dd/mm/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (dd/mm/aaaa hh:mm:ss)");
			UserId = JOptionPane.showInputDialog("Digite o Id do usu�rio que deseja: ");
			
		}
		
		else if (action.getSource() == b2) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (dd/mm/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (dd/mm/aaaa hh:mm:ss)");

		}
		
		else if (action.getSource() == b3) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (mm/dd/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (mm/dd/aaaa hh:mm:ss)");
			UserId = JOptionPane.showInputDialog("Digite o Id do usu�rio que deseja:");
			OtherUserId = JOptionPane.showInputDialog("Digite o outro usu�rio que deseja comparar:");
		
		}
		
		else if (action.getSource() == b4) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (mm/dd/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (mm/dd/aaaa hh:mm:ss)");
		
		}
		
	}

	
	public String getUserId() {
		
		return UserId;
		
	}
	
	public String getOtherUserId() {
		
		return OtherUserId;
		
	}


	public String getTimeBegin() {
		
		return TimeBegin;
		
	}


	public String getTimeEnd() {
		
		return TimeEnd;
		
	}

}
