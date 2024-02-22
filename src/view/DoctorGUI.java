package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1]="Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];		
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		
		
		
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.PINK);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, sayın" + doctor.getName());
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(23, 28, 308, 24);
		w_pane.add(lblNewLabel);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 63, 484, 287);
		w_pane.add(tabbedPane);

		JPanel w_whour = new JPanel();
		w_whour.setBackground(Color.WHITE);
		tabbedPane.addTab("Çalışma saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(20, 11, 130, 28);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setBackground(new Color(255, 255, 255));
		select_time.setForeground(new Color(255, 102, 102));
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30" }));
		select_time.setBounds(172, 11, 73, 22);
		w_whour.add(select_time);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(20, 50, 449, 198);
		w_whour.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.setBackground(new Color(255, 255, 255));
		btn_addWhour.setForeground(new Color(204, 102, 102));
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {

				}
				if (date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih girin.");
				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		btn_addWhour.setBounds(255, 11, 89, 23);
		w_whour.add(btn_addWhour);
		
		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.setBackground(new Color(255, 255, 255));
		btn_deleteWhour.setForeground(new Color(255, 102, 102));
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow >=0) {
					
					String selectRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					boolean control;
					try {	
						control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);							
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					Helper.showMsg("Lütfen bir tarih seçiniz !");
				}
			}
		});
		btn_deleteWhour.setBounds(380, 11, 89, 22);
		w_whour.add(btn_deleteWhour);

		JButton btnNewButton = new JButton("Çıkış");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.PINK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(384, 32, 89, 23);
		w_pane.add(btnNewButton); }
		
		public void updateWhourModel(Doctor doctor) throws SQLException { 
			DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel(); 
			clearModel.setRowCount(0); 
			for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
				whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
				whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
                whourModel.addRow(whourData);
			}
				
		
	}
}
