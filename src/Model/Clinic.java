package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBconnection;

public class Clinic { // clinic tablosuyla ilgili db islemleri icin olusturdugum class.

	private int id;
	private String name;

	DBconnection conn = new DBconnection(); // database sınıfnı dahil ettik.
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Clinic() {
	}

	public Clinic(int id, String name) { // constructerları olusturduk
		super();
		this.id = id;
		this.name = name;
	}

	public ArrayList<Clinic> getList() throws SQLException { // bu method ile sistemdeki tum klinikleri cekmis oluruz.
		ArrayList<Clinic> list = new ArrayList<>();
		Clinic obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");
			while (rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list; 
		}
	
	public Clinic getfetch(int id) { //getfetch tek bir deger döndürüyor.
		
		Connection con = conn.connDb();
		Clinic c = new Clinic();
		try {
			st=con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id =" + id);
			while(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	
		public boolean addClinic(String name) throws SQLException {
			
			String query = "INSERT INTO clinic" + "(name) VALUES" + "(?)";
			boolean key = false;
			Connection con = conn.connDb();
			try {
			st = con.createStatement();
			preparedStatement =con.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.executeUpdate();
			key = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(key)
				return true;
			else 
				return false;
		
	}
		
		public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
			ArrayList<User> list = new ArrayList<>();
			User obj;
			try {
				Connection con = conn.connDb();
				st = con.createStatement();
				rs = st.executeQuery("SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id ="+ clinic_id);// worker ve user ın kısaltmalarını belirledik. w ve u. joın ile worker ve user tablolarını birlestirdik.worker tablosundaki userid ile user tablosundaki id esit olanları getir.
				while (rs.next()) {
					obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.password"), rs.getString("u.name"),
							rs.getString("type"));
					list.add(obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		
		public boolean deleteClinic(int id) throws SQLException {
			
			String query = "DELETE FROM clinic WHERE id = ?";
			boolean key = false;
			Connection con = conn.connDb();
			try {
			st = con.createStatement();
			preparedStatement =con.prepareStatement(query);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			key = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(key)
				return true;
			else 
				return false;
	}
		
		 public boolean updateClinic(int id,String name) throws SQLException {
				
			   String query = "UPDATE clinic SET name = ? WHERE id=?";
		   	   boolean key = false;
		   	   Connection con = conn.connDb();
			   try {
		    	st = con.createStatement();
			    preparedStatement =con.prepareStatement(query);
			    preparedStatement.setString(1,name);
			    preparedStatement.setInt(2,id);
			    preparedStatement.executeUpdate();
			    key = true;
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			
		    	if(key)
			    	return true;
			    else 
			    	return false;
		    }

	public int getId() { // setter ve getterlari olustrduk source kısmından.
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
