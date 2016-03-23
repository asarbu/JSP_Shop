package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import framework.Entity;
import framework.User;

public class UserDAOSQL implements UserDAO
{
	static final String databasePath="jdbc:mysql://localhost:3306/testdb";
	static final String USER = "testuser";
	static final String PASSWORD = "password";
	
	private static final String C_ID="id";
	private static final String C_NAME="name";
	private static final String C_USERNAME="username";
	private static final String C_PASSWORD="password";
	private static final String C_ADDRESS="address";
	
	private static Connection con=null; 
		
	public UserDAOSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public User get(String username) {
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				 PreparedStatement stmt = con
						 .prepareStatement("SELECT * FROM users where username=\'"+username+"\'");
		         ResultSet result = stmt.executeQuery();
		         if(result.next())
		         {
		        	  User user;
		        	  int id = result.getInt(C_ID);
		        	  String name = result.getString(C_NAME);
		        	  String address = result.getString(C_ADDRESS);
		        	  String password = result.getString(C_PASSWORD);
		        	  
		        	  user=new User();
		        	  user.setId(id);
		        	  user.setName(name);
		        	  user.setAddress(address);
		        	  user.setPassword(password);
		        	  return user;
		         }
		         con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User login(String username, String password) {
		User user=get(username);
		if(user != null && user.getPassword().equals(password))
			return user;
		return null;
	}

	@Override
	public boolean register(User user) {
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				String sql = "insert into users("+C_USERNAME+","+C_PASSWORD+","+C_NAME+","+C_ADDRESS+") values ("
						+ "\'" + user.getUsername() + "\',\'" + user.getPassword() + "\', '"+ user.getName() + "\',\'" + 
						user.getAddress() +"\')";
				System.out.println("Executing SQL: " + sql);
				
				Statement stmt = con.createStatement();
				
		       
	
		        int result = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		         
				if(result > 0) {
					ResultSet rs = stmt.getGeneratedKeys();
					if (rs.next()){
					    int id = rs.getInt(1);
					    user.setId(id);
					}
				}
				
		         con.close();
		         return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
