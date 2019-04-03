
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

public class AnmeldeGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblNutzername;
	private JTextField textFieldNutzername;
	private JLabel lblPasswort;
	private JTextField textFieldPasswort;
	private JButton btnAnmelden;
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
	}

	private void initialize()
	{
		setTitle("Anmelden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblNutzername = new GridBagConstraints();
		gbc_lblNutzername.anchor = GridBagConstraints.WEST;
		gbc_lblNutzername.insets = new Insets(0, 0, 5, 5);
		gbc_lblNutzername.gridx = 0;
		gbc_lblNutzername.gridy = 0;
		contentPane.add(getLblNutzername(), gbc_lblNutzername);
		GridBagConstraints gbc_textFieldNutzername = new GridBagConstraints();
		gbc_textFieldNutzername.gridwidth = 2;
		gbc_textFieldNutzername.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNutzername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNutzername.gridx = 0;
		gbc_textFieldNutzername.gridy = 1;
		contentPane.add(getTextFieldNutzername(), gbc_textFieldNutzername);
		GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
		gbc_lblPasswort.anchor = GridBagConstraints.WEST;
		gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasswort.gridx = 0;
		gbc_lblPasswort.gridy = 2;
		contentPane.add(getLblPasswort(), gbc_lblPasswort);
		GridBagConstraints gbc_textFieldPasswort = new GridBagConstraints();
		gbc_textFieldPasswort.gridwidth = 2;
		gbc_textFieldPasswort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPasswort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPasswort.gridx = 0;
		gbc_textFieldPasswort.gridy = 3;
		contentPane.add(getTextFieldPasswort(), gbc_textFieldPasswort);
		GridBagConstraints gbc_lblFehlermeldung = new GridBagConstraints();
		gbc_lblFehlermeldung.anchor = GridBagConstraints.WEST;
		gbc_lblFehlermeldung.insets = new Insets(0, 0, 0, 5);
		gbc_lblFehlermeldung.gridx = 0;
		gbc_lblFehlermeldung.gridy = 4;
		contentPane.add(getLblFehlermeldung(), gbc_lblFehlermeldung);
		GridBagConstraints gbc_btnAnmelden = new GridBagConstraints();
		gbc_btnAnmelden.anchor = GridBagConstraints.WEST;
		gbc_btnAnmelden.gridx = 1;
		gbc_btnAnmelden.gridy = 4;
		contentPane.add(getBtnAnmelden(), gbc_btnAnmelden);
		this.setLocation(600, 0);
	}

	private JLabel getLblNutzername()
	{
		if (lblNutzername == null)
		{
			lblNutzername = new JLabel("Nutzername:");
		}
		return lblNutzername;
	}

	private JTextField getTextFieldNutzername()
	{
		if (textFieldNutzername == null)
		{
			textFieldNutzername = new JTextField("Niklas");
			textFieldNutzername.setColumns(10);
		}
		return textFieldNutzername;
	}

	private JLabel getLblPasswort()
	{
		if (lblPasswort == null)
		{
			lblPasswort = new JLabel("Passwort:");
		}
		return lblPasswort;
	}

	private JTextField getTextFieldPasswort()
	{
		if (textFieldPasswort == null)
		{
			textFieldPasswort = new JTextField("Niklas1234");
			textFieldPasswort.setColumns(10);
		}
		return textFieldPasswort;
	}

	private JButton getBtnAnmelden()
	{
		if (btnAnmelden == null)
		{
			btnAnmelden = new JButton("Anmelden");
		}
		return btnAnmelden;
	}

	private JLabel getLblFehlermeldung()
	{
		if (lblFehlermeldung == null)
		{
			lblFehlermeldung = new JLabel("");
		}
		return lblFehlermeldung;
	}
}
