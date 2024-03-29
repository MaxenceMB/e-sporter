package ihm;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import components.CoolScrollBar;
import components.PanelPopUp;
import components.PanelRound;
import controleur.ControleurDetailsTournoi;
import Images.ImagesIcons;
import modele.Arbitre;
import modele.Compte;
import modele.Equipe;
import modele.Joueur;
import modele.Tournoi;
import modele.TypeCompte;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.List;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

public class VueTournoi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tableEquipes;
	private JButton btnOuvrir;
	private JPanel panelNomsArbitres;
	private JPanel panelConteneurBoutons;
	private JPanel panelBoutonSupprimer;
	private JPanel panelBoutons;
	private PanelPopUp panelMessageArbitres;
	private int equipesSize;
	
	private Tournoi tournoi;

	private ControleurDetailsTournoi controleur;
	
	public VueTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
		this.equipesSize = 0;
//		this.controleur = new ControleurDetailsTournoi(this);
//		List<Equipe> equipesTournoi = new Tournoi().getEquipesTournoi(tournoi);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setResizable(false);
		setUndecorated(true);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);

		///// HEADER \\\\\
		Header header = new Header(this);
		header.setTitre(tournoi.getNomTournoi());
		contentPane.add(header, BorderLayout.NORTH);
		
		
		///// MENU BAR \\\\\
		afficherMenuBar(contentPane);
		
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
		JLabel lblTitre = new JLabel(tournoi.getNomTournoi());
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout(0, 15));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);

		
		///// PANEL INFOS DU TOURNOI \\\\\
		JPanel panelInfos = new JPanel();
		panelInfos.setBackground(Palette.DARK_GRAY);
		panelInfos.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelInfos, BorderLayout.NORTH);
		

		///// PANEL BULLES INFOS \\\\\
		JPanel panelBullesInfos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBullesInfos.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelBullesInfos.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelBullesInfos.setBackground(Palette.GRAY);
		panelInfos.add(panelBullesInfos, BorderLayout.CENTER);

		/********************************/
		/************ BULLES ************/
		/********************************/
		
		///// BULLE INFO PAYS \\\\\
		PanelRound panelPaysBorder = creerBordureBulleInfo();
		PanelRound panelPays = creerBulleInfo();
		ajouterLibelleBulle(panelPays, "Pays", ImagesIcons.BULLE_PAYS);
		ajouterInfoBulle(panelPays, tournoi.getPays().denomination());
		panelPaysBorder.add(panelPays);
		panelBullesInfos.add(panelPaysBorder);

		///// BULLE INFO NIVEAU \\\\\
		PanelRound panelNiveauBorder = creerBordureBulleInfo();
		PanelRound panelNiveau = creerBulleInfo();
		ajouterLibelleBulle(panelNiveau, "Niveau", ImagesIcons.BULLE_NIVEAU);
		ajouterInfoBulle(panelNiveau, tournoi.getNiveau().denomination());
		panelNiveauBorder.add(panelNiveau);
		panelBullesInfos.add(panelNiveauBorder);
		
		///// BULLE INFO NOMBRE ÉQUIPES \\\\\
		PanelRound panelEquipesBorder = creerBordureBulleInfo();
		PanelRound panelEquipes = creerBulleInfo();
		ajouterLibelleBulle(panelEquipes, "Équipes", ImagesIcons.BULLE_EQUIPES);
