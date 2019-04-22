package database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import info.GroceryInfo;
import info.Info;
import info.RecipeInfo;

public class GroceryDataManager extends DataManager {
	
	public void addToList(GroceryInfo ingredient) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // get driver for database
			conn = DriverManager.getConnection(JDBC_CONNECTION);
			ps = conn.prepareStatement("INSERT INTO GroceryList(GroceryItem) VALUES(?);");
			ps.setString(1, ingredient.item);
			ps.executeUpdate();
			System.out.println("Grocery added.");
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
	}
	
	public ArrayList<Info> loadGrocery() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Info> groceryList = new ArrayList<Info>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_CONNECTION);
	    	ps = conn.prepareStatement("SELECT * FROM GroceryList");
			rs = ps.executeQuery();
			System.out.println("Loading grocery list.");
			while(rs.next()) {				
				GroceryInfo groceryToAdd = new GroceryInfo(rs.getString("GroceryItem"));
				groceryList.add(groceryToAdd);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
		return groceryList;
	}
	
	public void removeFromList(String grocery) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_CONNECTION);
	    	ps = conn.prepareStatement("DELETE FROM GroceryList WHERE GroceryItem LIKE ?");
	    	ps.setString(1, grocery);
	    	ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
	}
}
