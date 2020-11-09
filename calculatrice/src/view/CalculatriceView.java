package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculatriceView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3766926449776833840L;
	private JPanel globalPanel;
	private JPanel buttonPanel;
	private JTextField input;
	private JTextArea output;
	private JButton resetButton;
	private JButton computeButton;

	private Dimension frameDimension = new Dimension(400, 400);
	private Dimension inputDimension = new Dimension(400, 150);
	private Dimension outputDimension = new Dimension(400, 150);
	private Dimension buttonDimension = new Dimension(200, 100);
	private Dimension buttonPanelDimension = new Dimension(400, 100);

	// Police pour saisir l'input et afficher l'output
	Font police = new Font("Calibri", Font.PLAIN, 30);

	public CalculatriceView() {
		this.setSize(frameDimension);
		this.setTitle("Calculatrice par Baptiste Thepault");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initView();
		this.setContentPane(globalPanel);
		this.setVisible(true);
	}

	// Initialise les différents éléments graphiques de la vue
	public void initView() {
		globalPanel = new JPanel();
		globalPanel.setLayout(new BorderLayout());
		globalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setPreferredSize(buttonPanelDimension);

		input = new JTextField();
		input.setPreferredSize(inputDimension);
		input.setFont(police);

		// TODO Aligner le texte de sortie au centre. Utiliser un JTextPane - StyleConstants.ALIGN_CENTER ou un JScrollPane ?
		output = new JTextArea();
		output.setPreferredSize(outputDimension);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		output.setFont(police);

		resetButton = new JButton("<html><center>Clear</center></html>");
		resetButton.setFont(police);
		resetButton.setForeground(Color.RED);
		resetButton.setPreferredSize(buttonDimension);

		computeButton = new JButton("<html><center>=</center></html>");
		computeButton.setFont(police);
		computeButton.setForeground(Color.BLUE);
		computeButton.setPreferredSize(buttonDimension);

		buttonPanel.add(resetButton, BorderLayout.WEST);
		buttonPanel.add(computeButton, BorderLayout.EAST);

		globalPanel.add(input, BorderLayout.NORTH);
		globalPanel.add(buttonPanel, BorderLayout.CENTER);
		globalPanel.add(output, BorderLayout.SOUTH);
	}

	// Renvoie le bouton reset de la vue
	public JButton getResetButton() {
		return resetButton;
	}

	// Renvoie le bouton compute de la vue
	public JButton getComputeButton() {
		return computeButton;
	}

	// Nettoie le texte présent dans l'input et l'output
	public void reset() {
		input.setText("");
		output.setText("");
	}

	// Renvoie le texte présent dans l'input
	public String getInputText() {
		return input.getText();
	}

	// Affiche le résultat souhaité dans le JTextArea d'output
	public void displayResult(String result) {
		output.setText(result);
	}
}
