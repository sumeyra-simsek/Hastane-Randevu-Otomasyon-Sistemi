package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID =0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
			

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public HastaGUI(Hasta hasta) throws SQLException {
		
		doctorModel = new DefaultTableModel(); 
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor); 
		doctorData = new Object[2];
		
		whourModel = new DefaultTableModel(); 
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour); 
		whourData = new Object[2];
		
		appointModel = new DefaultTableModel(); 
		Object[] colappoint = new Object[3];
		colappoint[0] = "ID";
		colappoint[1] = "Doktor";
		colappoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colappoint); 
		appointData = new Object[3];
		for(int i =0; i< appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
		
		
		setResizable(false);
		setTitle("Hastane yönetim sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 383);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, sayın " + hasta.getName());
		lblNewLabel.setForeground(new Color(255, 102, 102));
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(24, 26, 282, 24);
		w_pane.add(lblNewLabel);
		
		JButton btn_cikis = new JButton("Çıkış yap");
		btn_cikis.setBackground(new Color(255, 255, 255));
		btn_cikis.setForeground(new Color(255, 102, 102));
		btn_cikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_cikis.setBounds(505, 30, 89, 23);
		w_pane.add(btn_cikis);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(0, 85, 594, 259);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_tab.addTab("Randevu sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(20, 32, 206, 199);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1 = new JLabel("Doktor listesi :");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_1.setBounds(22, 7, 78, 25);
		w_appointment.add(lblNewLabel_1);
		
		JLabel lblPoliklinikAd_1 = new JLabel("Poliklinik Adı :");
		lblPoliklinikAd_1.setForeground(new Color(255, 102, 102));
		lblPoliklinikAd_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblPoliklinikAd_1.setBounds(236, 12, 96, 14);
		w_appointment.add(lblPoliklinikAd_1);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBackground(new Color(255, 255, 255));
		select_clinic.setForeground(new Color(255, 102, 102));
		select_clinic.setBounds(236, 29, 126, 22);
		select_clinic.addItem("--poliklinik seç");
		for(int i=0; i< clinic.getList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(),clinic.getList().get(i).getName()));
			
		}
		select_clinic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(select_clinic.getSelectedIndex() !=0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
					for(int i=0 ; i< clinic.getClinicDoctorList(item.getKey()).size(); i++) {
						doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
						doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
						doctorModel.addRow(doctorData);
					}
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
				
			}
		});
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_2 = new JLabel("Doktor Seç :");
		lblNewLabel_2.setForeground(new Color(255, 102, 102));
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		lblNewLabel_2.setBounds(236, 74, 70, 14);
		w_appointment.add(lblNewLabel_2);
		
		JButton btn_selDoctor = new JButton("Seç");
		btn_selDoctor.setBackground(new Color(255, 255, 255));
		btn_selDoctor.setForeground(new Color(255, 102, 102));
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i = 0 ; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
					System.out.println(selectDoctorID + "-" + selectDoctorName);
				} else {
					Helper.showMsg("Lütfen bir doktor seçiniz.");
				}
				
			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		btn_selDoctor.setBounds(236, 99, 126, 23);
		w_appointment.add(btn_selDoctor);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(372, 32, 206, 199);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
		
		JLabel lblNewLabel_1_1 = new JLabel("Doktor listesi :");
		lblNewLabel_1_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		lblNewLabel_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1.setBounds(372, 7, 78, 25);
		w_appointment.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Randevu");
		lblNewLabel_2_1.setForeground(new Color(255, 102, 102));
		lblNewLabel_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(236, 133, 70, 14);
		w_appointment.add(lblNewLabel_2_1);
		
		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.setBackground(new Color(255, 255, 255));
		btn_addAppoint.setForeground(new Color(255, 102, 102));
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName, hasta.getName(),date);
						if(control) {
							Helper.showMsg("success");
							hasta.updatewhourStatus(selectDoctorID,date);
							updatewhourModel(selectDoctorID);
							updateappointModel(hasta.getId());
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("lütfen geçerli bir tarih giriniz.");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		btn_addAppoint.setBounds(236, 158, 126, 23);
		w_appointment.add(btn_addAppoint);
		
		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevu kayıtları", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(0, 0, 589, 231);
		w_appoint.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
	}
	public void updatewhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
	    clearModel.setRowCount(0);
	    for(int i = 0 ; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	
	public void updateappointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
	    clearModel.setRowCount(0);
	    for(int i =0; i< appoint.getHastaList (hasta_id).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);

	    }
	}
}
