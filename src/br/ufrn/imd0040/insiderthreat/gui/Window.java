/**
 * Classe Window
 * 
 * Contém o esboço da interface gráfica.
 * 
 * @author Abmael Dantas
 * @author Julia Ferreira
 * @version 2018.11.29
 */ 

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
	
	JButton b1 ,b2,b3;
	String TimeBegin, TimeEnd;
	String UserId, OtherUserId;
	
	public Window(){
	
		super("Insider Threat");
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(3,1));
			
		b1 = new JButton("Buscar perfil de um usu�rio");
		b2 = new JButton("Vizualizar Histograma");
		b3 = new JButton("Procurar usu�rios an�malos");
		
		c.add(b1);
		c.add(b2);
		c.add(b3);	
			
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
				
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		
		if (action.getSource() == b1) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (mm/dd/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (mm/dd/aaaa hh:mm:ss)");
			UserId = JOptionPane.showInputDialog("Digite o Id do usu�rio que deseja: ");
			
		}
		
		else if (action.getSource() == b2) {
			
			TimeBegin = JOptionPane.showInputDialog("Digite a data e horario de inicio: (mm/dd/aaaa hh:mm:ss)");
			TimeEnd = JOptionPane.showInputDialog("Digite a data e horario final: (mm/dd/aaaa hh:mm:ss)");

		}
		
		
		else if (action.getSource() == b3) {
			
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
