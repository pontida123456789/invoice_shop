package GUI_Frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Management.ProductDB;
import Management.ProductManager;
import Management.UserDB;
import Management.UserDB_Current;
import Management.UserManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class User_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_user_id;
	private JTextField textField_username;
	private JTextField textField_Operator;
	private JTable table;
	private ArrayList<UserDB>list;
	private JTextField textField_password;
	private JComboBox comboBox_status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					User_Frame frame = new User_Frame();
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
	public User_Frame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1062, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100, 149, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 756, 520);
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
					String uid = (table.getValueAt(index, 0).toString());
					String uname = table.getValueAt(index, 1).toString();
					String upass =  table.getValueAt(index, 2).toString();
					String ustatus =  table.getValueAt(index, 3).toString();
	
					// ใส่ตัวอักษรที่เเยกไว้ก่อนหน้านี้ลงไปใน textfield
					// เมื่อกดที่เเถวนั้นๆจะปรากฎข้อความขึ้น
					textField_user_id.setText(uid);
					textField_username.setText(uname);
					textField_password.setText(upass);
					comboBox_status.setSelectedItem(ustatus);	
				}
				
			}
		});

		scrollPane.setViewportView(table);
		
		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_id.setBounds(788, 29, 146, 20);
		contentPane.add(lbl_id);
		
		textField_user_id = new JTextField();
		textField_user_id.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_user_id.setEditable(false);
		textField_user_id.setColumns(10);
		textField_user_id.setBounds(787, 53, 210, 25);
		contentPane.add(textField_user_id);
		
		JLabel lbl_Name = new JLabel("Username");
		lbl_Name.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_Name.setBounds(788, 89, 146, 20);
		contentPane.add(lbl_Name);
		
		textField_username = new JTextField();
		textField_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_username.setColumns(10);
		textField_username.setBounds(787, 113, 210, 25);
		contentPane.add(textField_username);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblStatus.setBounds(786, 209, 146, 20);
		contentPane.add(lblStatus);
		
		textField_Operator = new JTextField();
		textField_Operator.setText((String) null);
		textField_Operator.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Operator.setEditable(false);
		textField_Operator.setColumns(10);
		textField_Operator.setBackground(new Color(144, 238, 144));
		textField_Operator.setBounds(787, 333, 210, 25);
		contentPane.add(textField_Operator);
		textField_Operator.setText(UserDB_Current.username);

		
		JLabel lblOperator = new JLabel("Current Operator");
		lblOperator.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblOperator.setBounds(787, 307, 146, 20);
		contentPane.add(lblOperator);
		
		JButton btn_add = new JButton(MysetIconForJAR_File("/image/add_icon.png"));
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(checkTextField())
				{
					String Uname = textField_username.getText();
					String Upass = textField_password.getText();
					String Ustatus = comboBox_status.getSelectedItem().toString();
					
					UserManager UM = new UserManager();
					int k = UM.AddUser(Uname, Upass, Ustatus);
					showdialogSucessOrFaile(k);
					
				}
			}
		});
		btn_add.setText("ADD");
		btn_add.setForeground(SystemColor.desktop);
		btn_add.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_add.setBackground(SystemColor.window);
		btn_add.setBounds(823, 377, 146, 44);
		contentPane.add(btn_add);
		
		JButton btnEdit = new JButton(MysetIconForJAR_File("/image/edit_icon.png"));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				checkTextField();
				String Uname = textField_username.getText();
				String Upass = textField_password.getText();
				String Ustatus = comboBox_status.getSelectedItem().toString();
				if(!textField_user_id.getText().isEmpty())
				{
					int Uid = Integer.parseInt(textField_user_id.getText());
					UserManager UM = new UserManager();
					int k = UM.EditUser(Uname, Upass, Ustatus,Uid);
					showdialogSucessOrFaile(k);
				}
				
				
			}
		});
		btnEdit.setText("EDIT");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEdit.setBackground(SystemColor.window);
		btnEdit.setBounds(823, 442, 146, 44);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton(MysetIconForJAR_File("/image/delete.png"));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				checkTextFieldforDELETE();
				UserManager UM = new UserManager();
				if(!textField_user_id.getText().isEmpty())
				{
					int k = UM.DeleteUser(Integer.parseInt(textField_user_id.getText()));
					showdialogSucessOrFaile(k);
				}
				
			}
		});
		btnDelete.setText("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBackground(SystemColor.window);
		btnDelete.setBounds(823, 507, 146, 44);
		contentPane.add(btnDelete);
		
		comboBox_status = new JComboBox();
		comboBox_status.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_status.setModel(new DefaultComboBoxModel(new String[] {"user", "admin"}));
		comboBox_status.setBounds(788, 237, 209, 27);
		contentPane.add(comboBox_status);
		
		textField_password = new JTextField();
		textField_password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_password.setColumns(10);
		textField_password.setBounds(788, 172, 210, 25);
		contentPane.add(textField_password);
		
		JLabel lbl_Name_1 = new JLabel("Password");
		lbl_Name_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_Name_1.setBounds(789, 148, 146, 20);
		contentPane.add(lbl_Name_1);
		
		LoadUserlist();
	}
	public void LoadUserlist()
	{
		Font thaiFont = new Font("Tahoma", Font.PLAIN, 12); // แทน Tahoma ด้วยฟอนต์ที่รองรับภาษาไทย
		table.setFont(thaiFont);
		UserManager UM =new UserManager();
		list = UM.getAllUser();// เอาข้อมูลจาก db ที่ return เป็นarraylist ออกมาจาก method มาใส่ไว้ใน
												// arrayList ที่ชื่อ list ในนี้
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("user id"); // กำหนดชื่อหัวคอลัม
		model.addColumn("username");
		model.addColumn("password");
		model.addColumn("status");
		
		for (UserDB c : list) // CustomerDB ความยาวของอาร์เรย์จะยาวเท่าขนาดของข้อมูลที่ใส่ว่ามีกี่ id
		{
			model.addRow(new Object[] { c.user_id, c.username,c.password, c.status}); 
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
	
	public ImageIcon MysetIconForJAR_File(String path) {
	    // โหลดไอคอน ImageIcon โดยใช้ getResource()
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
	 public void clearTextField()
	 {
		 	textField_user_id.setText("");
			textField_username.setText("");
			textField_password.setText("");
			comboBox_status.setSelectedItem("user");	
	 }
	public boolean checkTextField()
	{
		if(textField_username.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(User_Frame.this, "Username is required!!!");
			return false;
		}
		else if(textField_password.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(User_Frame.this, "Password is required!!!");
			return false;
		}
		
		return true;
	}
	public boolean checkTextFieldforDELETE()
	{
		if(textField_username.getText().isEmpty()||textField_password.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(User_Frame.this, "Please select user to delete");
			return false;
		}	
		return true;
	}
	public void showdialogSucessOrFaile(int k)
	{
		if(k==1)
		{
			JOptionPane.showMessageDialog(User_Frame.this, "Process successfully!!!");
			LoadUserlist();
			clearTextField();
		}
		else
		{
			JOptionPane.showMessageDialog(User_Frame.this, "Process Failed!!!");			
		}
	}
}


