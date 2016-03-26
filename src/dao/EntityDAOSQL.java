package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import framework.Entity;

public class EntityDAOSQL implements EntityDAO
{
	static final String databasePath="jdbc:mysql://localhost:3306/testdb";
	static final String USER = "testuser";
	static final String PASSWORD = "password";
	
	public static final String C_ID="id";
	public static final String C_NAME="name";
	public static final String C_DESCRIPTION="description";
	public static final String C_CATEGORY="category";
	public static final String C_PRICE="price";
	public static final String C_QUANTITY="quantity";
	
	private static Connection con=null; 
	
	public EntityDAOSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Entity> getAll() 
	{
		List<Entity> entries=new ArrayList<Entity>();
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				 PreparedStatement stmt = con
						 .prepareStatement("SELECT * FROM entities");
		         ResultSet result = stmt.executeQuery();
		         while(result.next())
		         {
		        	  Entity entity;
		        	  int id = result.getInt(C_ID);
		        	  String name = result.getString(C_NAME);
		        	  String description = result.getString(C_DESCRIPTION);
		        	  double price = result.getDouble(C_PRICE);
		        	  int quantity = result.getInt(C_QUANTITY);
		        	  String category = result.getString(C_CATEGORY);
		        	  
		        	  entity=new Entity();
		        	  entity.setId(id);
		        	  entity.setName(name);
		        	  entity.setDescription(description);
		        	  entity.setPrice(price);
		        	  entity.setQuantity(quantity);
		        	  entity.setCategory(category);
		        	  entries.add(entity);
		         }
		         con.close();
		         return entries;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getCategories() {
		List<String> categories=new ArrayList<String>();
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				 PreparedStatement stmt = con
						 .prepareStatement("SELECT distinct category FROM entities");
		         ResultSet result = stmt.executeQuery();
		         while(result.next())
		         {
		        	  Entity entity;
		        	  String category = result.getString(C_CATEGORY);
		        	 
		        	  categories.add(category);
		         }
		         con.close();
		         return categories;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Entity> getAllForCategory(String category) {
		List<Entity> entries=new ArrayList<Entity>();
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				 PreparedStatement stmt = con
						 .prepareStatement("SELECT * FROM entities where category="+category);
		         ResultSet result = stmt.executeQuery();
		         while(result.next())
		         {
		        	  Entity entity;
		        	  int id = result.getInt(C_ID);
		        	  String name = result.getString(C_NAME);
		        	  String description = result.getString(C_DESCRIPTION);
		        	  double price = result.getDouble(C_PRICE);
		        	  int quantity = result.getInt(C_QUANTITY);
		        	  
		        	  entity=new Entity();
		        	  entity.setId(id);
		        	  entity.setName(name);
		        	  entity.setDescription(description);
		        	  entity.setPrice(price);
		        	  entity.setQuantity(quantity);
		        	  entity.setCategory(category);
		        	  entries.add(entity);
		         }
		         con.close();
		         return entries;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean add(Entity entity) {
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				String sql = "insert into entities("+C_NAME+","+C_DESCRIPTION+","+C_CATEGORY+","+C_PRICE+","+C_QUANTITY+") values ("
						+ "\'" + entity.getName() + "\',\'" + entity.getDescription() + "\', '"+ entity.getCategory() + "\'," + 
						entity.getPrice() + ", " + entity.getQuantity() + ")";
				System.out.println("Executing SQL: " + sql);
				
				Statement stmt = con.createStatement();
				
		        int result = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		         
				if(result > 0) {
					ResultSet rs = stmt.getGeneratedKeys();
					if (rs.next()){
					    int id = rs.getInt(1);
					    entity.setId(id);
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

	@Override
	public boolean update(Entity entity) {
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				String sql = "update entities set "+C_NAME+"=\'"+ entity.getName() +"\', "+C_DESCRIPTION+"=\'"+ entity.getDescription() +"\', "+C_CATEGORY+"=\'" + entity.getCategory() + "\', "+C_PRICE+"=" + entity.getPrice() + ", "+C_QUANTITY+"=" + entity.getQuantity() + " where "+C_ID+"="+entity.getId() + ";";
				System.out.println(sql);
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
		         
		        con.close();
		        return true;
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Entity e) {
		try {
			con=DriverManager.getConnection(databasePath, USER, PASSWORD);
			if(con==null)
				System.out.println("Null connection.");
			else
			{
				 PreparedStatement stmt = con.prepareStatement("delete from  entities where id=" + e.getId());
		         stmt.executeQuery();
		         
		         con.close();
		         return true;
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return false;
	}

}
