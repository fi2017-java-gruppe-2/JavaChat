
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;

public class AnmeldeGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNutzername;
	private JTextField textFieldNutzername;
	private JLabel lblPasswort;
	private JPasswordField textFieldPasswort;
	private JButton btnAnmelden;
	private JButton btnRegistrieren;
	private JLabel lblFehlermeldung;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame. 
	 */
	public AnmeldeGui()
	{
		initialize();	

		AnmeldeControl anmeldeControl = new AnmeldeControl(this, lblFehlermeldung);
		
		btnAnmelden.addActionListener(
				e -> anmeldeControl.anmelden(textFieldNutzername.getText(), textFieldPasswort.getText()));
		
		btnRegistrieren.addActionListener(e ->{
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							RegistrierenGui frame = new RegistrierenGui();
							frame.setVisible(true);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
		});
	}

	private void initialize()
	{
		setTitle("Anmelden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 309);
		setBackground(SystemColor.controlDkShadow);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlDkShadow);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNutzername());
		contentPane.add(getTextFieldNutzername());
		contentPane.add(getLblPasswort());
		contentPane.add(getTextFieldPasswort());
		contentPane.add(getBtnAnmelden());
		contentPane.add(getBtnRegistrieren());
		contentPane.add(getLblFehlermeldung());
		this.setLocation(600, 0);
	}

	private JLabel getLblNutzername()
	{
		if (lblNutzername == null)
		{
			lblNutzername = new JLabel("Nutzername:");
			lblNutzername.setBounds(31, 28, 67, 20);
			lblNutzername.setForeground(Color.WHITE);
			lblNutzername.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
		}
		return lblNutzername;
	}

	private JTextField getTextFieldNutzername()
	{
		if (textFieldNutzername == null)
		{
			textFieldNutzername = new JTextField("Niklas");
			textFieldNutzername.setBounds(31, 66, 380, 26);
			textFieldNutzername.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldNutzername.setColumns(10);
		}
		return textFieldNutzername;
	}

	private JLabel getLblPasswort()
	{
		if (lblPasswort == null)
		{
			lblPasswort = new JLabel("Passwort:");
			lblPasswort.setBounds(31, 119, 51, 20);
			lblPasswort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblPasswort.setForeground(Color.WHITE);
		}
		return lblPasswort;
	}

	private JPasswordField getTextFieldPasswort()
	{
		if (textFieldPasswort == null)
		{
			textFieldPasswort = new JPasswordField("Niklas1234");
			textFieldPasswort.setBounds(31, 156, 380, 26);
			textFieldPasswort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldPasswort.setColumns(10);
		}
		return textFieldPasswort;
	}

	private JButton getBtnAnmelden()
	{
		if (btnAnmelden == null)
		{
			btnAnmelden = new JButton("Anmelden");
			btnAnmelden.setBounds(292, 205, 119, 29);
			btnAnmelden.setForeground(Color.WHITE);
			btnAnmelden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnAnmelden.setBackground(new Color(51, 153, 153));
		}
		return btnAnmelden;
	}
	private JButton getBtnRegistrieren() {
		if (btnRegistrieren == null) {
			btnRegistrieren = new JButton("Registrieren");
			btnRegistrieren.setForeground(Color.WHITE);
			btnRegistrieren.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnRegistrieren.setBackground(new Color(51, 153, 153));
			btnRegistrieren.setBounds(31, 205, 119, 29);
		}
		return btnRegistrieren;
	}
	private JLabel getLblFehlermeldung() {
		if (lblFehlermeldung == null) {
			lblFehlermeldung = new JLabel("");
			lblFehlermeldung.setFont(new Font("Rockwell Condensed", Font.PLAIN, 12));
			lblFehlermeldung.setBounds(31, 245, 380, 14);
		}
		return lblFehlermeldung;
	}
}
