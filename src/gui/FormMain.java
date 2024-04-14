package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.transaction.Transactional.TxType;

import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Employee;
import model.Project;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormMain extends JFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("ID:");
	private final JLabel lblNewLabel_1 = new JLabel("Name:");
	private final JLabel lblNewLabel_2 = new JLabel("Gen:");
	private final JLabel lblNewLabel_3 = new JLabel("Age:");
	private final JLabel lblNewLabel_4 = new JLabel("Project:");
	private final JTextField textID = new JTextField();
	private final JTextField textName = new JTextField();
	private final JTextField textAge = new JTextField();
	private final JComboBox comboBoxProject = new JComboBox();
	private final JCheckBox chckbxGen = new JCheckBox("Female");
	private final JButton btnDelete = new JButton("Delete");
	private final JButton btnUpdate = new JButton("Update");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable tableNhanVien = new JTable();

	String[] column = { "ID", "Name", "Gen", "Age", "Project" };
	private EmployeeDAO daoem = new EmployeeDAO();
	private ProjectDAO daopro = new ProjectDAO();
	private List<Employee> listemp = daoem.findALL();
	private List<Project> listpro = daopro.findALL();
	private DefaultTableModel model = new DefaultTableModel(column, 0);
	private DefaultComboBoxModel<Project> cbmodel = new DefaultComboBoxModel<Project>();
	private final JButton btnAdd = new JButton("Add");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain frame = new FormMain();
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

	public FormMain() {
		textID.setEditable(false);
		textID.setBounds(66, 8, 86, 20);
		textID.setColumns(10);
		initialize();
		for (Employee emp : listemp) {
			String gen = convertGen(emp.isGen());
			Object[] o = { emp.getId(), emp.getName(), gen, emp.getAge(), emp.getProject().getId() };
			model.addRow(o);
		}
		for (Project pro : listpro) {
			cbmodel.addElement(pro);
		}
		tableNhanVien.setModel(model);
		comboBoxProject.setModel(cbmodel);

		tableNhanVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tableNhanVien.getSelectedRow();
				if (row != -1) {
					Employee emp = listemp.get(row);
					textID.setText(emp.getId() + " ");
					textName.setText(emp.getName());
					chckbxGen.setSelected(emp.isGen());
					textAge.setText(emp.getAge() + " ");
					cbmodel.setSelectedItem(emp.getProject());
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textName.getText();
				Boolean gen = chckbxGen.isSelected();
				Project pro = (Project) cbmodel.getSelectedItem();
				try {
					int age = Integer.parseInt(textAge.getText());
					Employee emp = new Employee(pro, name, gen, age);
					if (daoem.add(emp)) {
						String gens = convertGen(gen);
						Object[] o = { emp.getId(), emp.getName(), gens, emp.getAge(), emp.getProject().getId() };
						model.addRow(o);
						JOptionPane.showMessageDialog(null, "Added success !");
					} else
						JOptionPane.showMessageDialog(null, "Add failed !");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid age");
				}
			}
		});

		btnAdd.setBounds(639, 7, 89, 23);

		contentPane.add(btnAdd);
	}

	private static String convertGen(boolean b) {
		String gen;
		if (b)
			gen = "Female";
		else
			gen = "Male";
		return gen;
	}

	private void initialize() {
		setTitle("Employee Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setBounds(10, 11, 46, 14);

		contentPane.add(lblNewLabel);
		lblNewLabel_1.setBounds(10, 36, 46, 14);

		contentPane.add(lblNewLabel_1);
		lblNewLabel_2.setBounds(10, 61, 46, 14);

		contentPane.add(lblNewLabel_2);
		lblNewLabel_3.setBounds(10, 86, 46, 14);

		contentPane.add(lblNewLabel_3);
		lblNewLabel_4.setBounds(10, 111, 46, 14);

		contentPane.add(lblNewLabel_4);

		contentPane.add(textID);
		textName.setColumns(10);
		textName.setBounds(66, 33, 296, 20);

		contentPane.add(textName);
		textAge.setColumns(10);
		textAge.setBounds(66, 83, 46, 20);

		contentPane.add(textAge);
		comboBoxProject.setBounds(66, 108, 296, 20);

		contentPane.add(comboBoxProject);
		chckbxGen.setSelected(true);
		chckbxGen.setBounds(62, 57, 97, 23);

		contentPane.add(chckbxGen);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableNhanVien.getSelectedRow();
				if (row != -1) {
					Employee emp = listemp.get(row);
					if (daoem.delete(emp.getId())) {
						listemp.remove(row);
						model.removeRow(row);
						JOptionPane.showMessageDialog(null, "Deleted !");
					} else
						JOptionPane.showMessageDialog(null, "Delete failed !");
				}

			}
		});
		btnDelete.setBounds(639, 32, 89, 23);

		contentPane.add(btnDelete);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=tableNhanVien.getSelectedRow();
				if(row!=-1) {
					Employee cu=listemp.get(row);
					FormUpdate fr=new FormUpdate(cu);
					fr.setVisible(true);
					Employee moi=fr.returnData();
					if(moi!=null) {
						listemp.set(row, moi);
						model.setValueAt(moi.getName(), row, 1);
						model.setValueAt(moi.isGen(), row, 2);
						model.setValueAt(moi.getAge(), row, 3);
						model.setValueAt(moi.getProject(), row, 4);
					}
					
				}
				else
					JOptionPane.showMessageDialog(null, "Khong co doi tuong duoc chon!");
			}
		});
		btnUpdate.setBounds(639, 57, 89, 23);

		contentPane.add(btnUpdate);
		scrollPane.setBounds(10, 136, 718, 248);

		contentPane.add(scrollPane);

		scrollPane.setViewportView(tableNhanVien);
	}
}
