package mainFrame_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import comm.orderData;

public class OrderUI extends JFrame {
	private DefaultTableModel model;
	private JTable table;

	private JButton addButton;
	private JButton deleteButton;
	private JButton exitButton;
	
	private JTextField orderField;
	private JTextField pathField;

	private JPanel textPanel;
	private JPanel buttonPanel;

	private JScrollPane scroll;
	DataManagement datamanagement;
	
	public OrderUI(DataManagement datamanagement) {
		this.datamanagement=datamanagement;
		init();
		detailFrame();
		
	}


	public void init() {

		String[] Header = { "명령 번호", "명령어", "경로" };
		String[][] Data = new String[0][];

		model = new DefaultTableModel(Data, Header);
		table = new JTable(model);

		addButton = new JButton("추가");
		deleteButton = new JButton("삭제");
		exitButton = new JButton("종료");
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		
		orderField = new JTextField(11);
		orderField.setPreferredSize(new Dimension(50,25));
		pathField = new JTextField(14);
		pathField.setPreferredSize(new Dimension(100,25));

		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		DefaultTableCellRenderer tablecell = new DefaultTableCellRenderer();
		tablecell.setHorizontalAlignment(0);
		TableColumnModel cellmodel = table.getColumnModel();
		for (int i = 0; i < cellmodel.getColumnCount(); i++) {
			cellmodel.getColumn(i).setCellRenderer(tablecell);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);

		scroll = new JScrollPane(table);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() == -1) {
					return;
				} else {
					int selectedRow = table.getSelectedRow();

					model.removeRow(selectedRow);
					datamanagement.deleteOrderData(selectedRow);
					datamanagement.saveOrderToFile();
				}
			}

		});
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int num = table.getRowCount() - 1;
				orderData temp = new orderData( orderField.getText(),pathField.getText());
				datamanagement.addOrderData(num, temp);
				datamanagement.saveOrderToFile();
				String[] table = new String[3];
				//table[0] = String.valueOf(temp.getNum());
				//table[0] = String.valueOf(num);

				table[1] = temp.getOrderName();
				table[2] = temp.getPath();

				model.addRow(table);

				orderField.setText("");
				pathField.setText("");
			}

		});
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}

		});
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

	public void detailFrame() {
		addButton.setSize(50, 30);
		deleteButton.setSize(50, 30);
		
		JLabel temp =new JLabel("입력란");
		temp.setPreferredSize(new Dimension(100,50));
		textPanel.add(temp);
		textPanel.add(orderField);
		textPanel.add(pathField);
		textPanel.setBackground(new Color(54,54,54));
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(exitButton);
		buttonPanel.setBackground(new Color(54,54,54));
		setSize(400, 550);
		setBackground(new Color(54,54,54));
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setLayout(new BorderLayout());
		//pack();
		
		add(scroll, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
		printAllOrder();
	}

	public void printAllOrder() {
		int index=1;
		String[] table = new String[3];
		Iterator<orderData> itr = datamanagement.getArrayOrderData().iterator();
		while (itr.hasNext()) {
			orderData temp = (orderData) itr.next();

			table[0] = String.valueOf(index);
			table[1] = temp.getOrderName();
			table[2] = temp.getPath();
			model.addRow(table);
			index++;
		}
		this.table.updateUI();
	}
	public void deleteRow() {
	}

}
