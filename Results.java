import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Results extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Results frame = new Results();
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
	public Results() {
		setTitle("BIKE SHARING DEMAND\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Predicted count according to the mlp algorithm:");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 79, 346, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Predicted count according to the naiveBayes algorithm:\r\n");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 126, 379, 16);
		contentPane.add(lblNewLabel_1);
		
		BufferedReader fr;
		String MLPRes = null, NBRes = null;
		try {
			fr = new BufferedReader(new FileReader(
					"resultForFrame.txt"));
			MLPRes = fr.readLine();
			NBRes = fr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(NBRes + " " + MLPRes);
		JLabel lblNewLabel_3 = new JLabel(NBRes);
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_3.setForeground(Color.YELLOW);
		lblNewLabel_3.setBounds(376, 126, 56, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(MLPRes);
		lblNewLabel_4.setForeground(Color.YELLOW);
		lblNewLabel_4.setBackground(new Color(255, 200, 0));
		lblNewLabel_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(340, 79, 80, 16);
		contentPane.add(lblNewLabel_4);
	}
}
