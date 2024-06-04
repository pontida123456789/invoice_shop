package GUI_Frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Management.UserDB_Current;
import Management.UserManager;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Login_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField Field_password;
	private JTextField textField_username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					  UIManager.setLookAndFeel(
					            UIManager.getSystemLookAndFeelClassName());
					Login_Frame frame = new Login_Frame();
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
	public Login_Frame() {
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 352);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//set icon ให้กับโปรเเกรม
		setIconImage(new javax.swing.ImageIcon(getClass().getResource("/image/invoice.png")).getImage());
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setBounds(0, 0, 534, 103);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setBounds(180, 29, 153, 41);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 31));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usename");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(78, 147, 111, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(78, 191, 111, 20);
		contentPane.add(lblNewLabel_1_1);
		
		Field_password = new JPasswordField();
		Field_password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Field_password.setBackground(Color.WHITE);
		Field_password.setBounds(186, 185, 217, 26);
		contentPane.add(Field_password);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{  	String PasswordString =  new String(Field_password.getPassword());
				UserManager user = new UserManager();
				
				if(user.CheckUserLogin(textField_username.getText(),PasswordString))
				{
					Main_Frame cafeFrame = new Main_Frame();
					cafeFrame.setVisible(true);
					Login_Frame.this.dispose();					
				}
				else if(textField_username.getText().isEmpty()&&Field_password.getPassword().length==0)
				{
					JOptionPane.showMessageDialog(Login_Frame.this, "Please Enter your username and password to login.");
				}
				else
				{
					JOptionPane.showMessageDialog(Login_Frame.this, "username or password incorrect!!!");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(147, 242, 103, 31);
		contentPane.add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login_Frame.this.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExit.setBounds(282, 242, 103, 31);
		contentPane.add(btnExit);
		
		textField_username = new JTextField();
		textField_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_username.setBounds(186, 143, 217, 26);
		contentPane.add(textField_username);
		textField_username.setColumns(10);
	}
}
