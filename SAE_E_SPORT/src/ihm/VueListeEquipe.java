package ihm;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.JTextFieldArrondi;
import controleur.ControleurListeEquipe;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import modele.Equipe;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JLabel;
import java.sql.Connection;
import javax.swing.JScrollPane;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Color;

public class VueListeEquipe extends JFrame {

	private List<Equipe> equipes;
	private JTextField searchBar;
	private JList<Object> listeEquipes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// A RETIRER PLUS TARD
		Ecran.setup();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection c = ConnectionJDBC.getConnection();
					List<Equipe> equipes = (new EquipeJDBC().getAll());
					VueListeEquipe frame = new VueListeEquipe(equipes);
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
	public VueListeEquipe(List<Equipe> equipes) {
		
		ControleurListeEquipe controleur = new ControleurListeEquipe(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle("Équipes");
		setResizable(false);
		

		///// PANEL PRINCIPAL \\\\\	
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// MENU BAR \\\\\
		JPanel panelSide = new JPanel();
		panelSide.setBackground(Palette.DARK_GRAY);
		panelSide.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Palette.GRAY));
		panelSide.setPreferredSize(new Dimension(125, 600));
		contentPane.add(panelSide, BorderLayout.WEST);
		
		
		
		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(25, 0, 25, 0));
		panelMain.setLayout(new BorderLayout(0, 0));
		panelMain.setBackground(Palette.DARK_GRAY);
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(800, 120));
		panelTop.setBackground(Palette.DARK_GRAY);
		panelTop.setBorder(new EmptyBorder(15, 100, 0, 100));
		panelTop.setLayout(new GridLayout());
		panelMain.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Équipes");
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout(0, 25));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);
	
		
		/// PANEL RECHERCHE \\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(14, 15, 15, 15));
		panelSearch.setBackground(Palette.GRAY);
		panelSearch.setLayout(new BorderLayout(5, 0));
		panelCenter.add(panelSearch, BorderLayout.NORTH);
		
		// Bouton valider
		JPanel panelValider = new JPanel();
		panelValider.setOpaque(false);
		panelValider.setLayout(new BoxLayout(panelValider, BoxLayout.X_AXIS));
		panelSearch.add(panelValider, BorderLayout.EAST);
		
		ImageIcon icon = new ImageIcon(VueListeEquipe.class.getResource("/Images/Search_Icon.png"));
		Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		JButton validateBtn = new JButton("");
		validateBtn.setIcon(icon);
		validateBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
		validateBtn.setBackground(Palette.WHITE);
		validateBtn.addActionListener(controleur);
		validateBtn.addMouseListener(controleur);
		panelValider.add(validateBtn);

		// Barre recherche
		searchBar = new JTextFieldArrondi();
		searchBar.setBackground(Palette.DARK_GRAY);
		searchBar.setForeground(Palette.WHITE);
		searchBar.setFont(Police.INPUT);
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		panelSearch.add(searchBar);
		
		
		// Panel liste
		JPanel panelListe = new JPanel();
		panelListe.setLayout(new BorderLayout(5, 0));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);

		JLabel lblHeader = new JLabel(String.format("%-5s %-50s", "Rank", "Nom de l'équipe"));
		lblHeader.setFont(Police.TABLEAU);
		lblHeader.setBorder(new EmptyBorder(5, 10, 5, 10));
		lblHeader.setForeground(Palette.WHITE);
		panelListe.add(lblHeader, BorderLayout.NORTH);
		
		// Liste des équipes
		List<String> nomEquipes = equipes.stream()
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList());
		
		JList<Object> listeEquipes = new JList<Object>(nomEquipes.toArray());
		listeEquipes.setFont(Police.TABLEAU);
		listeEquipes.setBackground(Palette.GRAY);
		listeEquipes.setForeground(Palette.WHITE);
		listeEquipes.setBorder(new EmptyBorder(10, 10, 10, 10));
		listeEquipes.addMouseListener(controleur);
		this.listeEquipes = listeEquipes;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeEquipes);
		scrollPane.setBorder(null);
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		
		// PANEL DU BOUTON
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton retour
		JButton btnRetour = new JButton("<html><body style='padding: 5px 25px;'>Retour</body></html>");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		panelBoutons.add(btnRetour);
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeEquipes(List<String> elementsFiltres) {
	    this.listeEquipes.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeEquipes.repaint();
	}

}
