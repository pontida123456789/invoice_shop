package GUI_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Management.InvoiceDB;
import Management.InvoiceManager;
import Management.ProductDB;
import Management.ProductManager;
import Management.UserDB_Current;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JSeparator;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;
import java.text.DecimalFormat;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.applet.*;
import java.net.*;

public class main_Frame extends JFrame {
	//****btn.setEneble(true)คือทำให้ปุ่มยังกดได้ ถ้า false คือกดไม่ได้เเล้ว ไว้ใช้กับเรื่อง user****
//	if(QuantityList.get(i).getValue() instanceof Double && (Double)QuantityList.get(i).getValue() != 0.0) {
//	    // คำสั่งที่ต้องการให้ทำ
//	}เอาเก็บไว้ใช้ตอนจะดึงเลขจาก spinner มาลบกับในstock

	private static final long serialVersionUID = 1L;
	ArrayList<ProductDB>list;
	private JPanel panel;
//	สร้างเพื่อเอาไว้ดึงปุ่ม jspiner ออกมาจาก grid หลังจากสร้างมาเเล้ว
	ArrayList<JSpinner>QuantityListJspinner = new ArrayList<>();
	ArrayList<JLabel>ProductNameList = new ArrayList<>();
	ArrayList<JLabel>AddSpaceProductNameList = new ArrayList<>();
	ArrayList<JLabel>ProductPriceList = new ArrayList<>();
	ArrayList<Double>Sum = new ArrayList<>();
	private JLabel lblNewLabel_time;
	private JLabel lblNewLabel_date;
	private JTextField textField_VS;
	private JTextField textField_SubTotal;
	private JTextField textField_Total;
	private JTextArea textArea;
	private JButton btn_user;
	private JButton btn_stock;
	private double total[] = new double[1];
	private JTextField textField_SearchProduct;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					  UIManager.setLookAndFeel(
					            UIManager.getSystemLookAndFeelClassName());
					main_Frame frame = new main_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main_Frame()
	{
		setResizable(false);
		
		getContentPane().setBackground(new Color(100, 149, 237));   
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		setLocationRelativeTo(null);
		//สร้างjpanel ขึ้นมาขนาดเท่าเน้
		panel = new JPanel();
		panel.setBackground(SystemColor.window);
		panel.setForeground(SystemColor.desktop);
		panel.setBounds(200, 200, 900, 605);
		panel.setLayout(new GridLayout(0,3,0,0));
		
		LoadProduct();
		setTime();
		getContentPane().setLayout(null);
	
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(50, 50, 900, 605);
		getContentPane().add(scrollPane);
		
		JButton btn_preview = new JButton(MysetIconForJAR_File("/image/preview_icon.png"));
		btn_preview.setBounds(50, 676, 142, 41);
		btn_preview.setBackground(SystemColor.activeCaption);
		btn_preview.setForeground(Color.BLACK);
		btn_preview.setText("Preview");
		
		btn_preview.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_preview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				if(NoSelectItem())//ถ้าไม่มีการเลือก เป็นจริงไม่ต้องทำไร
				{
					//ถ้าไม่เลือกอะไรก็ให้ reset ไปด้วยเลย เผื่อมีคนมากดPreviewใบเสร็จค้างไว้ เเล้วมาลดจำนวนทีหลัง
					reset(); 
				}
				else
				{
					//ภ้าไม่จริงให้คำรวณ
					calculate();	
				}
				
			}
		});
		
		//ใส่ icon ให้กับปุ่ม ด้วยฟังชัน setIcon ที่สร้างไว้ปรับขนาด icon เเล้ว
		JButton btn_reset = new JButton(MysetIconForJAR_File("/image/refresh_icon.png"));
		btn_reset.setBounds(209, 677, 134, 41);
		btn_reset.setBackground(SystemColor.activeCaption);
		btn_reset.setText("Reset");
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				reset();
				panel.removeAll();
				panel.repaint();
				LoadProduct();
			}
		});
		btn_reset.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton btn_print = new JButton(MysetIconForJAR_File("/image/print_icon.jpeg"));
		btn_print.setBounds(360, 677, 134, 41);
		btn_print.setBackground(SystemColor.activeCaption);
		btn_print.setText("Print");
		btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NoSelectItem())
				{
					reset();
				}
				else
				{
					try 
					{   if(textArea.print())
						{	InvoiceManager IM = new InvoiceManager();
							IM.UpInvoiceToDB(UserDB_Current.user_id,total[0]);//กดปริ้นเสร็จปึ๊ปก็อัพใบเสร็จขึ้น DB
							int Number = 0;
							//เอาid ของ ใบเสร็จอันล่าสุดมา listInvoice.size()-1 ดึงนอกloop ก็ได้จะได้ไม่ต้องดึงซ้ำๆ
							InvoiceManager IM2 = new InvoiceManager();
							ArrayList<InvoiceDB>listInvoice = IM2.getAllInvoice();
							int Invoice_ID = listInvoice.get(listInvoice.size()-1).invoice_id;
							System.out.println("listInvoice.size()-1 "+(listInvoice.size()-1));
							System.out.println("Invoice_ID "+Invoice_ID);
							for(int i=0;i<QuantityListJspinner.size();i++)
							{  	//System.out.println(QuantityListJspinner.size()+"  "+Number);
								if(QuantityListJspinner.get(i).getValue() instanceof Double && (Double)QuantityListJspinner.get(i).getValue() != 0.0) 
								{   
									//หักลบจำนวนที่ซื้อ กับจำนวนที่มีใน stock จะได้จำนวนที่เหลือ คือ remain เเล้วค่อยอัพขึ้น db ทีละตัวๆเลยทำในลูป
									Number++;	
									int Qty = ((Double)QuantityListJspinner.get(i).getValue()).intValue() ;
									ProductDB P = list.get(i);
									int Product_capable = P.product_capable;
									int remain = Product_capable-Qty;
									ProductManager PM =new ProductManager();
									PM.UpdateQtyProductAfterPrint(remain, P.product_id);
									//System.out.println(QuantityListJspinner.size()+"  "+Number);
									
									//เพิ่ม invoice_detail ขึ้นไปใน db ที่เราอัพในloop เพราะต้องการรายการของที่ซื้อเเต่กล่องว่ากี่อันเเล้วadd ขึ้นไปทุกอัน เเละทำหลังจาก add invoice หลักเพราะเราจะได้ ดึง id_invoice ที่พึ่งอัพไปมาใช้ได้
									//ว่าในรายการใบเสร็จนั้นมีการซื้ออะไรบ้าง เอา list ที่ดึงมาเอาid ของใบเสร็จหลักตัวล่าสุดที่พึ่งอัพ ซึ่งก็คืออันเดียวกันกับ detail ที่กำลังจะอัพขึ้นไป ก็คือ list.size ของใบเสร็จหลัก -1 ก็จะได้ใบเสร็จหลักอันล่าสุดออกมา
									
									int Product_ID = P.product_id;
									//แปลงค่าจาก jspinner ให้เป็น int
									double value = (Double) QuantityListJspinner.get(i).getValue();
									int QtyInvoice = ((Integer) (int) value).intValue();
									double Price_per_list = QtyInvoice*P.product_price;
									IM2.UpInvoiceDetailToDB(Invoice_ID, Product_ID, QtyInvoice, Price_per_list);
									
								}
							}
							
						}
//					เพื่อให้ไฟล์เสียงเล่นในไฟล์ JAR ได้ ก็ต้อง getClass().getResource(path)เหมือนกับ ที่ทำกับรูปด้วยเพื่อให้ไฟล์ JAR เข้าถึงไฟล์ buildPath ในตัวมันเองด้วย
						playSound("/sound/mixkit-gold-coin-prize-1999.wav");
						LoadProduct();
					} 
					catch (PrinterException e1)
					{
						NoSelectItem();
						e1.printStackTrace();
					}
				}
			}
		});
		btn_print.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		btn_stock = new JButton(MysetIconForJAR_File("/image/stock_icon.png"));
		btn_stock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stock_Frame SF =  new Stock_Frame(main_Frame.this);
				SF.setVisible(true);
			}
		});
		btn_stock.setBounds(511, 677, 133, 41);
		btn_stock.setBackground(SystemColor.activeCaption);
		btn_stock.setText("Stock");
		btn_stock.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		btn_user = new JButton(MysetIconForJAR_File("/image/user_icon.jpeg"));
		btn_user.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_Frame UF =new User_Frame();
				UF.setVisible(true);
			}
		});
		btn_user.setBounds(665, 676, 133, 41);
		btn_user.setBackground(SystemColor.activeCaption);
		btn_user.setText("User");
		btn_user.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton btn_Exit = new JButton(MysetIconForJAR_File("/image/Exit_icon.png"));
		btn_Exit.setBounds(816, 676, 134, 41);
		btn_Exit.setBackground(SystemColor.activeCaption);
		btn_Exit.setText("Exit");
		btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(main_Frame.this, "Do you want to leave form this program?" ,"Confirm to exit", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) 
				{
					System.exit(0);
				} 
				else 
				{
				    
				}
				
			}
		});
		btn_Exit.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblNewLabel = new JLabel("MENU");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(442, 10, 90, 30);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(975, 0, 401, 766);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(173, 216, 230));
		panel_2.setForeground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1 = new JLabel("VAT & Service Charge");
		lblNewLabel_1.setBounds(14, 660, 232, 30);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sub Total");
		lblNewLabel_1_1.setBounds(14, 695, 105, 22);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblNewLabel_1_2 = new JLabel("Total");
		lblNewLabel_1_2.setBounds(14, 728, 105, 17);
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		textField_VS = new JTextField();
		textField_VS.setBounds(202, 659, 189, 27);
		textField_VS.setHorizontalAlignment(SwingConstants.CENTER);
		textField_VS.setText("0.0");
		textField_VS.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_VS.setColumns(10);
		
		textField_SubTotal = new JTextField();
		textField_SubTotal.setBounds(202, 691, 189, 27);
		textField_SubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		textField_SubTotal.setText("0.0");
		textField_SubTotal.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_SubTotal.setColumns(10);
		
		textField_Total = new JTextField();
		textField_Total.setBounds(202, 723, 189, 27);
		textField_Total.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Total.setText("0.0");
		textField_Total.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_Total.setColumns(10);
		
		//create textArea 
		textArea = new JTextArea();
		textArea.setBounds(1, 1, 226, 601); 
		// Create a JScrollPane and add the JTextArea to it
		// Create a JScrollPane and add the JTextArea to it
		JScrollPane scrollPanefortextArea = new JScrollPane(textArea);
		scrollPanefortextArea.setBounds(10, 50, 381, 603);
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel);
		getContentPane().add(scrollPane);
		getContentPane().add(btn_preview);
		getContentPane().add(btn_reset);
		getContentPane().add(btn_print);
		getContentPane().add(btn_stock);
		getContentPane().add(btn_user);
		getContentPane().add(btn_Exit);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		panel_2.add(scrollPanefortextArea);
		panel_2.add(lblNewLabel_1);
		panel_2.add(textField_VS);
		panel_2.add(lblNewLabel_1_1);
		panel_2.add(textField_SubTotal);
		panel_2.add(lblNewLabel_1_2);
		panel_2.add(textField_Total);
		
		textField_SearchProduct = new JTextField();
		textField_SearchProduct.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {//การกด enter ในกล่อง search
					 
					 LoadProductSearch(textField_SearchProduct.getText());
						if(textField_SearchProduct.getText().isEmpty())
						{
							LoadProduct();
						}
	                }
			}
		});
		textField_SearchProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_SearchProduct.setBounds(10, 19, 263, 23);
		panel_2.add(textField_SearchProduct);
		textField_SearchProduct.setColumns(10);
		
		JButton btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadProductSearch(textField_SearchProduct.getText());
				if(textField_SearchProduct.getText().isEmpty())
				{
					LoadProduct();
				}
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSearch.setBounds(289, 18, 102, 24);
		panel_2.add(btnSearch);
		
		lblNewLabel_time = new JLabel("");
		lblNewLabel_time.setBounds(665, 20, 130, 20);
		getContentPane().add(lblNewLabel_time);
		lblNewLabel_time.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblNewLabel_date = new JLabel("");
		lblNewLabel_date.setBounds(777, 20, 171, 20);
		getContentPane().add(lblNewLabel_date);
		lblNewLabel_date.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		//เช็คว่าสิทธ์การเข้าถึงว่าจะให้เข้าถึงปุ่ม stock กับ user ได้ไหม
		if(userPrivilage())
		{//ถ้าเป็นจริงเเสดงว่าเป็น Admin ให้เข้าถึงปุ่ม stock กับ user ได้
			btn_stock.setEnabled(true);
			btn_user.setEnabled(true);
		}
		else
		{
			btn_stock.setEnabled(false);
			btn_user.setEnabled(false);
			btn_stock.setIcon(MysetIconForJAR_File("/image/Lock_icon.png"));
			btn_user.setIcon(MysetIconForJAR_File("/image/Lock_icon.png"));
		}
	}

	public ImageIcon MysetIconForJAR_File(String path) {
	    // โหลดไอคอน ImageIcon โดยใช้ getResource()
	    ImageIcon iconReset = new ImageIcon(getClass().getResource(path));
	    // นำไอคอน ImageIcon ไปปรับขนาด
	    Image img = iconReset.getImage();
	    Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	    ImageIcon resizedIcon = new ImageIcon(newImg);
	    return resizedIcon;
	}

	private boolean NoSelectItem() {
	    int count = 0;
	    for (int i = 0; i < QuantityListJspinner.size(); i++) {
	        count++;
	        // ดึงค่าของ Spinner
	        Object spinnerValueObject = QuantityListJspinner.get(i).getValue();
	        if (spinnerValueObject instanceof Double) {
	            // แปลงค่าของ Spinner เป็น Double
	            double spinnerValue = ((Double) spinnerValueObject).doubleValue();
	            if (spinnerValue != 0.0) {
	                return false;
	            }
	        } else if (spinnerValueObject instanceof Integer) {
	            // แปลงค่าของ Spinner เป็น Integer
	            int spinnerValue = ((Integer) spinnerValueObject).intValue();
	            if (spinnerValue != 0) {
	                return false;
	            }
	        }
	        // ตรวจสอบว่าถึงแล้วที่ต้องการแจ้งเตือน
	        if (count == QuantityListJspinner.size()) {
	            JOptionPane.showMessageDialog(main_Frame.this, "please select item!!!");
	            return true;
	        }
	    }
	    // กรณีไม่พบข้อผิดพลาด
	    JOptionPane.showMessageDialog(main_Frame.this, "Error from NoSelectItem");
	    return true;
	}



