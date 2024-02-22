package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;
import Model.bashekim;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame { // bashekim arayuzu

	static bashekim bashekim = new bashekim();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTcno;
	private JTextField fld_dPass;
	private JTextField fld_doctorID;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null; // bir tabloya veri eklemek icin defaulttablemodelden yararlanırız
	private Object[] doctorData = null; // dataların icine atacagımız veriler olucak bu object.doctordata doktorların verilerini tutmak icin tanımlanan bir array.
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(bashekim bashekim) throws SQLException {

		doctorModel = new DefaultTableModel(); // doktorları alıyoz db den (bashekim.javadan)
		Object[] colDoctorName = new Object[4]; // object sınıfı tum sınıfların en geneli.stringte olabilirdi ama object tanımlamayı tercih ettik.coldoctorname kolon baslıklarını yazacagımız bir array olacak.db tablosu 4 kolonlu oldugu ıcın degere 4 dedik.
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC NO";
		colDoctorName[3] = "Şifre";
		doctorModel.setColumnIdentifiers(colDoctorName); // kolonların icine coldoctorname objesini attık
		doctorData = new Object[4];// yukardaki kolon sayısı ne kadarsa bunuda aynı yapmalıyız cunku satırları olusturyoruz esit olmalı.
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId(); // getıd user ın icinden geliyor.
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}

		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		// workerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0]="ID";
		colWorker[1]="Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];
		

		setTitle("Hastane yönetim sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, sayın " + bashekim.getName());
		lblNewLabel.setForeground(new Color(255, 102, 102));
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(20, 25, 282, 24);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış yap");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(255, 102, 102));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnNewButton.setBounds(608, 26, 89, 23);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(0, 112, 734, 349);
		w_pane.add(w_tab);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_tab.addTab("Doktor Yönetimi", null, panel, null);
		panel.setLayout(null);

		JLabel label = new JLabel("Ad Soyad ");
		label.setForeground(new Color(255, 102, 102));
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		label.setBounds(583, 11, 62, 14);
		panel.add(label);

		fld_dName = new JTextField();
		fld_dName.setBounds(583, 25, 126, 20);
		panel.add(fld_dName);
		fld_dName.setColumns(10);

		JLabel label_1 = new JLabel("T.C. No");
		label_1.setForeground(new Color(255, 102, 102));
		label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		label_1.setBounds(583, 56, 46, 14);
		panel.add(label_1);

		fld_dTcno = new JTextField();
		fld_dTcno.setBounds(583, 70, 126, 20);
		panel.add(fld_dTcno);
		fld_dTcno.setColumns(10);

		JLabel label_2 = new JLabel("Şifre");
		label_2.setForeground(new Color(255, 102, 102));
		label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		label_2.setBounds(583, 101, 35, 14);
		panel.add(label_2);

		fld_dPass = new JTextField();
		fld_dPass.setBounds(583, 115, 126, 20);
		panel.add(fld_dPass);
		fld_dPass.setColumns(10);

		JButton btn_addDoctor = new JButton("Ekle");
		btn_addDoctor.setBackground(new Color(255, 102, 102));
		btn_addDoctor.setForeground(new Color(255, 255, 255));
		btn_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dName.getText().length() == 0 || fld_dTcno.getText().length() == 0
						|| fld_dTcno.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = bashekim.addDoctor(fld_dTcno.getText(), fld_dPass.getText(),
								fld_dName.getText(), null);
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btn_addDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addDoctor.setBounds(583, 146, 126, 23);
		panel.add(btn_addDoctor);

		JLabel label_3 = new JLabel("Kullanıcı ID");
		label_3.setForeground(new Color(255, 102, 102));
		label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		label_3.setBounds(583, 203, 72, 14);
		panel.add(label_3);

		fld_doctorID = new JTextField();
		fld_doctorID.setBackground(Color.PINK);
		fld_doctorID.setBounds(583, 218, 126, 20);
		panel.add(fld_doctorID);
		fld_doctorID.setColumns(10);

		JButton btn_delDoctor = new JButton("Sil");
		btn_delDoctor.setBackground(new Color(255, 102, 102));
		btn_delDoctor.setForeground(new Color(255, 255, 255));
		btn_delDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorID.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz.");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean control = bashekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								updateDoctorModel();
								fld_doctorID.setText(null);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_delDoctor.setBounds(583, 249, 126, 23);
		panel.add(btn_delDoctor);

		JScrollPane w_scrolldoctor = new JScrollPane();
		w_scrolldoctor.setBounds(10, 11, 563, 299);
		panel.add(w_scrolldoctor);

		table_doctor = new JTable(doctorModel);
		w_scrolldoctor.setViewportView(table_doctor);
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() { // getselectionmodel methodu bisey degistiginde yapılacakları yazdık.

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString()); // settext icindeki veriyi degismemizi saglıyor.tabledoctor getselectedrow secili satırın indexini nesnede gosteriyor.
				} catch (Exception ex) {

				}
			}
		});

		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoctor(selectID, selectTcno, selectPass, selectName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 261, 299);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() { // sag tıkta guncelleme islemi icin updatemenu nun addactionlistenerına baktık.

			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getfetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) { // frame kapandıgında yap.
						try {
							updateClinicModel(); // guncelleme islemi.sql islemleri var icinde nerde tanımladık bilmiyom
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint(); // Point sınıfından point adında bi nesne ürettik.e bize veri tasıyor e.getpoint ile tıklanan koordinatları aldık.
				int selectedRow = table_clinic.rowAtPoint(point); // rowatpoint icine point alıyor(tıklanan andaki koordinat)
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);

			}
		});
		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblPoliklinikAd = new JLabel("Poliklinik Adı");
		lblPoliklinikAd.setForeground(new Color(255, 102, 102));
		lblPoliklinikAd.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblPoliklinikAd.setBounds(281, 11, 96, 14);
		w_clinic.add(lblPoliklinikAd);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(281, 33, 167, 23);
		w_clinic.add(fld_clinicName);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(458, 11, 261, 299);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.setBackground(new Color(255, 102, 102));
		btn_addClinic.setForeground(new Color(255, 255, 255));
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btn_addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addClinic.setBounds(281, 67, 167, 34);
		w_clinic.add(btn_addClinic);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(281, 218, 167, 34);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(
					new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName())); //getdoctorlist methodu ile db deki doktorları comboboxa aktardık.Item sınıfı sayesinde hem ıd hem name aktardık.

		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + ":" + item.getValue()); // id ve name leri yazdırdık ekrana gormek istedgmiz icin. item sınıfndan yararlandık key id yi value name i temsil etti.
		});
		w_clinic.add(select_doctor);

		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.setBackground(new Color(255, 102, 102));
		btn_addWorker.setForeground(new Color(255, 255, 255));
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();// secilen bi row var mı
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString(); // secilen poliklinigin ıd sini alıyoruz.
					int selClinicID = Integer.parseInt(selClinic); // int bi degiskene atadık verimizi.
					Item doctorItem = (Item) select_doctor.getSelectedItem(); // yeni bir item olusturduk. select_doctor cb ının getselected itemini getir.doctorItem secili olan user_id(db) yi temsil ediyor.yani secili id (user_id kolonundan)yi doctorIteme aktardık.aslımda bir nesneye döndürdük.alt satırda da keyini istiyoruz.
					try {
						boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID); // key ıd value name.(ıtem.java)
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
						    clearModel.setRowCount(0);
						    for(int i=0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++ ) { 
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
						} else
							Helper.showMsg("error");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz.");
				}
			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addWorker.setBounds(281, 261, 167, 34);
		w_clinic.add(btn_addWorker);
		
		JLabel lblPoliklinikAd_1 = new JLabel("Poliklinik Adı");
		lblPoliklinikAd_1.setForeground(new Color(255, 102, 102));
		lblPoliklinikAd_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblPoliklinikAd_1.setBounds(281, 123, 96, 14);
		w_clinic.add(lblPoliklinikAd_1);
		
		JButton btn_workerSelect = new JButton("Seç");
		btn_workerSelect.setBackground(new Color(255, 102, 102));
		btn_workerSelect.setForeground(new Color(255, 255, 255));
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();   // 1.tabloda secili satırı selrow degiskenine atadık
				if(selRow >=0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString(); 
					int selClinicID = Integer.parseInt(selClinic);
				    DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
				    clearModel.setRowCount(0);
				    try {
						for(int i=0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++ ) {   // bashekim.java da getclicindoctorlist methodunu cagırdık
							workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    table_worker.setModel(workerModel);
				    
				} else Helper.showMsg("Lütfen bir poliklinik seçiniz.");
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_workerSelect.setBounds(281, 148, 167, 34);
		w_clinic.add(btn_workerSelect);

	}

	public void updateDoctorModel() throws SQLException { // method olusturduk tabloya doktor ekledigimizde guncellenmesi icin.
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel(); // table_doctor tablosunun modelini getir. ve bunu clearmodel isimli bi yere tanımla
		clearModel.setRowCount(0); // clearmodelin tum rowları method calısınca silinsin.
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
