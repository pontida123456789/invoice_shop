package Management;

public class Invoice_detailDB {
	public int invoice_id;
	public int product_id;
	public int Qty	;
	public double price_per_list;
	public String time_print_invoice;
	public Invoice_detailDB() {
		
	}
	public Invoice_detailDB(int xinvoice_id, int xproduct_id, int xQty, double xprice_per_list,String xtime_print_invoice) 
	{
		invoice_id = xinvoice_id;
		product_id =xproduct_id;
		Qty	=xQty;
		price_per_list=xprice_per_list;
		time_print_invoice = xtime_print_invoice;
	}
}
