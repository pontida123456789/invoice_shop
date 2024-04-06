package Management;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.* ;
import javax.imageio.ImageIO;



public class ProductManager 
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
		public ArrayList<ProductDB> getAllProduct() 
		{   
			Connect();
			
			ArrayList<ProductDB> list =new ArrayList<ProductDB>();
			try
		    {	
			  pst = con.prepareStatement("SELECT * FROM products");
			  rs = pst.executeQuery();
		      while (rs.next())
		      {  
		    	int id = rs.getInt("product_id");//จะใส่เลข 1 เเทนก็ได้
		        String pName = rs.getString(2);
		        double pPrice = rs.getDouble(3);
		        int pcapa = rs.getInt(4);
		        String pImage = rs.getString(5);
		        String username = rs.getString(6);
		        String pTime = rs.getString(7);
		        
		        ProductDB cc = new ProductDB(id, pName, pPrice, pcapa,pImage,username,pTime);//ตอนนี้เราได้ customer คนใหม่เเล้ว
		        list.add(cc);//เเล้วเราก็เอา customer add ลง list
		        for (ProductDB c : list)//เอาไว้เช็คว่า img เป็น null ไหม
			        {
			            System.out.println("Product ID: " + c.product_id + ", Image: " + c.product_Image);
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

		public ArrayList<ProductDB> SearchProduct(String xProductname) 
		{   
			Connect();
			
			ArrayList<ProductDB> list =new ArrayList<ProductDB>();
			try
		    {	
			  pst = con.prepareStatement("SELECT * FROM products WHERE product_name LIKE '"+xProductname+"'");
			  rs = pst.executeQuery();
		      while (rs.next())
		      {  
		    	int id = rs.getInt("product_id");//จะใส่เลข 1 เเทนก็ได้
		        String pName = rs.getString(2);
		        double pPrice = rs.getDouble(3);
		        int pcapa = rs.getInt(4);
		        String pImage = rs.getString(5);
		        String username = rs.getString(6);
		        String pTime = rs.getString(7);
		        
		        ProductDB cc = new ProductDB(id, pName, pPrice, pcapa,pImage,username,pTime);//ตอนนี้เราได้ customer คนใหม่เเล้ว
		        list.add(cc);//เเล้วเราก็เอา customer add ลง list
		        for (ProductDB c : list)//เอาไว้เช็คว่า img เป็น null ไหม
			        {
			            System.out.println("Product ID: " + c.product_id + ", Image: " + c.product_Image);
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
	public int AddProduct(String xproduct_name
			,double xproduct_price,int xproduct_capable,
			String xproduct_Image,String xusername)
	{
		Connect();
		//ทำการเตรียมสถานะ คำสั่งที่เราจะทำ
		//ตัวไหนที่ไม่ได้จะกรอกให้มันเปลี่ยนตามที่เรากรอกเเต่ SQL มันจะรันให้เองไม่ต้องใส่มา เช่น id เเละ time stamp
		try 
		{
			pst = con.prepareStatement("INSERT INTO products (product_name,product_price,product_capable,product_Image,username)VALUE(?,?,?,?,?)");
			pst.setString(1, xproduct_name);
			pst.setDouble(2, xproduct_price);
			pst.setInt(3, xproduct_capable);
			pst.setString(4, xproduct_Image);
			pst.setString(5, xusername);
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
		
	
	public int EditProduct(String xproduct_name
			,double xproduct_price,int xproduct_capable,
			String xproduct_Image,String xusername,int xproduct_id)
	{
		Connect();
		try 
		{
			pst = con.prepareStatement("UPDATE Products SET product_name = ?,product_price = ?,product_capable = ?,product_Image = ?,username = ? WHERE product_id =?");
			pst.setString(1, xproduct_name);
			pst.setDouble(2, xproduct_price);
			pst.setInt(3, xproduct_capable);
			pst.setString(4, xproduct_Image);
			pst.setString(5, xusername);
			pst.setInt(6,xproduct_id);
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
		 
	public int DeleteProduct(int Product_id)
	{
		Connect();
		try 
		{
			pst = con.prepareStatement("DELETE FROM products WHERE product_id = ?");
			pst.setInt(1, Product_id);
		
			int k = pst.executeUpdate();
			return k;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; //ถ้าไม่ได้ insert จะ return 0
	}
	
	public int UpdateQtyProductAfterPrint(int xproduct_remain,int xproduct_id)
	{
		Connect();
		try 
		{
			pst = con.prepareStatement("UPDATE Products SET product_capable = ? WHERE product_id =?");
			pst.setInt(1, xproduct_remain);		
			pst.setInt(2,xproduct_id);
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
}
