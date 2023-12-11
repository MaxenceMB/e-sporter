package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controleur.ControleurImportation;
import modele.Joueur;
import modele.Tournoi;

import java.awt.Color;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VueImportation extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modele;
	public static List<String[]> data;
	private JTable table;
	private JButton btnValider;
	private JLabel msgErreur;
	private JPanel panelMessage;
	
	private Tournoi tournoi;

	/**
	 * Create the frame.
	 */
	public VueImportation(Tournoi tournoi) {
		this.tournoi = tournoi;
		ControleurImportation controleur = new ControleurImportation(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 564);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(new Color(255, 255, 255));
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTitre.setBorder(new EmptyBorder(15, 0, 0, 0));
		
		JLabel titreImport = new JLabel("Importation des équipes");
		titreImport.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitre.add(titreImport);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(new Color(255, 255, 255));
		contentPane.add(panelCenter, BorderLayout.CENTER);
		
        Object[] columnsName = new Object [] {"", "Membres"};
        
        this.modele = new DefaultTableModel();
		panelCenter.setBorder(new EmptyBorder(50, 10, 0, 10));
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTable = new JPanel();
		panelTable.setBackground(new Color(255, 255, 255));
		panelCenter.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(modele);
		table.setRowHeight(25);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelTable.add(table, BorderLayout.CENTER);
		
		this.panelMessage = new JPanel();
		panelMessage.setBackground(new Color(255, 255, 255));
		panelTable.add(panelMessage, BorderLayout.NORTH);
		
		this.msgErreur = new JLabel(" ");
		msgErreur.setHorizontalAlignment(SwingConstants.CENTER);
		panelMessage.add(msgErreur);
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBackground(new Color(255, 255, 255));
		panelCenter.add(panelNorth, BorderLayout.NORTH);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(Color.WHITE);
        btnRetour.setForeground(Color.BLACK);
		panelNorth.add(btnRetour);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		
		JButton btnImportation = new JButton("Importer");
		btnImportation.setBackground(Color.WHITE);
        btnImportation.setForeground(Color.BLACK);
		panelNorth.add(btnImportation);
		btnImportation.addActionListener(controleur);
		btnImportation.setFocusable(false);
		
		btnValider = new JButton("Valider");
		btnValider.setBackground(Color.WHITE);
        btnValider.setForeground(Color.BLACK);
		panelNorth.add(btnValider);
		btnValider.addActionListener(controleur);	
		btnValider.setFocusable(false);
	}
	
	public DefaultTableModel getModel() {
		return modele;
	}
	
	public void newModel() {
		this.modele = new DefaultTableModel();
	}
	
	public void setMsgErreur(String erreur){
		msgErreur.setText(erreur);
	}
	
	public void setColorMessage(Color color) {
		panelMessage.setBackground(color);
	}
	
	public JTable getTable() {
		return table;
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}
}
