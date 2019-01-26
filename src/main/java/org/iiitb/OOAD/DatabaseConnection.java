package org.iiitb.OOAD;

import java.sql.*;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseConnection{
	Statement stmt;
	ResultSet rs;
	Connection conn=null;
	String query=null;
	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Found");
		}

		catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found: " + e);
		}
		String user = "root";
		String password = "1234";
        String URL="jdbc:mysql://localhost:3306/flipkart";
		try {
			conn = DriverManager.getConnection(URL,user,password);
			System.out.println("Successfully Connected to Database");
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}

	}	

	public String authenticateUser(String user_name) throws JSONException {

		java.sql.PreparedStatement preparedStatement = null;
		JSONObject user = new JSONObject();
		try {
			query = "select * from userTable where name=?";
	//		System.out.println(user_name);
		//	System.out.println(query);
			preparedStatement = conn.prepareStatement(query);
			System.out.println(user_name);
			preparedStatement.setString(1, user_name);
			System.out.println(user_name);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				user.put("result", "success");
				user.put("user_id", rs.getInt("user_id"));
				user.put("user_name", rs.getString("name"));
				user.put("dob", rs.getString("dateOfBirth"));
				user.put("pic_location", rs.getString("pic"));
			}
			else {
				user.put("result", "fail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user.toString();

	}
	
	public String getItemDetails(int itemid) throws JSONException {

		java.sql.PreparedStatement preparedStatement = null;
		JSONObject item = new JSONObject();
		try {
			query = "select * from itemTable where id=?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, itemid);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				item.put("result", "success");
				item.put("item_id", rs.getInt("item_id"));
				item.put("item_name", rs.getString("description"));
				item.put("expiry_date", rs.getString("expiryDate"));
				item.put("price", rs.getInt("price"));
				//item.put("pic_location", rs.getString("pic_location"));
			}
			else {
				item.put("result", "fail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item.toString();

	}
/*public static void main(String[] args)
{
	DatabaseConnection m=new DatabaseConnection();
	m.getEmployeeNames();
}*/
}
