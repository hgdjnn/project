package action;

import java.sql.Connection;
import java.sql.Statement;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DetelBook extends ActionSupport{
	private String title;
	private int i =0;
	public int getI() {
		return i;
	}

	public int det(int m)
	{
		return m=4;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public char detel(int i)
	{
		char aut=(char)(i/256);
		return  aut;
	}

	public void DetBook() throws Exception {
		DaoCon dao = new DaoCon();
		Connection con = dao.GetConn();
		Statement stmt = con.createStatement() ;
		String sql = "delete from book where title = '"+title+"'";
		stmt.executeUpdate(sql) ;
		setI(9);
		stmt.close();
        con.close();
	}
	
	public void setI(int i) {
		this.i = i;
	}

	public String getTitle() {
		return title;
	}
	
	
	public String execute(){
		try {
			DetBook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
