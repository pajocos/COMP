package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private String directory = "";
	private JTextArea textArea;
	private JTextField graphvizPath;
	private JTextField fileName;

	private ButtonGroup buttonGroup;
	private JRadioButton rdbtnDot;
	private JRadioButton rdbtnPdf;
	private JRadioButton rdbtnPng;
	private JRadioButton rdbtnPlain;
	private JLabel lblSelectFolderTo;
	private JTextField save;
	private JPanel outputPanel;
	private JPanel filesPanel;

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
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		filesPanel = new JPanel();
		filesPanel.setBounds(0, 0, 444, 173);
		contentPane.add(filesPanel);
		filesPanel.setLayout(null);
		
		outputPanel = new JPanel();
		outputPanel.setBounds(444, 0, 300, 173);
		contentPane.add(outputPanel);
		outputPanel.setLayout(null);
		outputType();
		
		//ALL BUTTONS
		buttons();

		//LABELS AND TEXTFIELD FOR PATHS
		paths();
				
		//FILENAME
		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilename.setBounds(100, 5, 100, 19);
		outputPanel.add(lblFilename);
		lblFilename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		fileName = new JTextField();
		fileName.setBounds(25, 29, 250, 25);
		outputPanel.add(fileName);
		fileName.setColumns(10);

		// CONSOLE
		textArea = new JTextArea();
		contentPane.add(textArea);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 180, 745, 385);
		contentPane.add(scrollPane);

		redirectSystemStreams();
	}

	private void paths() {
		//PATH TO GENERATED FILE
		lblSelectFolderTo = new JLabel("Select folder to save generated file");
		lblSelectFolderTo.setBounds(56, 59, 226, 19);
		filesPanel.add(lblSelectFolderTo);
		lblSelectFolderTo.setFont(new Font("Tahoma", Font.PLAIN, 15));

		save = new JTextField();
		save.setBounds(15, 83, 308, 25);
		filesPanel.add(save);
		save.setColumns(10);

		//FILE TO COMPILE
		JLabel lblChooseAFile = new JLabel("Choose a file to compile");
		lblChooseAFile.setBounds(92, 5, 154, 19);
		filesPanel.add(lblChooseAFile);
		lblChooseAFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChooseAFile.setHorizontalAlignment(SwingConstants.CENTER);

		filePath = new JTextField();
		filePath.setBounds(15, 29, 308, 25);
		filesPanel.add(filePath);
		filePath.setColumns(10);

		//GRAPHVIZ PATH
		JLabel lblPathToDotexe = new JLabel(
				"Path to dot.exe in Graphviz folder");
		lblPathToDotexe.setBounds(60, 113, 219, 19);
		filesPanel.add(lblPathToDotexe);
		lblPathToDotexe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		graphvizPath = new JTextField();
		graphvizPath.setBounds(15, 137, 308, 25);
		filesPanel.add(graphvizPath);
		graphvizPath.setText("If nothing selected it will not be used");
		graphvizPath.setToolTipText("");
		graphvizPath.setColumns(10);
	}

	private void outputType() {
		JLabel lblOutputType = new JLabel("Output type");
		lblOutputType.setBounds(105, 59, 80, 19);
		outputPanel.add(lblOutputType);
		lblOutputType.setFont(new Font("Tahoma", Font.PLAIN, 15));

		buttonGroup = new ButtonGroup();
		rdbtnDot = new JRadioButton("DOT");
		rdbtnDot.setBounds(12, 83, 59, 27);
		outputPanel.add(rdbtnDot);
		rdbtnDot.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnDot);
		rdbtnDot.setSelected(true);

		rdbtnPdf = new JRadioButton("PDF");
		rdbtnPdf.setBounds(83, 83, 55, 27);
		outputPanel.add(rdbtnPdf);
		rdbtnPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnPdf);
		rdbtnPdf.setEnabled(false);

		rdbtnPng = new JRadioButton("PNG");
		rdbtnPng.setBounds(150, 83, 57, 27);
		outputPanel.add(rdbtnPng);
		rdbtnPng.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnPng);
		rdbtnPng.setEnabled(false);

		rdbtnPlain = new JRadioButton("PLAIN");
		rdbtnPlain.setBounds(219, 83, 69, 27);
		outputPanel.add(rdbtnPlain);
		rdbtnPlain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(rdbtnPlain);
		rdbtnPlain.setEnabled(false);
	}

	private void buttons() {
		JButton btnGraphViz = new JButton("Select Path");
		btnGraphViz.setBounds(335, 137, 100, 25);
		filesPanel.add(btnGraphViz);
		btnGraphViz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select path to DOT.EXE");
				fileChooser.setAcceptAllFileFilterUsed(false);
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
		
		JButton btnSelectFile = new JButton("Select file");
		btnSelectFile.setBounds(335, 29, 100, 25);
		filesPanel.add(btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select file to compile");
				fileChooser.setAcceptAllFileFilterUsed(false);
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
		JButton btnSelectPath = new JButton("Select Path");
		btnSelectPath.setBounds(335, 83, 100, 25);
		filesPanel.add(btnSelectPath);
		btnSelectPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select destination folder");
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

		// COMPILE BUTTOn
		JButton btnCompile = new JButton("Compile");
		btnCompile.setBounds(100, 137, 100, 25);
		outputPanel.add(btnCompile);
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
