import java.awt.EventQueue;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaProjectForDBMS {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaProjectForDBMS window = new JavaProjectForDBMS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaProjectForDBMS() {
		initialize();
		Connect();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect()
	{
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/JavaProjectForDBMS", "root","");
	}
	catch(ClassNotFoundException ex)
	{
		
	}
	catch(SQLException ex) {
		
		
	}
}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DATABASE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(299, 0, 168, 138);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "DATA INFORMATION", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(23, 102, 386, 266);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("NAME");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(32, 36, 74, 34);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("CITY");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(32, 92, 74, 40);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("ADDRESS");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(32, 151, 131, 46);
		panel.add(lblNewLabel_3);
		
		txtbname = new JTextField();
		txtbname.setBounds(153, 36, 192, 31);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(153, 101, 192, 31);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(153, 168, 192, 31);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    String bname,edition,price;
				    bname = txtbname.getText();
				    edition = txtedition.getText();
				    price = txtprice.getText();
				                
				     try {
				        pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
				        pst.setString(1, bname);
				        pst.setString(2, edition);
				        pst.setString(3, price);
				        pst.executeUpdate();
				        JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
				        table_load();
				                       
				        txtbname.setText("");
				        txtedition.setText("");
				        txtprice.setText("");
				        txtbname.requestFocus();
				       }

				    catch (SQLException e1) 
				        {            
				       e1.printStackTrace();
				    }
				}

			public void table_load()
			{
			    try 
			    {
			    pst = con.prepareStatement("select * from book");
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			} 
			    catch (SQLException e) 
			     {
			        e.printStackTrace();
			  } 
			}
				
				
			}
		);
		btnNewButton.setBounds(33, 379, 89, 48);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.setBounds(149, 379, 96, 48);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.setBounds(267, 379, 96, 48);
		frame.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(419, 109, 330, 259);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(23, 438, 386, 53);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
	                
	                 try {
	                      
	                        String id = txtbid.getText();

	                            pst = con.prepareStatement("select name,edition,price from book where id = ?");
	                            pst.setString(1, id);
	                            ResultSet rs = pst.executeQuery();

	                        if(rs.next()==true)
	                        {
	                          
	                            String name = rs.getString(1);
	                            String edition = rs.getString(2);
	                            String price = rs.getString(3);
	                            
	                            txtbname.setText(name);
	                            txtedition.setText(edition);
	                            txtprice.setText(price);
	    
	                        }   
	                        else
	                        {
	                            txtbname.setText("");
	                            txtedition.setText("");
	                            txtprice.setText("");
	                             
	                        }
	                    } 
	                
	                 catch (SQLException ex) {
	                       
	                    }
	            }
			}
		);
		txtbid.setBounds(127, 11, 235, 30);
		txtbid.setColumns(10);
		panel_1.add(txtbid);
		
		JLabel lblNewLabel_2_1 = new JLabel("ID");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2_1.setBounds(78, 2, 74, 40);
		panel_1.add(lblNewLabel_2_1);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String bname,edition,price,bid;
				    bname = txtbname.getText();
				    edition = txtedition.getText();
				    price = txtprice.getText();
				    bid = txtbid.getText();
				                
				     try {
				        pst = con.prepareStatement("update book set name=? , edition = ? , price = ? where id = ?");
				        pst.setString(1, bname);
				        pst.setString(2, edition);
				        pst.setString(3, price);
				        pst.setString(4, price);
				        pst.executeUpdate();
				        JOptionPane.showMessageDialog(null, "Record Updated!");
				        table_load();
				                       
				        txtbname.setText("");
				        txtedition.setText("");
				        txtprice.setText("");
				        txtbname.requestFocus();
				       }

				    catch (SQLException e1) 
				        {            
				       e1.printStackTrace();
				    }
			}

			public void table_load()
			{
			    try 
			    {
			    pst = con.prepareStatement("select * from book");
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			} 
			    catch (SQLException e) 
			     {
			        e.printStackTrace();
			  } 
			}
		});
		btnUpdate.setBounds(468, 379, 89, 48);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_2_1 = new JButton("Delete");
		btnNewButton_2_1.setBounds(567, 379, 96, 48);
		frame.getContentPane().add(btnNewButton_2_1);
	}
}
