package Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager
{
	//ประกาศตป.ที่ไว้ใช้ในการ connect
		Connection con;
		PreparedStatement pst;
		ResultSet rs;//ถ้ามีการ select ก็ต้องมีตัว Resultset	 
			public void Connect() 
			{
				try 
				{  //jdbc:mysql://"+GlobalData.DATABASE_LOCATION+":"+GlobalData.DATABASE_PORT+"/"+GlobalData.DATABASE_DATABASE_NAME
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cafe","root","");
				}
				catch (ClassNotFoundException e)
				{
					
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			public UserManager() {
//		        Connect(); // เชื่อมต่อฐานข้อมูลเมื่อสร้างอ็อบเจกต์
//		    }
			public boolean CheckUserLogin(String Loginusername,String Loginpassword) 
			{   //Got an exception! 
				//Cannot invoke "java.sql.Connection.prepareStatement(String)" because "this.con" is null
				//คือยังไม่ได้เชื่อมต่อฐานข้อมูล
				 Connect();
				 System.out.println("connected");
				UserDB_Current user = new UserDB_Current();
				try
			    {	
					
				  pst = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ? ");
				  pst.setString(1,Loginusername);
				  pst.setString(2,Loginpassword);
				  rs = pst.executeQuery();
			      while (rs.next())
			      {  
			    	user.user_id = rs.getInt(1);
			    	user.username =rs.getString(2);
			    	user.password =rs.getString(3);
			    	user.status =rs.getString(4);
			    	return true;
			      } 
			      pst.close();
			    }
			    catch (Exception e)
			    {
			      System.err.println("Got an exception! ");
			      System.err.println(e.getMessage());
			    }
				return false;
			}
			
			public ArrayList<UserDB> getAllUser() 
			{   //Got an exception! 
				//Cannot invoke "java.sql.Connection.prepareStatement(String)" because "this.con" is null
				//คือยังไม่ได้เชื่อมต่อฐานข้อมูล
				Connect();
				
				ArrayList<UserDB> list =new ArrayList<UserDB>();
				try
			    {	
				  pst = con.prepareStatement("SELECT * FROM users");
				  rs = pst.executeQuery();
			      while (rs.next())
			      {  
			    	int user_id = rs.getInt(1);//จะใส่เลข 1 เเทนก็ได้
			    	String userame = rs.getString(2);
			    	String password = rs.getString(3);
			        String status = rs.getString(4);
			        
			        
			        UserDB cc = new UserDB(user_id, userame, password, status);//ตอนนี้เราได้ customer คนใหม่เเล้ว
			        list.add(cc);//เเล้วเราก็เอา customer add ลง list
			        for (UserDB c : list)//เอาไว้เช็คว่า img เป็น null ไหม
				        {
				            System.out.println("User ID: " + c.user_id + ", username: " + c.username);
				        }
			      }
			      pst.close();
			    }
			    catch (Exception e)
			    {
			      System.err.println("Got an exception! ");
			      System.err.println(e.getMessage());
			    }
				return list;//มันก็จะรีเทินออกไปเเต่ตอนนี้ยังไม่ได้เชื่อมต่อกับ db
			}
			
			
		public int AddUser(String xusername
				,String xpassword,String xstatus)
		{
			Connect();
			//ทำการเตรียมสถานะ คำสั่งที่เราจะทำ
			//ตัวไหนที่ไม่ได้จะกรอกให้มันเปลี่ยนตามที่เรากรอกเเต่ SQL มันจะรันให้เองไม่ต้องใส่มา เช่น id เเละ time stamp
			try 
			{
				pst = con.prepareStatement("INSERT INTO users (username,password,status)VALUE(?,?,?)");
				pst.setString(1, xusername);
				pst.setString(2, xpassword);
				pst.setString(3, xstatus);
				//ให้ k เก็บค่าว่า insert ไปเเล้วจริงๆ คือ return 1
				int k = pst.executeUpdate();
				return k;
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0; //ถ้าไม่ได้ insert จะ return 0
		}
			
		public int EditUser(String xusername
						   ,String xpassword,String xstatus,int xuser_id)
		{
			Connect();
			try 
			{
				pst = con.prepareStatement("UPDATE users SET username = ?,password = ?,status = ? WHERE user_id =?");
				pst.setString(1, xusername);
				pst.setString(2, xpassword);
				pst.setString(3, xstatus);
				pst.setInt(4,xuser_id);
				//ให้ k เก็บค่าว่า insert ไปเเล้วจริงๆ คือ return 1
				int k = pst.executeUpdate();
				return k;
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0; //ถ้าไม่ได้ insert จะ return 0
		}
			 
		public int DeleteUser(int xuser_id)
		{
			Connect();
			try 
			{
				pst = con.prepareStatement("DELETE FROM users WHERE user_id = ?");
				pst.setInt(1, xuser_id);
			
				int k = pst.executeUpdate();
				return k;
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			return 0; //ถ้าไม่ได้ insert จะ return 0
		}
			 
	}
		
			


