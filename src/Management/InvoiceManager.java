package Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceManager {

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
			
			public int UpInvoiceToDB(int user_id, double total_price)
			{
				Connect();
				//ทำการเตรียมสถานะ คำสั่งที่เราจะทำ
				//ตัวไหนที่ไม่ได้จะกรอกให้มันเปลี่ยนตามที่เรากรอกเเต่ SQL มันจะรันให้เองไม่ต้องใส่มา เช่น id เเละ time stamp
				try 
				{
					pst = con.prepareStatement("INSERT INTO invoice (user_id,total_price)VALUE(?,?)");
					pst.setInt(1, user_id);
					pst.setDouble(2, total_price);					
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
			
			public ArrayList<InvoiceDB> getAllInvoice() 
			{   //Got an exception! 
				//Cannot invoke "java.sql.Connection.prepareStatement(String)" because "this.con" is null
				//คือยังไม่ได้เชื่อมต่อฐานข้อมูล
				Connect();
				
				ArrayList<InvoiceDB> list =new ArrayList<InvoiceDB>();
				try
			    {	
				  pst = con.prepareStatement("SELECT * FROM invoice");
				  rs = pst.executeQuery();
			      while (rs.next())
			      {  
			    	int invoice_id = rs.getInt(1);//จะใส่เลข 1 เเทนก็ได้
			      	int user_id = rs.getInt(2);
			      	double total_price = rs.getDouble(3);
			      	String time_print_invoice = rs.getString(4);
			        
			        
			      	InvoiceDB cc = new InvoiceDB(invoice_id,user_id,total_price,time_print_invoice);//ตอนนี้เราได้ customer คนใหม่เเล้ว
			        list.add(cc);//เเล้วเราก็เอา customer add ลง list
			        for (InvoiceDB c : list)//เอาไว้เช็คว่า img เป็น null ไหม
				        {
				            System.out.println("invoice_id: " + c.invoice_id + ", total_price: " + c.total_price);
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
				
			public int UpInvoiceDetailToDB(int invoice_id, int product_id, int Qty	, double price_per_list)
			{
				Connect();
				//ทำการเตรียมสถานะ คำสั่งที่เราจะทำ
				//ตัวไหนที่ไม่ได้จะกรอกให้มันเปลี่ยนตามที่เรากรอกเเต่ SQL มันจะรันให้เองไม่ต้องใส่มา เช่น id เเละ time stamp
				try 
				{
					pst = con.prepareStatement("INSERT INTO invoice_datail (invoice_id,product_id,Qty,price_per_list)VALUE(?,?,?,?)");
					pst.setInt(1, invoice_id);
					pst.setInt(2, product_id);
					pst.setInt(3, Qty);
					pst.setDouble(4, price_per_list);
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
			public ArrayList<Invoice_detailDB> getAllInvoice_detail(String intitialDay,String endDay) 
			{   //Got an exception! 
				//Cannot invoke "java.sql.Connection.prepareStatement(String)" because "this.con" is null
				//คือยังไม่ได้เชื่อมต่อฐานข้อมูล
				Connect();
				
				ArrayList<Invoice_detailDB> list =new ArrayList<Invoice_detailDB>();
				
				try
			    {	
				  pst = con.prepareStatement("SELECT invoice_datail.invoice_id,price_per_list,product_id,Qty,time_print_invoice\r\n"
				  		+ "FROM invoice_datail\r\n"
				  		+ "JOIN invoice\r\n"
				  		+ "ON invoice_datail.invoice_id = invoice.invoice_id\r\n"
				  		+ "WHERE time_print_invoice BETWEEN '"+intitialDay+" 00:00:00' AND '"+endDay+" 23:59:59'");
				  rs = pst.executeQuery();
			      while (rs.next())
			      {  
			    	int invoice_id = rs.getInt(1);//จะใส่เลข 1 เเทนก็ได้
			      	double price_per_list = rs.getInt(2);
			      	int product_id = rs.getInt(3);
			      	int Quantity = rs.getInt(4);
			      	String time_print_invoice = rs.getString(5);
			        
			        
			      	Invoice_detailDB cc = new Invoice_detailDB(invoice_id,product_id,Quantity, price_per_list,time_print_invoice);//ตอนนี้เราได้ customer คนใหม่เเล้ว
			        list.add(cc);//เเล้วเราก็เอา customer add ลง list
			        for (Invoice_detailDB c : list)//เอาไว้เช็คว่า img เป็น null ไหม
				        {
				            System.out.println("invoice_id: " + c.invoice_id );
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
}
