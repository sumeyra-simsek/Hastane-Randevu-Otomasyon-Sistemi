package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();
			

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setTitle("Hastane yönetim sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 264, 306);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(204, 204, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ad Soyad :");
		lblNewLabel.setForeground(new Color(255, 102, 102));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(20, 11, 68, 29);
		w_pane.add(lblNewLabel);
		
		fld_name = new JTextField();
		fld_name.setBounds(20, 39, 190, 29);
		w_pane.add(fld_name);
		fld_name.setColumns(10);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(20, 107, 190, 29);
		w_pane.add(fld_tcno);
		
		JLabel lblTcNo = new JLabel("T.C. Numarası :");
		lblTcNo.setForeground(new Color(255, 102, 102));
		lblTcNo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTcNo.setBounds(20, 79, 102, 29);
		w_pane.add(lblTcNo);
		
		JLabel lblifre = new JLabel("Şifre :");
		lblifre.setForeground(new Color(255, 102, 102));
		lblifre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblifre.setBounds(20, 147, 68, 29);
		w_pane.add(lblifre);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(20, 178, 190, 29);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayıt ol");
		btn_register.setBackground(new Color(255, 255, 255));
		btn_register.setForeground(new Color(255, 102, 102));
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_tcno.getText().length() == 0 || fld_pass.getText().length() == 0 || fld_name.getText().length() == 0 ) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = hasta.register(fld_tcno.getText(), fld_pass.getText(), fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						} else Helper.showMsg("error");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setBounds(20, 218, 89, 23);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri dön");
		btn_backto.setBackground(new Color(255, 255, 255));
		btn_backto.setForeground(new Color(255, 102, 102));
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setBounds(121, 218, 89, 23);
		w_pane.add(btn_backto);
	}
}
