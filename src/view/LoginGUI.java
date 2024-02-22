package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.Doctor;
import Model.Hasta;
import Model.bashekim;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_tcno;
	private JTextField fld_doctcno;
	private JPasswordField pswordfld_doc;
	private DBconnection conn = new DBconnection();
	private JPasswordField fldpass_hasta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane otomasyonu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("logos (1).png"))); // imageicon sınıfından getclass(suanki sınıfı getir) hangi packeagein icindeyse resource unu getir (getresource. imageicon classını import ettik
		lbl_logo.setBounds(367, 23, 53, 74);
		w_pane.add(lbl_logo);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(0, 161, 484, 200);
		w_pane.add(w_tabpane);
		
		JPanel jpanel_hastalogin = new JPanel();
		jpanel_hastalogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta girişi", null, jpanel_hastalogin, null); //isim verdik
		jpanel_hastalogin.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("T.C. Numaranızı girin : ");
		lblNewLabel_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 12, 156, 21);
		jpanel_hastalogin.add(lblNewLabel_1);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		fld_tcno.setBounds(157, 11, 283, 21);
		jpanel_hastalogin.add(fld_tcno);
		fld_tcno.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Şifre :");
		lblNewLabel_1_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(10, 54, 53, 21);
		jpanel_hastalogin.add(lblNewLabel_1_1);
		
		JButton btn_kayıtol = new JButton("Kayıt ol");
		btn_kayıtol.setForeground(new Color(255, 255, 255));
		btn_kayıtol.setBackground(new Color(255, 102, 102));
		btn_kayıtol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_kayıtol.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_kayıtol.setBounds(20, 98, 198, 46);
		jpanel_hastalogin.add(btn_kayıtol);
		
		JButton btn_hastalogin = new JButton("Giriş yap");
		btn_hastalogin.setForeground(new Color(255, 255, 255));
		btn_hastalogin.setBackground(new Color(255, 102, 102));
		btn_hastalogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length() == 0 || fldpass_hasta.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * from user");
						while(rs.next()) {
							if(fld_tcno.getText().equals(rs.getString("tcno")) && fldpass_hasta.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
								Hasta hasta = new Hasta();
								hasta.setId(rs.getInt("id"));
								hasta.setPassword("password");
								hasta.setTcno(rs.getString("tcno"));
								hasta.setName(rs.getString("name"));
								hasta.setType(rs.getString("type"));
								HastaGUI hGUI = new HastaGUI(hasta);
								hGUI.setVisible(true);
								dispose(); 
								key = false;
								}
							}
						}
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("böyle bir hasta bulunamadı.");
					}
				}
			}
		});
		btn_hastalogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_hastalogin.setBounds(242, 98, 198, 47);
		jpanel_hastalogin.add(btn_hastalogin);
		
		fldpass_hasta = new JPasswordField();
		fldpass_hasta.setBounds(157, 55, 283, 20);
		jpanel_hastalogin.add(fldpass_hasta);
		
		JPanel jpanel_doctorlogin = new JPanel();
		jpanel_doctorlogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor girişi", null, jpanel_doctorlogin, null);
		jpanel_doctorlogin.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("T.C. Numaranızı girin : ");
		lblNewLabel_1_2.setForeground(new Color(255, 102, 102));
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(10, 12, 156, 21);
		jpanel_doctorlogin.add(lblNewLabel_1_2);
		
		fld_doctcno = new JTextField();
		fld_doctcno.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		fld_doctcno.setColumns(10);
		fld_doctcno.setBounds(157, 11, 283, 21);
		jpanel_doctorlogin.add(fld_doctcno);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Şifre :");
		lblNewLabel_1_1_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(10, 54, 53, 21);
		jpanel_doctorlogin.add(lblNewLabel_1_1_1);
		
		JButton btn_doclogin = new JButton("Giriş yap");
		btn_doclogin.setBackground(new Color(255, 102, 102));
		btn_doclogin.setForeground(new Color(255, 255, 255));
		btn_doclogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doctcno.getText().length() == 0 || pswordfld_doc.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * from user");
						while(rs.next()) {
							if(fld_doctcno.getText().equals(rs.getString("tcno")) && pswordfld_doc.getText().equals(rs.getString("password"))) { // veritbanından gelen alanlarla aynı olmalı.
								if(rs.getString("type").equals("bashekim")) {
							
								bashekim bhekim = new bashekim(); // giris yapan bashekimdir. btn_doclogin tıklandıysa.
								bhekim.setId(rs.getInt("id"));// databasedeki verileri nesneye döküyoruz.
								bhekim.setPassword("password");// set yeni bi veri alma. get var olan veriyi getirme
								bhekim.setTcno(rs.getString("tcno")); // burda dbdeki verileri jframe e attık.
								bhekim.setName(rs.getString("name"));
								bhekim.setType(rs.getString("type"));

								BashekimGUI bGUI = new BashekimGUI(bhekim); // bashekimguı ye bGUI adında bi nesne uret. ve icine olusturdugum bhekim nesnesini at
								bGUI.setVisible(true);
								dispose(); // var olan jframei öldürdük
								}
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor(); // doctor nesnesini ürettik
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor); // doctor nesnesini doctorguı nin icine attık.
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		btn_doclogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_doclogin.setBounds(20, 96, 420, 47);
		jpanel_doctorlogin.add(btn_doclogin);
		
		pswordfld_doc = new JPasswordField();
		pswordfld_doc.setBounds(157, 55, 283, 20);
		jpanel_doctorlogin.add(pswordfld_doc);
		
		JLabel lblNewLabel = new JLabel("Hastane yönetim sistemine hoşgeldiniz");
		lblNewLabel.setForeground(new Color(255, 102, 102));
		lblNewLabel.setBounds(45, 46, 305, 38);
		w_pane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
	}
}
