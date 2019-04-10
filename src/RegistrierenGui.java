

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegistrierenGui extends JFrame implements FocusListener
{

	private JPanel contentPane;
	private JLabel lblNutzername;
	private JTextField textFieldNutzername;
	private JLabel lblPasswort;
	private JPasswordField textFieldPasswort1;
	private JLabel lblPasswortWiederholen;
	private JPasswordField textFieldPasswort2;
	private JButton btnRegistrieren;
	private JLabel lblStatus;
	private RegistrierenControl registrieren;

	/**
	 * Create the frame.
	 */
	public RegistrierenGui()
	{
		setTitle("Registrieren");
		initialize();
		
		registrieren = new RegistrierenControl(this);
		
		textFieldNutzername.addFocusListener(this);
		
		btnRegistrieren.addActionListener(e -> registrieren.registrieren(textFieldNutzername.getText(), textFieldPasswort1.getText(), textFieldPasswort2.getText(), lblStatus));
	}
		
	public void focusGained(FocusEvent e)
	{
		
	}
	
	public void focusLost(FocusEvent e)
	{
		if(registrieren.nutzernamePruefen(textFieldNutzername.getText()))
		{
			lblStatus.setText("Nutzername schon vergeben!");
		}
	}
	
	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 287);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlDkShadow);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNutzername());
		contentPane.add(getTextFieldNutzername());
		contentPane.add(getLblPasswort());
		contentPane.add(getTextFieldPasswort1());
		contentPane.add(getLblPasswortWiederholen());
		contentPane.add(getTextFieldPasswort2());
		contentPane.add(getBtnRegistrieren());
		contentPane.add(getLblStatus());
	}

	private JLabel getLblNutzername() {
		if (lblNutzername == null) {
			lblNutzername = new JLabel("Nutzername");
			lblNutzername.setForeground(Color.WHITE);
			lblNutzername.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblNutzername.setBounds(30, 21, 97, 14);
		}
		return lblNutzername;
	}
	private JTextField getTextFieldNutzername() {
		if (textFieldNutzername == null) {
			textFieldNutzername = new JTextField();
			textFieldNutzername.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldNutzername.setBounds(30, 47, 319, 20);
			textFieldNutzername.setColumns(10);
		}
		return textFieldNutzername;
	}
	private JLabel getLblPasswort() {
		if (lblPasswort == null) {
			lblPasswort = new JLabel("Passwort");
			lblPasswort.setForeground(Color.WHITE);
			lblPasswort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblPasswort.setBounds(30, 78, 116, 14);
		}
		return lblPasswort;
	}
	private JPasswordField getTextFieldPasswort1() {
		if (textFieldPasswort1 == null) {
			textFieldPasswort1 = new JPasswordField();
			textFieldPasswort1.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldPasswort1.setBounds(30, 103, 319, 20);
			textFieldPasswort1.setColumns(10);
		}
		return textFieldPasswort1;
	}
	private JLabel getLblPasswortWiederholen() {
		if (lblPasswortWiederholen == null) {
			lblPasswortWiederholen = new JLabel("Passwort wiederholen");
			lblPasswortWiederholen.setForeground(Color.WHITE);
			lblPasswortWiederholen.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblPasswortWiederholen.setBounds(30, 134, 166, 14);
		}
		return lblPasswortWiederholen;
	}
	private JPasswordField getTextFieldPasswort2() {
		if (textFieldPasswort2 == null) {
			textFieldPasswort2 = new JPasswordField();
			textFieldPasswort2.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldPasswort2.setBounds(30, 159, 319, 20);
			textFieldPasswort2.setColumns(10);
		}
		return textFieldPasswort2;
	}
	private JButton getBtnRegistrieren() {
		if (btnRegistrieren == null) {
			btnRegistrieren = new JButton("Registrieren");
			btnRegistrieren.setBackground(new Color(51, 153, 153));
			btnRegistrieren.setForeground(Color.WHITE);
			btnRegistrieren.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnRegistrieren.setBounds(296, 205, 100, 23);
		}
		return btnRegistrieren;
	}
	private JLabel getLblStatus() {
		if (lblStatus == null) {
			lblStatus = new JLabel("");
			lblStatus.setFont(new Font("Rockwell Condensed", Font.PLAIN, 12));
			lblStatus.setBounds(30, 214, 240, 14);
		}
		return lblStatus;
	}
}