//		ajouterInfoBulle(panelEquipes, this.equipesSize + " participants");
		panelEquipesBorder.add(panelEquipes);
		panelBullesInfos.add(panelEquipesBorder);
		
		///// BULLE INFO DATES \\\\\
		PanelRound panelDatesBorder = creerBordureBulleInfo();
		PanelRound panelDates = creerBulleInfo();
		ajouterLibelleBulle(panelDates, "Dates", ImagesIcons.BULLE_DATES);
		ajouterInfoBulle(panelDates, Utilitaires.formaterDate(tournoi.getDateDebut()) + " au " + Utilitaires.formaterDate(tournoi.getDateFin()));
		panelDatesBorder.add(panelDates);
		panelBullesInfos.add(panelDatesBorder);

		///// BULLE INFO DATES \\\\\
		PanelRound panelVainqueurBorder = creerBordureBulleInfo();
		PanelRound panelVainqueur = creerBulleInfo();
		ajouterLibelleBulle(panelVainqueur, "Vainqueur", ImagesIcons.BULLE_VAINQUEUR);
		if (tournoi.getVainqueur() == null) {
			ajouterInfoBulle(panelVainqueur, "Aucun");
		} else {
			ajouterInfoBulle(panelVainqueur, tournoi.getVainqueur().getNom());
		}
		panelVainqueurBorder.add(panelVainqueur);
		panelBullesInfos.add(panelVainqueurBorder);
		
		/********************************/
		/********** FIN BULLES **********/
		/********************************/
		
		///// PANEL TABLE EQUIPES \\\\\
		JPanel panelTableEquipes = new JPanel();
		panelTableEquipes.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelTableEquipes.setBackground(Palette.GRAY);
		panelCenter.add(panelTableEquipes, BorderLayout.CENTER);
		panelTableEquipes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTableEquipes = new JScrollPane();
		scrollPaneTableEquipes.getViewport().setBackground(Palette.GRAY);
		scrollPaneTableEquipes.setBackground(Palette.GRAY);
		scrollPaneTableEquipes.setVerticalScrollBar(new CoolScrollBar());
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		// Table des équipes
		tableEquipes = new JTable();
		tableEquipes.setFont(Police.TABLEAU);
		tableEquipes.setRowHeight(30);
		tableEquipes.setSelectionBackground(Palette.LIGHT_PURPLE);
		tableEquipes.setBackground(Palette.DARK_GRAY);
		tableEquipes.setForeground(Palette.WHITE);
