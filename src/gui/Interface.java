package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import json.Json;
import json.ParseException;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private String path;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
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
	public Interface() {
		setTitle("D3fdg2Dot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChooseAFile = new JLabel("Choose a file to compile");
		lblChooseAFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChooseAFile.setBounds(5, 5, 472, 16);
		lblChooseAFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblChooseAFile);
		
		JButton btnSelectFile = new JButton("Select file");
		btnSelectFile.setBounds(388, 20,90, 30);
		contentPane.add(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"TXT File", "txt");
				fileChooser.setFileFilter(filter);
				int temp = fileChooser.showOpenDialog(null);
				if (temp == JFileChooser.APPROVE_OPTION) {
					path = fileChooser.getSelectedFile().getAbsolutePath();
					textField.setText(path);
				}
			}
		});
		
		JButton btnCompile = new JButton("Compile");
		btnCompile.setBounds(388, 55, 90, 30);
		contentPane.add(btnCompile);
		btnCompile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(path.isEmpty() || path == null) {
					JOptionPane.showMessageDialog(null, "No file choosed!");
				}
				else {
					String[] args = new String[1];
					args[0] = path;
					try {
						Json.main(args);
					} catch (ParseException | FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JLabel lblFile = new JLabel("File:");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFile.setBounds(15, 45, 56, 16);
		contentPane.add(lblFile);
		
		textField = new JTextField();
		textField.setBounds(57, 42, 300, 25);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