private void HeaderInvoice()
{
	textArea.setText(" ****************Dessert Cafe*****************\n"
	 +" Time: "+lblNewLabel_time.getText()+" Date: "+lblNewLabel_date.getText()+"\n"
	 				+" *********************************************\n"
	 				+" Item Name\t       Qty.   \tPrice(Bath)\n");
					
}

public void calculate()
{   int Number=0;
    //การจะคำนวณอีกรอบต้องทำการ clear Arraylist Sum ที่เก็บค่าผลรวม รอบเเรกออกก่อนโดยใช้คำสั่ง Sum.clear() เพื่อคำนวณใหม่เมื่อกดปุ่มครั้งถัดไปหลังเเก้ไขจำนวนสินค้าที่เลือก 
	Sum.clear();
    double Cusum=0.0;
    double VatService=0.0;
    double Total=0.0;
    HeaderInvoice();
	for(int i=0;i<QuantityListJspinner.size();i++)
	{    
		if(QuantityListJspinner.get(i).getValue() instanceof Double && (Double)QuantityListJspinner.get(i).getValue() != 0.0)
		{  
			//เรียกใช้ fn addspace ที่ทำการปรับ String ข้างในให้มีขนาดเท่ากันเเม้จะมีความยาวชื่อเเตกต่างกันโดยการเพิ่มช่องว่างเข้าไป
			AddSpaceProductNameList=addSpaces(ProductNameList);			
			int Qty = ((Double)QuantityListJspinner.get(i).getValue()).intValue() ;
			Number++;
			//กรณีในเหตุการณ์ที่ ตัวเเรกไม่ถูกกดซื้อ มันจะข้ามมาตัวที่สอง ดังนั้นการบวกเข้า array Sum จะ add 
			//เข้าเพียงตัวเดียวดังนั้นความยาวจึงมีขนาดต่างจาก QuantityList.size() จะใช้ค่า i อ้างอิงดึงค่าจาก get Sum ออกมาไม่ได้ เปลี่ยนเป็น  Sum.get(Number) เเทนเพราะถูกนับทุกๆครั้งที่เจอตัวที่ไม่เท่ากับ 0 ต้องลบ 1 ออกด้วยเพราะเราใช้ index array
	    	Sum.add(Qty*Double.parseDouble(ProductPriceList.get(i).getText()));
	    	textArea.setText(textArea.getText()+" "+Number+". "+AddSpaceProductNameList.get(i).getText()+"\t"+Qty+"\t   "+Sum.get(Number-1)+"0\n");
	    	//คำนวนผลรวมของราคาสินค้าที่ซื้อ
	    	Cusum+= Sum.get(Number-1);//หาผลรวมของราคาสินค้าทั้งหมดก่อนบวก vat
	    	
	     }
	}
	//คำนวนในส่วนของ Vat&Service charge
	VatService=(Cusum/100.0)*17.00;
	Total =VatService+Cusum;
	total[0] = Total;
	
	textField_VS.setText(String.valueOf(String.format("%.2f", VatService)));
	textField_SubTotal.setText(String.valueOf(String.format("%.2f", Cusum)));
	textField_Total.setText(String.valueOf(String.format("%.2f", Total)));
	
	textArea.setText(textArea.getText()+" *********************************************\n"
			+" Vat&Service: \t\t\t   "+String.valueOf(String.format("%.2f", VatService))+"\n"
			+" Sub Total:   \t\t\t   "+String.valueOf(String.format("%.2f", Cusum))+"\n"
			+" Total:       \t\t\t   "+String.valueOf(String.format("%.2f", Total))+"\n"
			+" *********************************************\n");
	
     
}
public boolean userPrivilage()
{
	String status = UserDB_Current.status;	
	if(status.equals("admin"))
	{
		return true;
	}
	else
	{
		return false;
	}
}
public void reset()
{
	for(int i=0;i<QuantityListJspinner.size();i++)
	{
		 QuantityListJspinner.get(i).setValue(0);
		 textField_VS.setText("0.0");
		 textField_SubTotal.setText("0.0");
		 textField_Total.setText("0.0");
		 textArea.setText("");
		 
	}

}
//ซ่อนไว้เพราะเราจะใช้อันที่มันสามารถเเสดงผลใน JAR file ได้ด้วยเเทนเเล้ว
//public ImageIcon MysetIcon(String URL)
//{
//	
//	ImageIcon iconReset = new ImageIcon(URL);
//	Image img = iconReset.getImage();
//	//ปรับขนาด icon ให้มีขนาดเล็กลง
//	Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
//	ImageIcon resizedIcon = new ImageIcon(newImg);
//	return resizedIcon;
//}

