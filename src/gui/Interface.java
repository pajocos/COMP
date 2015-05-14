package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import json.Json;
import json.ParseException;

import javax.swing.JTextArea;
import javax.swing.JRadioButton;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField filePath;
	private String path = null;
	private String graphPath = "";
	private String directory =  "";
	private JTextArea textArea;
	private JTextField graphvizPath;
	private JTextField fileName;

	ButtonGroup buttonGroup;
	JRadioButton rdbtnDot;
	JRadioButton rdbtnPdf;
	JRadioButton rdbtnPng;
	JRadioButton rdbtnPlain;
	private JLabel lblSelectFolderTo;
	private JTextField save;

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
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblChooseAFile = new JLabel("Choose a file to compile");
		lblChooseAFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChooseAFile.setBounds(30, 13, 257, 16);
		lblChooseAFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblChooseAFile);

		JButton btnSelectFile = new JButton("Select file");
		btnSelectFile.setBounds(327, 30, 100, 30);
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
					filePath.setText(path);
				}
			}
		});

		filePath = new JTextField();
		filePath.setBounds(12, 33, 300, 25);
		contentPane.add(filePath);
		filePath.setColumns(10);

		graphvizPath = new JTextField();
		graphvizPath.setText("If nothing selected it will not be used");
		graphvizPath.setToolTipText("");
		graphvizPath.setBounds(12, 85, 300, 25);
		contentPane.add(graphvizPath);
		graphvizPath.setColumns(10);

		JButton btnGraphViz = new JButton("Select Path");
		btnGraphViz.setBounds(327, 82, 100, 30);
		contentPane.add(btnGraphViz);
		btnGraphViz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"DOT.EXE", "exe");
				fileChooser.setFileFilter(filter);
				int temp = fileChooser.showOpenDialog(null);
				if (temp == JFileChooser.APPROVE_OPTION) {
					graphPath = fileChooser.getSelectedFile().getAbsolutePath();
					graphvizPath.setText(graphPath);
					rdbtnPdf.setEnabled(true);
					rdbtnPng.setEnabled(true);
					rdbtnPlain.setEnabled(true);
				}
			}
		});

		JLabel lblPathToDotexe = new JLabel(
				"Path to dot.exe in Graphviz folder");
		lblPathToDotexe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPathToDotexe.setBounds(50, 66, 241, 16);
		contentPane.add(lblPathToDotexe);

		fileName = new JTextField();
		fileName.setBounds(538, 33, 170, 25);
		contentPane.add(fileName);
		fileName.setColumns(10);

		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilename.setBounds(476, 37, 70, 16);
		contentPane.add(lblFilename);

		rdbtnDot = new JRadioButton("DOT");
		rdbtnDot.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnDot.setBounds(548, 73, 70, 25);
		contentPane.add(rdbtnDot);

		rdbtnPdf = new JRadioButton("PDF");
		rdbtnPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPdf.setBounds(625, 73, 70, 25);
		contentPane.add(rdbtnPdf);

		rdbtnPng = new JRadioButton("PNG");
		rdbtnPng.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPng.setBounds(548, 103, 70, 25);
		contentPane.add(rdbtnPng);

		rdbtnPlain = new JRadioButton("PLAIN");
		rdbtnPlain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPlain.setBounds(625, 103, 70, 25);
		contentPane.add(rdbtnPlain);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnPlain);
		buttonGroup.add(rdbtnDot);
		buttonGroup.add(rdbtnPdf);
		buttonGroup.add(rdbtnPng);
		rdbtnDot.setSelected(true);
		rdbtnPdf.setEnabled(false);
		rdbtnPng.setEnabled(false);
		rdbtnPlain.setEnabled(false);

		JLabel lblOutputType = new JLabel("Output type");
		lblOutputType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOutputType.setBounds(456, 88, 90, 16);
		contentPane.add(lblOutputType);

		// CONSOLE
		textArea = new JTextArea();
		textArea.setBounds(0, 173, 732, 380);
		contentPane.add(textArea);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 173, 732, 380);
		contentPane.add(scrollPane);

		JButton btnSelectPath = new JButton("Select Path");
		btnSelectPath.setBounds(327, 136, 100, 30);
		contentPane.add(btnSelectPath);
		btnSelectPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int temp = fileChooser.showOpenDialog(null);
				if (temp == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					directory = file.getAbsolutePath();
					save.setText(directory);
				} else {
					System.out.println("No Selection ");
				}
			}
		});

		lblSelectFolderTo = new JLabel("Select folder to save generated file");
		lblSelectFolderTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelectFolderTo.setBounds(46, 118, 241, 16);
		contentPane.add(lblSelectFolderTo);

		save = new JTextField();
		save.setBounds(12, 138, 300, 25);
		contentPane.add(save);
		save.setColumns(10);

		// COMPILE
		JButton btnCompile = new JButton("Compile");
		btnCompile.setBounds(525, 136, 100, 30);
		contentPane.add(btnCompile);
		btnCompile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				if (path == null) {
					JOptionPane.showMessageDialog(null, "No file choosed!");

				} else if (fileName.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Choose a name!");
				else if (directory.equals(""))
					JOptionPane
							.showMessageDialog(null, "No directory choosed!");
				else {
					String[] args = new String[5];
					args[0] = path;
					args[1] = directory;
					args[2] = fileName.getText();
					if (rdbtnDot.isSelected())
						args[3] = "dot";
					else if (rdbtnPdf.isSelected())
						args[3] = "pdf";
					else if (rdbtnPlain.isSelected())
						args[3] = "plain";
					else
						args[3] = "png";
					args[4] = graphPath;
					try {
						Json.main(args);
					} catch (ParseException | FileNotFoundException
							| UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		redirectSystemStreams();
	}

	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append(text);
			}
		});
	}

	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};
		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
