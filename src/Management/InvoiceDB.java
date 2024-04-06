package Management;

public class InvoiceDB {
	public int invoice_id;
	public int user_id;
	public double total_price;
	public String time_print_invoice;
	
	public InvoiceDB() {
			}
	public InvoiceDB( int xinvoice_id, int xuser_id, double xtotal_price, String xtime_print_invoice) 
	{
		 invoice_id =xinvoice_id ;
		 user_id = xuser_id;
		 total_price = xtotal_price;
		 time_print_invoice = xtime_print_invoice;
	}

}