public static ArrayList<JLabel> addSpaces(ArrayList<JLabel> labels) {
    // หาความยาวของ JLabel ที่ยาวที่สุด
    int maxLength = 0;
    for (JLabel label : labels) {
        if (label.getText().length() > maxLength) {
            maxLength = label.getText().length();
        }
    }

    // สร้าง ArrayList ใหม่เพื่อเก็บ JLabel ที่มีช่องว่างเพิ่มเติม
    ArrayList<JLabel> result = new ArrayList<>();
    for (JLabel label : labels) {
        StringBuilder textWithSpaces = new StringBuilder(label.getText());
        int spacesToAdd = maxLength - label.getText().length();
        for (int j = 0; j < spacesToAdd; j++) {
            textWithSpaces.append(" "); // เพิ่มช่องว่าง
        }
        JLabel newLabel = new JLabel(textWithSpaces.toString()); // สร้าง JLabel ใหม่ที่มีข้อความพร้อมช่องว่าง
        result.add(newLabel); // เพิ่ม JLabel ใหม่ใน ArrayList ผลลัพธ์
    }

    return result;
}
public  void LoadProduct()
{   
	
	//อย่าลืม reset array ของ japinner ด้วยเพราะทุกๆรอบที่มันเอาไปใช้มันมีการสรา้งใหม่เสมอออออ ถ้าเราเอาไปใช้ข้างนอก array ของ japinner มันไม่ได้หายไปไหน
	QuantityListJspinner.clear();
	panel.removeAll();
	panel.repaint();
	//ดึงข้อมูลของProduct มาจากฐานข้อมูล
	ProductManager getAllProduct = new ProductManager();
	list = getAllProduct.getAllProduct();
	for(int i = 0; i < list.size(); i++)
	{   JPanel sub_panel = new JPanel();
		ProductDB P = list.get(i);
			if(P.product_capable==0)//ถ้าอันไหนมี stock เป็น 0 ให้พื้นหลังเป็นสีเเดงกับ add Label sold out เข้าไป
			{
				//สร้าง label ลงไปยัง่panel ที่กลายเป็น grid ไปเเล้ว
				
				sub_panel.setBackground(new Color(255,222,183));
				sub_panel.setPreferredSize(new Dimension(260, 200)); // กำหนดขนาด sub_panel ตามที่ต้องการ
			    sub_panel.setLayout(null);//กำหนดตำเเหน่งของ sub_panel ที่อยู่ใน JPanel อันนี้ต้องเป็น null ไม่งั้นจะปรับตำเเหน่งของ component อื่นๆไม่ได้เลย
			    
				
				ImageIcon iconReset = new ImageIcon(getClass().getResource("/image/sold-out.png"));
				Image img = iconReset.getImage();
				//ปรับขนาด icon ให้มีขนาดเล็กลง
				Image newImg = img.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(newImg);
				JLabel soldout = new JLabel(resizedIcon);
				soldout.setBounds(75,-10,150,150);
				sub_panel.add(soldout);
				
				JLabel imgLabel = new JLabel(loadImg(P.product_Image,285,115));
				imgLabel.setPreferredSize(new Dimension(285,115));
				imgLabel.setBounds(5,5,285,115);
				sub_panel.add(imgLabel);
				// เพิ่มเส้นแบ่งระหว่าง Grid ด้วย LineBorde
//				sub_panel.setBorder(new LineBorder(Color.BLACK));
				sub_panel. setBorder(BorderFactory. createLineBorder(Color. black));
				
				
			}
			else
			{
					//สร้าง label ลงไปยัง่panel ที่กลายเป็น grid ไปเเล้ว
					
					sub_panel.setBackground(new Color(229,255,251));
					sub_panel.setPreferredSize(new Dimension(260, 200)); // กำหนดขนาด sub_panel ตามที่ต้องการ
				    sub_panel.setLayout(null);//กำหนดตำเเหน่งของ sub_panel ที่อยู่ใน JPanel อันนี้ต้องเป็น null ไม่งั้นจะปรับตำเเหน่งของ component อื่นๆไม่ได้เลย
				    JLabel imgLabel = new JLabel(loadImg(P.product_Image,285,115));
					imgLabel.setPreferredSize(new Dimension(285,115));
					imgLabel.setBounds(5,5,285,115);
					sub_panel.add(imgLabel);
					// เพิ่มเส้นแบ่งระหว่าง Grid ด้วย LineBorde
//					sub_panel.setBorder(new LineBorder(Color.BLACK));
					sub_panel. setBorder(BorderFactory. createLineBorder(Color. black));
			}		
//					label.setFont(new Font("Serif", Font.PLAIN, 20)); 
					
					
					JLabel name = new JLabel("Name :");
					name.setFont(new Font("Tahoma", Font.BOLD, 15));
					name.setBounds(15,90,100,100);
					sub_panel.add(name);
								
					JLabel db_FoodName = new JLabel(P.product_name);
					db_FoodName.setFont(new Font("Tahoma", Font.PLAIN, 15));
					db_FoodName.setBounds(70,90,100,100);
					ProductNameList.add(db_FoodName);
					sub_panel.add(db_FoodName);
					
					JLabel price = new JLabel("Price  :");
					price.setFont(new Font("Tahoma", Font.BOLD, 15));
					price.setBounds(15,110,100,100);
					sub_panel.add(price);
					
					JLabel db_Foodprice = new JLabel(P.product_price+"");
					db_Foodprice.setFont(new Font("Tahoma", Font.PLAIN, 15));
					db_Foodprice.setBounds(70,110,100,100);
					ProductPriceList.add(db_Foodprice);
					sub_panel.add(db_Foodprice);
					
					
					// from 0 to 9, in 1.0 steps start value 0
					SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, P.product_capable, 1.0);
					JSpinner Quantity = new JSpinner(model);
					Quantity.setBounds(218,140,70,30);
					
//					Quantity.addChangeListener(new ChangeListener() { 
//						int countJspinnerChange=0;    
//						  @Override
//						  public void stateChanged(ChangeEvent e) 
//						  {   countJspinnerChange++;
//							  if(countJspinnerChange>P.product_capable)
//							  {
//								JOptionPane.showMessageDialog(cafe_GUI.this, "not enough"+P.product_name+"have only"+P.product_capable);								
//							  }
//						  }
//						});
					//นำ arraylist ที่เก็บตัวเเปรประเภท spinner มาเก็บที่สร้างไว้ใน gridlayout ซึ่งจะเอาไปใช้ข้างนอกได้เพราะเราประกาศให้เป็น global variable เเล้ว
					//ทำเเบบนี้ในทำนองเดียวกันกับชื่อเเละราคาของ product  
					QuantityListJspinner.add(Quantity);	
					System.out.println(QuantityListJspinner.size()+"from loadProduct");
					
					
					sub_panel.add(Quantity);
								
					panel.add(sub_panel);
				
	}
}
public  void LoadProductSearch(String Xtext_search)
{   
	String text_search =Xtext_search;
	//อย่าลืม reset array ของ japinner ด้วยเพราะทุกๆรอบที่มันเอาไปใช้มันมีการสรา้งใหม่เสมอออออ ถ้าเราเอาไปใช้ข้างนอก array ของ japinner มันไม่ได้หายไปไหน
	QuantityListJspinner.clear();
	panel.removeAll();
	panel.repaint();
	//ดึงข้อมูลของProduct มาจากฐานข้อมูล
	ProductManager PM = new ProductManager();
	list = PM.SearchProduct(text_search);
	
	for(int i = 0; i < list.size(); i++)
	{   JPanel sub_panel = new JPanel();
		ProductDB P = list.get(i);
			if(P.product_capable==0)//ถ้าอันไหนมี stock เป็น 0 ให้พื้นหลังเป็นสีเเดงกับ add Label sold out เข้าไป
			{
				//สร้าง label ลงไปยัง่panel ที่กลายเป็น grid ไปเเล้ว
				
				sub_panel.setBackground(new Color(255,222,183));
				sub_panel.setPreferredSize(new Dimension(260, 200)); // กำหนดขนาด sub_panel ตามที่ต้องการ
			    sub_panel.setLayout(null);//กำหนดตำเเหน่งของ sub_panel ที่อยู่ใน JPanel อันนี้ต้องเป็น null ไม่งั้นจะปรับตำเเหน่งของ component อื่นๆไม่ได้เลย
			    
				
				ImageIcon iconReset = new ImageIcon(getClass().getResource("/image/sold-out.png"));
				Image img = iconReset.getImage();
				//ปรับขนาด icon ให้มีขนาดเล็กลง
				Image newImg = img.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(newImg);
				JLabel soldout = new JLabel(resizedIcon);
				soldout.setBounds(75,-10,150,150);
				sub_panel.add(soldout);
				
				JLabel imgLabel = new JLabel(loadImg(P.product_Image,285,115));
				imgLabel.setPreferredSize(new Dimension(285,115));
				imgLabel.setBounds(5,5,285,115);
				sub_panel.add(imgLabel);
				// เพิ่มเส้นแบ่งระหว่าง Grid ด้วย LineBorde
//				sub_panel.setBorder(new LineBorder(Color.BLACK));
				sub_panel. setBorder(BorderFactory. createLineBorder(Color. black));
				
				
			}
			else
			{
					//สร้าง label ลงไปยัง่panel ที่กลายเป็น grid ไปเเล้ว
					
					sub_panel.setBackground(new Color(229,255,251));
					sub_panel.setPreferredSize(new Dimension(260, 200)); // กำหนดขนาด sub_panel ตามที่ต้องการ
				    sub_panel.setLayout(null);//กำหนดตำเเหน่งของ sub_panel ที่อยู่ใน JPanel อันนี้ต้องเป็น null ไม่งั้นจะปรับตำเเหน่งของ component อื่นๆไม่ได้เลย
				    JLabel imgLabel = new JLabel(loadImg(P.product_Image,285,115));
					imgLabel.setPreferredSize(new Dimension(285,115));
					imgLabel.setBounds(5,5,285,115);
					sub_panel.add(imgLabel);
					// เพิ่มเส้นแบ่งระหว่าง Grid ด้วย LineBorde
//					sub_panel.setBorder(new LineBorder(Color.BLACK));
					sub_panel. setBorder(BorderFactory. createLineBorder(Color. black));
			}		
//					label.setFont(new Font("Serif", Font.PLAIN, 20)); 
					
					
					JLabel name = new JLabel("Name :");
					name.setFont(new Font("Tahoma", Font.BOLD, 15));
					name.setBounds(15,90,100,100);
					sub_panel.add(name);
								
					JLabel db_FoodName = new JLabel(P.product_name);
					db_FoodName.setFont(new Font("Tahoma", Font.PLAIN, 15));
					db_FoodName.setBounds(70,90,100,100);
					ProductNameList.add(db_FoodName);
					sub_panel.add(db_FoodName);
					
					JLabel price = new JLabel("Price  :");
					price.setFont(new Font("Tahoma", Font.BOLD, 15));
					price.setBounds(15,110,100,100);
					sub_panel.add(price);
					
					JLabel db_Foodprice = new JLabel(P.product_price+"");
					db_Foodprice.setFont(new Font("Tahoma", Font.PLAIN, 15));
					db_Foodprice.setBounds(70,110,100,100);
					ProductPriceList.add(db_Foodprice);
					sub_panel.add(db_Foodprice);
					
					
					// from 0 to 9, in 1.0 steps start value 0
					SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, P.product_capable, 1.0);
					JSpinner Quantity = new JSpinner(model);
					Quantity.setBounds(218,140,70,30);
					
					//นำ arraylist ที่เก็บตัวเเปรประเภท spinner มาเก็บที่สร้างไว้ใน gridlayout ซึ่งจะเอาไปใช้ข้างนอกได้เพราะเราประกาศให้เป็น global variable เเล้ว
					//ทำเเบบนี้ในทำนองเดียวกันกับชื่อเเละราคาของ product  
					QuantityListJspinner.add(Quantity);	
					System.out.println(QuantityListJspinner.size()+"from loadProduct");
					
					
					sub_panel.add(Quantity);
								
					panel.add(sub_panel);
				
	}
}
public void setTime()
{
	new Thread(new Runnable() {
		
		@Override
		public void run()
		{
			while(true)
			{
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				Date date = new Date();
				SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
				SimpleDateFormat df = new SimpleDateFormat("EEEE,dd-MM-yyyy");
				String time = tf.format(date);
				lblNewLabel_time.setText(time.split(" ")[0]+" "+time.split(" ")[1]);
				lblNewLabel_date.setText(df.format(date));
			}
			
		}
	}).start();
}
 private ImageIcon loadImg(String path,int width,int heigth)
 {
	 
	 try 
	 {BufferedImage originalImg = ImageIO.read(new File(path));
     Image scaledImg = originalImg.getScaledInstance(width, heigth, Image.SCALE_SMOOTH);

     BufferedImage bufferedImg = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
     Graphics2D g2d = bufferedImg.createGraphics();
     g2d.drawImage(scaledImg, 0, 0, null);
     g2d.dispose();

     return new ImageIcon(bufferedImg); 
	 } 
	 catch (IOException e) 
	 {
		e.printStackTrace();
		return null;
	}	 
 }
 public void playSound(String soundFilePath) {
     try {
//    	 getClass().getResource(string path) return ค่าออกมาเป็นประเภท URL 
         URL Sound =  getClass().getResource(soundFilePath);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound);
         Clip clip = AudioSystem.getClip();
         clip.open(audioInputStream);
         clip.start();

     } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
         e.printStackTrace();
     }
 }
}
