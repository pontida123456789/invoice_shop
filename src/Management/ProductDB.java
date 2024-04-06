package Management;

public class ProductDB 
{		
	public int product_id;
	public String product_name;
	public double product_price;
	public int product_capable;
	public String product_Image;
	public String username;
	public String time;

	public ProductDB() 
	{
		
	}
	
	public ProductDB(int xproduct_id,String xproduct_name
			,double xproduct_price,int xproduct_capable,
			String xproduct_Image,String xusername,String xtime	) 
	{
		
		product_id = xproduct_id;
		product_name=xproduct_name;
		product_price=xproduct_price;
		product_capable=xproduct_capable;
		product_Image=xproduct_Image;
		username=xusername;
		time=xtime;
	}
}
