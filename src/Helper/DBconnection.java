package Helper; // bu pakette database baglantısı sagladık.
import java.sql.*; // * hepsini import eder.

public class DBconnection {
	
    Connection c = null; // connection sınıfından c adında bir connection olusturduk. bu sınıf sayesinde veritabanına baglantı saglarız.
    
    public DBconnection() {} // bos bi constructer olusturduk.burdan bir nesne ürettiğimizde içindeki bir method sayesinde database baglantımızı saglarız. 
    
    public Connection connDb() { //bu methodun türü connection oldu.database e bagla fonksiyonu.
    	try {
			this.c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/hospital?user=root&password="); //içerdeki connection'ı 
			return c;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	return c;
    }
}
