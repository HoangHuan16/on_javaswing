package gui;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Employee;
import model.Project;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormUpdate extends JDialog {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("ID:");
	private final JLabel lblName = new JLabel("Name:");
	private final JLabel lblNewLabel_2 = new JLabel("Gen:");
	private final JLabel lblNewLabel_3 = new JLabel("Age: ");
	private final JLabel lblNewLabel_4 = new JLabel("Project: ");
	private final JTextField textID = new JTextField();
	private final JTextField textName = new JTextField();
	private final JCheckBox chckbxGen = new JCheckBox("Female");
	private final JTextField textAge = new JTextField();
	private final JComboBox comboBoxProject = new JComboBox();
	private final JButton btnOk = new JButton("OK");
	private final JButton btnCancel = new JButton("Cancel");
	
	private EmployeeDAO daoemp=new EmployeeDAO(); 
	private ProjectDAO daopro=new ProjectDAO();
	private List<Project> listpro=daopro.findALL();
	private Employee cu;
	private Employee moi=null;
	private DefaultComboBoxModel<Project> cbmodel=new DefaultComboBoxModel<Project>();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FormUpdate(Employee emp) {
		textID.setEditable(false);
		textID.setBounds(80, 11, 86, 20);
		textID.setColumns(10);
		initialize();
		setModal(true);
		this.cu=emp;
		for(Project pro:listpro) {
			cbmodel.addElement(pro);
		}
		comboBoxProject.setModel(cbmodel);
		textID.setText(emp.getId() + " ");
		textName.setText(emp.getName());
		chckbxGen.setSelected(true);
		textAge.setText(emp.getAge() + " ");
		cbmodel.setSelectedItem(emp.getProject());
		
		contentPane.add(textName);
		cbmodel.setSelectedItem(emp.getProject());
	}
	
	public void update() {
		moi=new Employee();
		moi.setName(textName.getName());
		moi.setGen(chckbxGen.isSelected());
		moi.setProject((Project) cbmodel.getSelectedItem());
		moi.setId(cu.getId());
		try {
			moi.setAge(Integer.parseInt(textAge.getText()));
			if(daoemp.update(moi)) {
				JOptionPane.showMessageDialog(null, "Cap nhat thanh cong!");
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Cap nhat that bai!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tuoi sai dinh dang!");
		}
		
	}
	
	public Employee returnData() {
		return moi;
	}
	private void initialize() {
		setTitle("Update");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setBounds(10, 11, 50, 14);
		
		contentPane.add(lblNewLabel);
		lblName.setBounds(10, 39, 50, 14);
		
		contentPane.add(lblName);
		lblNewLabel_2.setBounds(10, 67, 50, 14);
		
		contentPane.add(lblNewLabel_2);
		lblNewLabel_3.setBounds(10, 92, 50, 14);
		
		contentPane.add(lblNewLabel_3);
		lblNewLabel_4.setBounds(10, 123, 50, 14);
		
		contentPane.add(lblNewLabel_4);
		
		contentPane.add(textID);
		textName.setColumns(10);
		textName.setBounds(80, 39, 171, 20);
		
		contentPane.add(textName);
		chckbxGen.setBounds(80, 66, 96, 23);
		
		contentPane.add(chckbxGen);
		textAge.setColumns(10);
		textAge.setBounds(80, 92, 86, 20);
		
		contentPane.add(textAge);
		comboBoxProject.setBounds(81, 120, 170, 20);
		
		contentPane.add(comboBoxProject);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		btnOk.setBounds(342, 39, 89, 23);
		
		contentPane.add(btnOk);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(342, 71, 89, 23);
		
		contentPane.add(btnCancel);
	}
}
