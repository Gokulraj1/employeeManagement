package com.xadmin.usermanagement.dao;
import com.xadmin.usermanagement.bean.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDao {

	 private String jdbcURL="jdbc:mysql://localhost:3306/userdb";
	    private String jdbcUsername="root";
	    private String jdbcPassword ="Gokulraj@1";
	    private String jdbcDriver ="com.mysql.jdbc.Driver";
	    
	    private static final String INSERT_USERS_SQL ="INSERT INTO users ( name,email,country) VALUES (?,?,?)";      
	    private static final String Select_users_By_Id= "select name,email,country from users where id=?";
	    private static final String Select_All_Users="select * from users ";
	    private static final String Delete_Users_Sql ="delete from users where id=?;";
	    private static final String Update_Users_Sql ="update users set name =?,email=?,country=? where id=?;";
		public UserDao() {
			
		}
		
		protected Connection getConnection() {
			Connection connection=null;
			try {
				Class.forName(jdbcDriver);
				connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return connection;	
		}
		
// insert user
		
		
		public void insertUser(User user){
			System.out.println(INSERT_USERS_SQL);
			try {Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getEmail());
				preparedStatement.setString(3, user.getCountry());
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();
				
				
			} catch (SQLException e) {
				printSQLException(e);
			}
		}
		
		
//select user by id

		public User selectUser(int id){
			User user = null;
			try {Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(Select_users_By_Id);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User (name,email,country);
				
			}
				
			} catch (SQLException e) {
				printSQLException(e);
			}
			
			return user;
		}
	
		
//select all users
		
		public List<User> selectAllUsers() {
			List<User> users = new ArrayList<User>();
			try {Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(Select_All_Users);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				int id =rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add (new User (id,name,email,country));
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		
			return users;
		}
		
		
//update users
		
		public boolean updateUser(User user){
			boolean rowUpdated = false ;
			try {Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(Update_Users_Sql);
			System.out.println("updated User:" +statement);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			 rowUpdated = statement.executeUpdate ()> 0;
			}
			catch (SQLException e) {
				printSQLException(e);
			}
			return rowUpdated;
		}
		
		
//delete users
		
		public boolean deleteUser(int id){
			boolean rowDeleted = false;
			try {Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(Delete_Users_Sql);
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
			
			}catch (SQLException e) {
				printSQLException(e);
			}
			return rowDeleted;
		}
		
// SQLException
		
		private void printSQLException(SQLException ex) {
			for (Throwable e : ex) {
				if (e instanceof SQLException) {
					e.printStackTrace(System.err);
				System.err.println("SQLState :" +((SQLException) e).getSQLState());
				System.err.println("Error Code :" +((SQLException) e).getErrorCode());
				System.err.println("Message :" + e.getMessage());
				Throwable t = ex.getCause();
				while(t != null){
					System.out.println("Cause:" +t);
					t = t.getCause();
					
				}
				}
				
				
			}
			
		}
		
		
}


