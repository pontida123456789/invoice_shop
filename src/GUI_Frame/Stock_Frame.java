package GUI_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import GUI_Frame.main_Frame;
import Management.ProductDB;
import Management.ProductManager;
import Management.UserDB_Current;

import javax.swing.table.DefaultTableCellRenderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Stock_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_Name;
	private JTextField textField_Price;
	private JTextField textField_Qty;
	private JTextField textField_Operator;
	private JTable table;
	private ArrayList<ProductDB>list;
	private JPanel panelImage;
	private JTextField textField_URLimage;
	 // ประกาศตัวแปรเก็บอินสแตนซ์ของ cafe_GUI
    private main_Frame cafeGUI;
    private JTextField textField_search;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					
					 // สร้างอินสแตนซ์ของ cafe_GUI
	                main_Frame cafeGUI = new main_Frame();
	                // กำหนดค่า cafe_GUI ให้กับ Stock_Frame
	                
					Stock_Frame frame = new Stock_Frame(cafeGUI);
	                frame.setCafeGUI(cafeGUI);
	                
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
	public Stock_Frame(main_Frame cafeGUI) {
		setResizable(false);
		this.cafeGUI = cafeGUI;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1062, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100, 149, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//set icon ให้กับโปรเเกรม
		setIconImage(new javax.swing.ImageIcon(getClass().getResource("/image/invoice.png")).getImage());
				
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 31, 756, 469);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{   System.out.println("select tabled");
				if (table.getSelectedRow() < 0) {
					return;
				} else { // เเยกเอาเเต่ละตัวในเเถวนั้นออกมา ใส่ไว้ในเเต่ละตัวเเปร
					int index = table.getSelectedRow();
					String pid = (table.getValueAt(index, 0).toString());
					String pname = table.getValueAt(index, 1).toString();
					String pprice =  table.getValueAt(index, 2).toString();
					String qty = table.getValueAt(index, 3).toString();
					
					// ใส่ตัวอักษรที่เเยกไว้ก่อนหน้านี้ลงไปใน textfield
					// เมื่อกดที่เเถวนั้นๆจะปรากฎข้อความขึ้น
					textField_id.setText(pid);
					textField_Name.setText(pname);
					textField_Price.setText(pprice);
					textField_Qty.setText(qty);
					String img_path = list.get(index).product_Image;
					System.out.println(img_path);
					addImagePanel(img_path,150,130);
					textField_URLimage.setText(img_path);
					
					
					
				}
				
			}
		});

		scrollPane.setViewportView(table);
		
		panelImage = new JPanel();
		panelImage.setBounds(808, 66, 210, 142);
		panelImage.setBackground(SystemColor.window);
		panelImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panelImage);
		
		JLabel lbl_id = new JLabel("ID");
		lbl_id.setBounds(810, 251, 146, 20);
		lbl_id.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lbl_id);
		
		textField_id = new JTextField();
		textField_id.setBounds(809, 275, 210, 25);
		textField_id.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_id.setEditable(false);
		contentPane.add(textField_id);
		textField_id.setColumns(10);
		
		JLabel lbl_Name = new JLabel("Name");
		lbl_Name.setBounds(810, 311, 146, 20);
		lbl_Name.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lbl_Name);
		
		textField_Name = new JTextField();
		textField_Name.setBounds(809, 335, 210, 25);
		textField_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Name.setColumns(10);
		contentPane.add(textField_Name);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(808, 369, 146, 20);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblPrice);
		
		textField_Price = new JTextField();
		textField_Price.setBounds(807, 393, 210, 25);
		textField_Price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Price.setColumns(10);
		contentPane.add(textField_Price);
		
		textField_Qty = new JTextField();
		textField_Qty.setBounds(806, 448, 210, 25);
		textField_Qty.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Qty.setColumns(10);
		contentPane.add(textField_Qty);
		
		JLabel lblQty = new JLabel("Qty.");
		lblQty.setBounds(807, 424, 146, 20);
		lblQty.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblQty);
		
		textField_Operator = new JTextField();
		textField_Operator.setBounds(805, 508, 210, 25);
		textField_Operator.setBackground(new Color(144, 238, 144));
		textField_Operator.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Operator.setEditable(false);
		textField_Operator.setColumns(10);
		contentPane.add(textField_Operator);
		textField_Operator.setText(UserDB_Current.username);
		
		JLabel lblOperator = new JLabel("Current Operator");
		lblOperator.setBounds(806, 484, 146, 20);
		lblOperator.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblOperator);
		
		JButton btn_Browsing = new JButton("Browse Image");
		btn_Browsing.setBounds(895, 218, 124, 25);
		btn_Browsing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new OpenFileFilter("png","PNG image") );
				fc.addChoosableFileFilter(new OpenFileFilter("jpg","Photo in JPEG format") );
				fc.addChoosableFileFilter(new OpenFileFilter("jpeg","Photo in JPEG format") );
				fc.addChoosableFileFilter(new OpenFileFilter("svg","Scalable Vector Graphic") );
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(Stock_Frame.this);
				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            File file = fc.getSelectedFile();
				            System.out.println(file.toString());
				            addImagePanel(file.toString(), 150, 130);
				            textField_URLimage.setText(file.toString());
				        } else {
				            
				        }
				   
			}
		});
		btn_Browsing.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(btn_Browsing);
		
		
		JButton btn_add = new JButton(MysetIconForJAR_File("/image/add_icon.png"));
		btn_add.setBounds(160, 510, 146, 44);
		btn_add.setForeground(SystemColor.desktop);
		btn_add.setBackground(SystemColor.window);
		btn_add.setText("ADD");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkTextField()&&checkTypeInt()&&checkTypeDouble())
				{
					String Pname = textField_Name.getText();
					double Pprice = Double.parseDouble(textField_Price.getText());
					int Pcapa = Integer.parseInt(textField_Qty.getText());
					String Image = textField_URLimage.getText();
					String username = textField_Operator.getText();
					
					ProductManager PM = new ProductManager();
					int k = PM.AddProduct(Pname, Pprice, Pcapa, Image, username);
					showdialogSucessOrFaile(k);
					
				}
			}
		});
		btn_add.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(btn_add);
		
		JButton btnEdit = new JButton(MysetIconForJAR_File("/image/edit_icon.png"));
		btnEdit.setBounds(333, 510, 146, 44);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkTextField()&&checkTypeInt()&&checkTypeDouble())
				{
					String Pname = textField_Name.getText();
					double Pprice = Double.parseDouble(textField_Price.getText());
					int Pcapa = Integer.parseInt(textField_Qty.getText());
					String Image = textField_URLimage.getText();
					String username = textField_Operator.getText();
					int pID =Integer.parseInt(textField_id.getText());
					ProductManager PM = new ProductManager();
					int k = PM.EditProduct(Pname, Pprice, Pcapa, Image, username, pID);
					showdialogSucessOrFaile(k);
				}
			}
		});
		btnEdit.setBackground(SystemColor.window);
		btnEdit.setText("EDIT");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton(MysetIconForJAR_File("/image/delete.png"));
		btnDelete.setBounds(505, 510, 146, 44);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkTextFieldforDELETE())
				{
					ProductManager PM = new ProductManager();
					int k = PM.DeleteProduct(Integer.parseInt(textField_id.getText()));
					showdialogSucessOrFaile(k);
				}	
			}
		});
		btnDelete.setBackground(SystemColor.window);
		btnDelete.setText("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(btnDelete);
		
		textField_URLimage = new JTextField();
		textField_URLimage.setBounds(809, 66, 210, 19);
		textField_URLimage.setEditable(false);
		contentPane.add(textField_URLimage);
		textField_URLimage.setColumns(10);
		
		textField_search = new JTextField();
		textField_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					LoadProductSerch(textField_search.getText());
					if(textField_search.getText().isEmpty())
					{
						LoadProductlist();
					}
				}
			}
		});
		textField_search.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_search.setColumns(10);
		textField_search.setBounds(805, 31, 144, 25);
		contentPane.add(textField_search);
		
		JButton btnNewButton = new JButton("search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadProductSerch(textField_search.getText());
				if(textField_search.getText().isEmpty())
				{
					LoadProductlist();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(953, 32, 66, 24);
		contentPane.add(btnNewButton);
		textField_URLimage.setVisible(false);
		
		LoadProductlist();
	}
	public void LoadProductlist()
	{
		Font thaiFont = new Font("Tahoma", Font.PLAIN, 12); // แทน Tahoma ด้วยฟอนต์ที่รองรับภาษาไทย
		table.setFont(thaiFont);
		ProductManager productMN =new ProductManager();
		list = productMN.getAllProduct();// เอาข้อมูลจาก db ที่ return เป็นarraylist ออกมาจาก method มาใส่ไว้ใน
												// arrayList ที่ชื่อ list ในนี้
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product id"); // กำหนดชื่อหัวคอลัม
		model.addColumn("product name");
		model.addColumn("price per unit");
		model.addColumn("quantity");
		model.addColumn("operator");
		model.addColumn("time");
		
		for (ProductDB c : list) // CustomerDB ความยาวของอาร์เรย์จะยาวเท่าขนาดของข้อมูลที่ใส่ว่ามีกี่ id
		{
			model.addRow(new Object[] { c.product_id, c.product_name, c.product_price, c.product_capable,c.username,c.time}); 
			// กำหนดว่าเเต่ละ id													// ในเเถวนึงมีข้อมูลอะไรบ้าง																				// เป็นข้อมูลที่ดึงมาจาก db
		}	
		table.setModel(model);
		
		 // กำหนด DefaultTableCellRenderer เป็นเซลล์หัวคอลัมน์ของ JTable
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		//ทำให้เป็นข้อมูลอยู่ตรงกลางหลังเรียกข้อมูลเสร็จเเล้ว
		 DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		 centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		    // กำหนด DefaultTableCellRenderer ให้กับทุกคอลัมน์
		    for (int i = 0; i < table.getColumnCount(); i++) 
		    {
		        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		    }
	}
	public void LoadProductSerch(String textProductSearch)
	{
		Font thaiFont = new Font("Tahoma", Font.PLAIN, 12); // แทน Tahoma ด้วยฟอนต์ที่รองรับภาษาไทย
		table.setFont(thaiFont);
		ProductManager productMN =new ProductManager();
		list = productMN.SearchProduct(textProductSearch);// เอาข้อมูลจาก db ที่ return เป็นarraylist ออกมาจาก method มาใส่ไว้ใน
												// arrayList ที่ชื่อ list ในนี้
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product id"); // กำหนดชื่อหัวคอลัม
		model.addColumn("product name");
		model.addColumn("price per unit");
		model.addColumn("quantity");
		model.addColumn("operator");
		model.addColumn("time");
		
		for (ProductDB c : list) // CustomerDB ความยาวของอาร์เรย์จะยาวเท่าขนาดของข้อมูลที่ใส่ว่ามีกี่ id
		{
			model.addRow(new Object[] { c.product_id, c.product_name, c.product_price, c.product_capable,c.username,c.time}); 
			// กำหนดว่าเเต่ละ id													// ในเเถวนึงมีข้อมูลอะไรบ้าง																				// เป็นข้อมูลที่ดึงมาจาก db
		}	
		table.setModel(model);
		
		 // กำหนด DefaultTableCellRenderer เป็นเซลล์หัวคอลัมน์ของ JTable
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		//ทำให้เป็นข้อมูลอยู่ตรงกลางหลังเรียกข้อมูลเสร็จเเล้ว
		 DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		 centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		    // กำหนด DefaultTableCellRenderer ให้กับทุกคอลัมน์
		    for (int i = 0; i < table.getColumnCount(); i++) 
		    {
		        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		    }
	}
	
	public void addImagePanel(String path, int width, int height)
	{
	    
		if(path.charAt(0)== '/')//สำหรับโหลดรูปภาพที่อยู่ใน JAR File ที่เเนบไปในโปรเเกรม ซึ่งจะขึ้นต้นด้วย / เสมอ เพราะรูปเราอยู่ใน /image/xxx.png เป็นรูปตั้งต้นที่อยากให้ผู้ใช้เห็นตอนเริ่มโปรเเกรมโดยลิ้งค์ใน db ก็จะเก็บในลักษณะนี้
    	{
    		try {
    			panelImage.removeAll();
    	        // อ่านไฟล์รูปภาพจาก InputStream
    	        InputStream inputStream = getClass().getResourceAsStream(path);
    	        if (inputStream != null) {
    	            BufferedImage originalImg = ImageIO.read(inputStream);

    	            // ปรับขนาดรูปภาพ
    	            Image scaledImg = originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);

    	            // สร้าง BufferedImage ใหม่
    	            BufferedImage bufferedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    	            // วาดรูปภาพลงบน BufferedImage ใหม่
    	            Graphics2D g2d = bufferedImg.createGraphics();
    	            g2d.drawImage(scaledImg, 0, 0, null);
    	            g2d.dispose();
    	            
    	            JLabel picLabel = new JLabel(new ImageIcon(bufferedImg));
    		        panelImage.add(picLabel);
    		        
    		        // ตรวจสอบขนาดของ JPanel และเรียกใช้ revalidate() และ repaint()
    		        panelImage.setPreferredSize(new Dimension(width, height));
    		        panelImage.revalidate();
    		        panelImage.repaint();
    	           
    	        } else {
    	            // กรณีที่ InputStream เป็น null
    	            System.out.println("Input stream is null!");
    	           
    	        }
    	    } catch (IOException e) {
    	        // กรณีเกิดข้อผิดพลาดในการอ่านไฟล์
    	        e.printStackTrace();
    	        
    	    }
    	}  	
    	else
    	{

			try {//สำหรับอัพโหลดรูปทั่วไปที่ไม่ใช่ link ในไฟล์ jar
		    	panelImage.removeAll();
		        BufferedImage originalImg = ImageIO.read(new File(path));
		        Image scaledImg = originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	
		        BufferedImage bufferedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g2d = bufferedImg.createGraphics();
		        g2d.drawImage(scaledImg, 0, 0, null);
		        g2d.dispose();
	
		        JLabel picLabel = new JLabel(new ImageIcon(bufferedImg));
		        panelImage.add(picLabel);
		        
		        // ตรวจสอบขนาดของ JPanel และเรียกใช้ revalidate() และ repaint()
		        panelImage.setPreferredSize(new Dimension(width, height));
		        panelImage.revalidate();
		        panelImage.repaint();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
    	}
	}
	  
	// เมธอดหรือคอนสตรักเตอร์ที่ใช้ในการกำหนดค่า cafeGUI
    public void setCafeGUI(main_Frame cafeGUI) {
        this.cafeGUI = cafeGUI;
    }   
    
    public ImageIcon MysetIconForJAR_File(String path) {
	    // โหลดไอคอน ImageIcon โดยใช้ getResource() เพราะจะให้ JAR file โหลดได้ด้วย
	    ImageIcon iconReset = new ImageIcon(getClass().getResource(path));
	    // นำไอคอน ImageIcon ไปปรับขนาด
	    Image img = iconReset.getImage();
	    Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	    ImageIcon resizedIcon = new ImageIcon(newImg);
	    return resizedIcon;
	}
    
//	public ImageIcon MysetIcon(String URL)
//	{
//		
//		ImageIcon iconReset = new ImageIcon(URL);
//		Image img = iconReset.getImage();
//		//ปรับขนาด icon ให้มีขนาดเล็กลง
//		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
//		ImageIcon resizedIcon = new ImageIcon(newImg);
//		return resizedIcon;
//	}
	class OpenFileFilter extends FileFilter 
	{

	    String description = "";
	    String fileExt = "";

	    public OpenFileFilter(String extension) {
	        fileExt = extension;
	    }

	    public OpenFileFilter(String extension, String typeDescription) {
	        fileExt = extension;
	        this.description = typeDescription;
	    }

	    public boolean accept(File f) {
	        if (f.isDirectory())
	            return true;
	        return (f.getName().toLowerCase().endsWith(fileExt));
	    }

	    public String getDescription() {
	        return description;
	    }
	}
	 public void clearTextField()
	 {
		 	textField_Name.setText("");
			textField_Price.setText("");
			textField_Qty.setText("");
			textField_URLimage.setText("");
			textField_id.setText("");
			panelImage.removeAll();
			panelImage.repaint(); 
	 }
	 public boolean checkTypeDouble()
	 {		
		    try
		    {   //พยายามเเบ่ง text เป็น Int ดูว่าทำได้ไหมถ้าไม่ได้ ก็เเปลว่ามันไม่ใช่ตัวเลขก็จะไปเข้า Exception
		        Double.parseDouble(textField_Price.getText());
		        return true;
		    } catch (NumberFormatException ex)
		    {
				 JOptionPane.showMessageDialog(Stock_Frame.this, "Pleas Enter Price only NUMBER!!!");
		        return false;
		    }
		
	 }
	 public boolean checkTypeInt()
	 {		
		    try
		    {   //พยายามเเบ่ง text เป็น Int ดูว่าทำได้ไหมถ้าไม่ได้ ก็เเปลว่ามันไม่ใช่ตัวเลขก็จะไปเข้า Exception
		        Integer.parseInt(textField_Qty.getText());
		        return true;
		    } catch (NumberFormatException ex)
		    {
				 JOptionPane.showMessageDialog(Stock_Frame.this, "Pleas Enter Quantity only Integer!!!");
		        return false;
		    }
		
	 }
	public boolean checkTextField()
	{
		if(textField_Name.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Product name is required!!!");
			return false;
		}
		else if(textField_Price.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Product price is required!!!");
			return false;
		}
		else if(textField_Qty.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Product Quantity is required!!!");
			return false;
		}
		else if(textField_URLimage.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Product Image is required!!!");
			return false;
		}
		return true;
	}
	public boolean checkTextFieldforDELETE()
	{
		if(textField_Name.getText().isEmpty()||textField_Price.getText().isEmpty()||
		   textField_Qty.getText().isEmpty()||textField_URLimage.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Please select item to delete");
			return false;
		}	
		return true;
	}
	public void showdialogSucessOrFaile(int k)
	{
		if(k==1)
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Process successfully!!!");
			LoadProductlist();
			clearTextField();
			cafeGUI.LoadProduct();
			cafeGUI.reset();
		}
		else
		{
			JOptionPane.showMessageDialog(Stock_Frame.this, "Process Failed!!!");			
		}
	}
}
