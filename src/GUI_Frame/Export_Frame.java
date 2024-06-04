package GUI_Frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Management.InvoiceManager;
import Management.Invoice_detailDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.regex.*;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.io.File;
import java.awt.Desktop;
import javax.swing.JComboBox;

public class Export_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ArrayList<Invoice_detailDB>list ;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JComboBox comboBox3;
	private JComboBox comboBox4;
	private JComboBox comboBox5;
	private JComboBox comboBox6;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 UIManager.setLookAndFeel(
					            UIManager.getSystemLookAndFeelClassName());
					Export_Frame frame = new Export_Frame();
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
	public Export_Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(100, 149, 237));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Range of date :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(33, 34, 127, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("to");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(213, 95, 18, 32);
		contentPane.add(lblNewLabel_1);
		
		comboBox1 = new JComboBox();
		comboBox1.setBounds(20, 100, 58, 21);
		contentPane.add(comboBox1);
		
		comboBox2 = new JComboBox();
		comboBox2.setBounds(85, 100, 58, 21);
		contentPane.add(comboBox2);
		
		comboBox3 = new JComboBox();
		comboBox3.setBounds(150, 100, 58, 21);
		contentPane.add(comboBox3);
		
		comboBox4 = new JComboBox();
		comboBox4.setBounds(235, 100, 58, 21);
		contentPane.add(comboBox4);
		
		comboBox5 = new JComboBox();
		comboBox5.setBounds(300, 100, 58, 21);
		contentPane.add(comboBox5);
		
		comboBox6 = new JComboBox();
		comboBox6.setBounds(365, 100, 58, 21);
		contentPane.add(comboBox6);
		
		setCombobox(comboBox1, comboBox2, comboBox3);
		setCombobox(comboBox4, comboBox5, comboBox6);
		
		
		JButton btnNewButton = new JButton("Export");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
						String intitialDate = comboBox1.getSelectedItem().toString()
											  +"-"+comboBox2.getSelectedItem().toString()
											  +"-"+comboBox3.getSelectedItem().toString();
						String EndDate	    = comboBox4.getSelectedItem().toString()
								  			  +"-"+comboBox5.getSelectedItem().toString()
								  			  +"-"+comboBox6.getSelectedItem().toString();
						
						InvoiceManager InvManage = new InvoiceManager();
						list = InvManage.getAllInvoice_detail(intitialDate, EndDate) ;
						if(list.size()==0)
						{
							JOptionPane.showMessageDialog(Export_Frame.this, "Data not found for the selected date. Please specify a new date.");
						}
						else
						{
							String file_name = JOptionPane.showInputDialog(Export_Frame.this,"Please name the file");
							if(file_name.isEmpty())
							{
								JOptionPane.showMessageDialog(Export_Frame.this, "Please name the file!!!");
							}
							else
							{
								File file = new File("C:\\user\\Desktop\\List_of_invoice\\"+file_name+".csv");
								file.getParentFile().mkdirs();
								try 
								{	
									FileWriter fw = new FileWriter(file);
									
									//Write column name
									fw.append("invoice_id,");
									fw.append("product_id,");
									fw.append("Qty,");
									fw.append("price_per_list,");
									fw.append("time_print_invoice");
									fw.append("\n");
									
									//Write Data
									for(int i=0;i<list.size();i++)
									{   
										Invoice_detailDB I = list.get(i);
										fw.append(I.invoice_id+"");
										fw.append(",");
										fw.append(I.product_id+"");
										fw.append(",");
										fw.append(I.Qty+"");
										fw.append(",");
										fw.append(I.price_per_list+"");
										fw.append(",");
										fw.append(I.time_print_invoice+"");
										fw.append("\n");	
									}
									
									playSound("/sound/mixkit-gold-coin-prize-1999.wav");
									JOptionPane.showMessageDialog(Export_Frame.this, "CSV file is export successfully!!!");
									fw.flush();
									Export_Frame.this.setVisible(false);
									Desktop desktop = Desktop.getDesktop();
									desktop.open(file);
									fw.close();
								} 
								catch (IOException e1) 
								{
									
									e1.printStackTrace();
								}
							}
							
						}				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(306, 154, 85, 25);
		contentPane.add(btnNewButton);
		
		
	}
	public void setCombobox (JComboBox a,JComboBox b,JComboBox c)
	{
		for(int i=1;i<=50;i++)
		{
			a.addItem(2023+i);
		}
		for(int i=1;i<=12;i++)
		{	
			if(i<=9)
			{
				b.addItem("0"+i);
			}
			else
			{
				b.addItem(i);
			}
		}
		for(int i=1;i<=31;i++)
		{
			if(i<=9)
			{
				c.addItem("0"+i);
			}
			else
			{
				c.addItem(i);
			}
		}
	}
	 public void playSound(String soundFilePath) {
	     try {
//	    	 getClass().getResource(string path) return ค่าออกมาเป็นประเภท URL 
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