//		tableEquipes.addMouseListener(controleur);
		
		tableEquipes.getTableHeader().setBackground(Palette.GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.getTableHeader().setFont(Police.LABEL);
		tableEquipes.getTableHeader().setReorderingAllowed(false);
		tableEquipes.getTableHeader().setResizingAllowed(false);
		
		scrollPaneTableEquipes.setViewportView(tableEquipes);
		@SuppressWarnings("serial")
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Équipe", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
				/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		tableEquipes.setModel(modele);
//		afficherEquipes(equipesTournoi);
		
		// Assigner images au header
		mettreIconeDansHeader("Joueur 1", ImagesIcons.TOP);
		mettreIconeDansHeader("Joueur 2", ImagesIcons.JUNGLE);
		mettreIconeDansHeader("Joueur 3", ImagesIcons.MID);
		mettreIconeDansHeader("Joueur 4", ImagesIcons.SUPPORT);
		mettreIconeDansHeader("Joueur 5", ImagesIcons.BOTTOM);
		
		///// PANEL BOUTONS \\\\\
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnRetour = new JButton("Retour");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
//		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
//		btnRetour.addMouseListener(controleur);
		panelBoutons.add(btnRetour);

		///// PANEL ARBITRES \\\\\
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelArbitres.setBackground(Palette.GRAY);
		panelTableEquipes.add(panelArbitres, BorderLayout.SOUTH);
		panelArbitres.setLayout(new BorderLayout(0, 0));

		JLabel lblArbitres = new JLabel("Arbitres");
		lblArbitres.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
		lblArbitres.setForeground(Palette.WHITE);
		lblArbitres.setFont(Police.SOUS_TITRE);
		panelArbitres.add(lblArbitres, BorderLayout.NORTH);

		// Liste des noms et prénoms des arbitres
		panelNomsArbitres = new JPanel();
		panelNomsArbitres.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNomsArbitres.setBackground(Palette.GRAY);
		panelNomsArbitres.setBorder(new EmptyBorder(5, 0, 0, 0));
		panelArbitres.add(panelNomsArbitres);

		///// PANEL BOUTONS \\\\\
		panelConteneurBoutons = new JPanel();
		panelConteneurBoutons.setBackground(Palette.DARK_GRAY);
		panelConteneurBoutons.setLayout(new BorderLayout());
		panelCenter.add(panelConteneurBoutons, BorderLayout.SOUTH);
		
		panelBoutons = new JPanel();
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelConteneurBoutons.add(panelBoutons, BorderLayout.CENTER);
		
		panelBoutonSupprimer = new JPanel();
		panelBoutonSupprimer.setBackground(Palette.DARK_GRAY);
		panelBoutonSupprimer.setLayout(new FlowLayout());
		panelConteneurBoutons.add(panelBoutonSupprimer, BorderLayout.WEST);
		
		this.controleur = new ControleurDetailsTournoi(this);
		ajouterInfoBulle(panelEquipes, this.equipesSize + " participants");
		if(Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			tableEquipes.addMouseListener(this.controleur);
		}		
		btnRetour.addActionListener(this.controleur);
		btnRetour.addMouseListener(this.controleur);
		addWindowListener(controleur);
	}
	
	public void setEquipesSize(List<Equipe> equipes) {
		this.equipesSize = equipes.size();
	}

	private void afficherMenuBar(JPanel contentPane) {
		if(Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			MenuBar panelSide = new MenuBar(this);
			contentPane.add(panelSide, BorderLayout.WEST);
		}
	}

	public void afficherBoutons(List<Arbitre> arbitres, ControleurDetailsTournoi controleur) {
		afficherBoutonRetour(controleur);
		switch (tournoi.getStatut()) {
		case ATTENTE_EQUIPES:
			afficherBoutonImporter(controleur);
			afficherBoutonSupprimer(controleur);
			break;
		case ATTENTE_ARBITRES:
			afficherBoutonArbitres(controleur);
			afficherBoutonSupprimer(controleur);
			break;
		case A_VENIR:
			afficherBoutonOuvrir(controleur);
			if (tournoi.getDateDebut().after(new Date(System.currentTimeMillis()))) {
				btnOuvrir.setEnabled(false);
			}
			afficherBoutonSupprimer(controleur);
			break;
		case EN_COURS:
			afficherBoutonMDP(controleur);
			afficherBoutonGererPoule("Gérer la poule", controleur);
			afficherArbitresTournoi(arbitres);
			break;
		case FINALE:
		case ATTENTE_RESULTATS:
			afficherBoutonMDP(controleur);
			afficherBoutonGererPoule("Consulter la poule", controleur);
			afficherBoutonFinale("Gérer la finale", controleur);
			break;
		case TERMINE:
			afficherBoutonGererPoule("Consulter la poule", controleur);
			afficherBoutonFinale("Consulter la finale", controleur);
			break;
		case ANNULE:
			afficherBoutonGererPoule("Consulter la poule", controleur);
			break;
		}
	}
	
	public void afficherArbitresTournoi(List<Arbitre> arbitresTournoi) {
//	public void afficherArbitresTournoi() {
//		List<Arbitre> arbitresTournoi = new Tournoi().getArbitresTournoi(tournoi);
//		System.out.println(arbitresTournoi);
		if (arbitresTournoi.size() == 0) {
			afficherMessageArbitres();
		} else {
			panelNomsArbitres.removeAll();
			for (Arbitre arbitre : arbitresTournoi) {
				JLabel labelArbitre = new JLabel(arbitre.getNom() + " " + arbitre.getPrenom());
				labelArbitre.setForeground(Palette.WHITE);
				labelArbitre.setFont(Police.LABEL);
				labelArbitre.setBackground(Palette.DARK_GRAY);
				labelArbitre.setOpaque(true);
				labelArbitre.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE), Utilitaires.EMPTY_BORDER_BOUTONS));
				panelNomsArbitres.add(labelArbitre);
			}
		}
	}
	
	public void afficherMessageArbitres() {
		panelMessageArbitres = new PanelPopUp();
		panelMessageArbitres.setNormal("Aucun arbitre assigné");
		panelNomsArbitres.setLayout(new BorderLayout());
		panelNomsArbitres.add(panelMessageArbitres, BorderLayout.CENTER);
	}
	
	private void afficherBoutonSupprimer(ControleurDetailsTournoi controleur) {
		if (Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			JButton btnSupprimer = new JButton("Supprimer");
			btnSupprimer.setName("Supprimer");
			btnSupprimer.setBackground(Palette.GRAY);
			btnSupprimer.setForeground(Palette.ERREUR);
			btnSupprimer.setBorder(Utilitaires.BORDER_BOUTONS_DANGEREUX);
			btnSupprimer.setFont(Police.LABEL);
			btnSupprimer.setFocusable(false);
			btnSupprimer.addActionListener(controleur);
			panelBoutonSupprimer.add(btnSupprimer);
		}
	}
	
	public void afficherBoutonImporter(ControleurDetailsTournoi controleur) {
		JButton btnImporter = new JButton("Importer des équipes");
		btnImporter.setName("Importer");
		btnImporter.setBackground(Palette.GRAY);
		btnImporter.setForeground(Palette.WHITE);
		btnImporter.setBorder(Utilitaires.BORDER_BOUTONS);
		btnImporter.setFont(Police.LABEL);
		btnImporter.setFocusable(false);
		btnImporter.addActionListener(controleur);
		btnImporter.addMouseListener(controleur);
		panelBoutons.add(btnImporter);
	}
	
	public void afficherBoutonMDP(ControleurDetailsTournoi controleur) {
		if (Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			JButton btnMDP = new JButton("Mot de passe");
			btnMDP.setName("Mot de passe");
			btnMDP.setBackground(Palette.GRAY);
			btnMDP.setForeground(Palette.WHITE);
			btnMDP.setBorder(Utilitaires.BORDER_BOUTONS);
			btnMDP.setFont(Police.LABEL);
			btnMDP.setFocusable(false);
			btnMDP.addActionListener(controleur);
			btnMDP.addMouseListener(controleur);
			panelBoutons.add(btnMDP);
		}
	}
	
	public void afficherBoutonRetour(ControleurDetailsTournoi controleur) {
		if (Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			JButton btnRetour = new JButton("Retour");
			btnRetour.setName("Retour");
			btnRetour.setBackground(Palette.GRAY);
			btnRetour.setForeground(Palette.WHITE);
			btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
			btnRetour.setFont(Police.LABEL);
			btnRetour.addActionListener(controleur);
			btnRetour.addMouseListener(controleur);
			btnRetour.setFocusable(false);
			panelBoutons.add(btnRetour);
		}
	}
	
	public void afficherBoutonArbitres(ControleurDetailsTournoi controleur) {
		JButton btnImporter = new JButton("Attribuer des arbitres");
		btnImporter.setName("Arbitres");
		btnImporter.setBackground(Palette.GRAY);
		btnImporter.setForeground(Palette.WHITE);
		btnImporter.setBorder(Utilitaires.BORDER_BOUTONS);
		btnImporter.setFont(Police.LABEL);
		btnImporter.setFocusable(false);
		btnImporter.addActionListener(controleur);
		panelBoutons.add(btnImporter);
	}

	public void afficherBoutonOuvrir(ControleurDetailsTournoi controleur) {
		if (Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			btnOuvrir = new JButton("Ouvrir le tournoi");
			btnOuvrir.setName("Ouvrir");
			btnOuvrir.setBackground(Palette.GRAY);
			btnOuvrir.setForeground(Palette.WHITE);
			btnOuvrir.setBorder(Utilitaires.BORDER_BOUTONS);
			btnOuvrir.setFont(Police.LABEL);
			btnOuvrir.setFocusable(false);
			btnOuvrir.addActionListener(controleur);
			btnOuvrir.addMouseListener(controleur);
			panelBoutons.add(btnOuvrir);
		}
	}

	public void afficherBoutonGererPoule(String nomBouton, ControleurDetailsTournoi controleur) {
		JButton btnRetour = new JButton(nomBouton);
		btnRetour.setName("Poule");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		btnRetour.addActionListener(controleur);
		btnRetour.addMouseListener(controleur);
		panelBoutons.add(btnRetour);
	}

	public void afficherBoutonFinale(String nomBouton, ControleurDetailsTournoi controleur) {
		JButton btnFinale = new JButton(nomBouton);
		btnFinale.setName("Finale");
		btnFinale.setBackground(Palette.GRAY);
		btnFinale.setForeground(Palette.WHITE);
		btnFinale.setBorder(Utilitaires.BORDER_BOUTONS);
		btnFinale.setFont(Police.LABEL);
		btnFinale.setFocusable(false);
		btnFinale.addActionListener(controleur);
		btnFinale.addMouseListener(controleur);
		panelBoutons.add(btnFinale);
	}
	
	public void masquerBoutonSupprimer() {
		for (Component c : panelBoutonSupprimer.getComponents()) {
			panelBoutonSupprimer.remove(c);
		}
	}
	
	private PanelRound creerBordureBulleInfo() {
		PanelRound bordureBulle = new PanelRound();
		FlowLayout flowLayout = (FlowLayout) bordureBulle.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		bordureBulle.setBorder(new EmptyBorder(1, 1, 1, 1));
		bordureBulle.setRoundTopLeft(20);
		bordureBulle.setRoundTopRight(20);
		bordureBulle.setRoundBottomRight(20);
		bordureBulle.setBackground(Palette.WHITE);
		return bordureBulle;
	}
	
	private PanelRound creerBulleInfo() {
		PanelRound bulle = new PanelRound();
		bulle.setBorder(new EmptyBorder(10, 15, 10, 15));
		bulle.setRoundTopLeft(20);
		bulle.setRoundTopRight(20);
		bulle.setRoundBottomRight(20);
		bulle.setBackground(Palette.DARK_GRAY);
		bulle.setLayout(new BorderLayout(0, 0));
		return bulle;
	}
	
	private void ajouterLibelleBulle(PanelRound panelPays, String libelle, ImageIcon icone) {
		JLabel lblIconePays = new JLabel(" " + libelle);
		lblIconePays.setForeground(Palette.LIGHT_PURPLE);
		lblIconePays.setFont(Police.LABEL);
		lblIconePays.setIcon(icone);
		panelPays.add(lblIconePays, BorderLayout.NORTH);
	}
	
	private void ajouterInfoBulle(PanelRound bulle, String info) {
		JLabel lblPays = new JLabel(info);
		lblPays.setBorder(new EmptyBorder(2, 0, 0, 0));
		lblPays.setForeground(Palette.WHITE);
		lblPays.setFont(Police.TABLEAU);
		bulle.add(lblPays, BorderLayout.SOUTH);
	}
	
	public void setVisibleBoutonOuvrir(boolean visible) {
		this.btnOuvrir.setVisible(visible);
	}
	
	public void afficherMessageErreurArbitres() {
		this.panelMessageArbitres.setErreur("Il n'y a pas assez d'arbitres disponibles pour ouvrir ce tournoi");
	}

	private void mettreIconeDansHeader(String colonne, ImageIcon image) {
		tableEquipes.getColumn(colonne).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		               return new JLabel(image);
		        }
		    });
	}

	public void afficherEquipes(List<Equipe> equipesTournoi) {
		DefaultTableModel modele = (DefaultTableModel) tableEquipes.getModel();
		for (Equipe equipe : equipesTournoi) {
			List<Joueur> joueursEquipe = equipe.getJoueurs();
			modele.addRow(new Object[] {
				equipe.getNom(), joueursEquipe.get(0).getPseudo(), joueursEquipe.get(1).getPseudo(), joueursEquipe.get(2).getPseudo(), joueursEquipe.get(3).getPseudo(), joueursEquipe.get(4).getPseudo(),  
			});
		}
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
}
