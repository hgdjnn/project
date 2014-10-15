package action;
import java.sql.*;


public class DaoCon {
	public Connection GetConn() throws Exception{
		//Êý¾Ý¿â
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/bookdb?characterEncodeing="+"utf-8";
		String user = "root";
		String password = "2623628929";
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}
}
