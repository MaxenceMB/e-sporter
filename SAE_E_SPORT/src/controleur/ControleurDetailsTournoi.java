package controleur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import ihm.Ecran;
import ihm.VueEquipe;
import ihm.VueFinale;
import ihm.VueGestionDeLaPoule;
import ihm.VueImportation;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import ihm.VueListeArbitre;
import modele.Arbitre;
import modele.Compte;
import modele.Equipe;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;

public class ControleurDetailsTournoi implements ActionListener, MouseListener, WindowListener {
	
	private VueTournoi vue;
	private Tournoi modele;

	public ControleurDetailsTournoi(VueTournoi vue) {
		this.modele = new Tournoi();
		this.vue = vue;
		List<Equipe> equipes = this.modele.getEquipesTournoi(this.vue.getTournoi());
		this.vue.afficherEquipes(equipes);
		this.vue.setEquipesSize(equipes);
		List<Arbitre> arbitres = this.modele.getArbitresTournoi(this.vue.getTournoi());
		this.vue.afficherBoutons(arbitres, this);
		this.vue.afficherArbitresTournoi(arbitres);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getName().equals("Importer")) {
			Ecran.update(this.vue);
			
			VueImportation vueImportation = new VueImportation(vue.getTournoi());
			vueImportation.setVisible(true);
			
		} else if (bouton.getText().equals("Supprimer")) {
			int choix = afficherPopUpConfirmation(); 
			
			if (choix == JOptionPane.YES_OPTION) {
				Ecran.update(this.vue);	
				this.modele.supprimerCompteArbitres(this.vue.getTournoi());
				this.modele.supprimerTournoi(this.vue.getTournoi());
				
				VueListeTournois vueTournois = new VueListeTournois(this.modele.getTousLesTournois());
				vueTournois.setVisible(true);
			} 			
			
		} else if (bouton.getName().equals("Arbitres")) {
			Ecran.update(this.vue);	
			
			VueListeArbitre vueArbitres = new VueListeArbitre(new Arbitre().getTousLesArbitres(), true, this.vue.getTournoi()); 
			vueArbitres.setVisible(true);
			
		} else if (bouton.getName().equals("Retour")) {
			Ecran.update(this.vue);	
			
			VueListeTournois vue = new VueListeTournois(new Tournoi().getTousLesTournois());
			vue.setVisible(true);
			
		} else if (bouton.getName().equals("Poule")) {
			Ecran.update(this.vue);		
			
	        VueGestionDeLaPoule frame = new VueGestionDeLaPoule(this.vue.getTournoi());
	        frame.setVisible(true);
	        
		} else if (bouton.getName().equals("Ouvrir")) {
			this.modele.changerStatutTournoi(vue.getTournoi(), Statut.EN_COURS);
			this.vue.getTournoi().setStatut(Statut.EN_COURS);
			this.vue.getTournoi().generationPoule();
			this.vue.setVisibleBoutonOuvrir(false);
			this.vue.afficherBoutonMDP(this);
			this.vue.afficherBoutonGererPoule("Gérer la poule",this);
			this.vue.masquerBoutonSupprimer();
			
		} else if (bouton.getName().equals("Finale")) {
			Ecran.update(this.vue);		
			
			VueFinale vueFinale = new VueFinale(this.vue.getTournoi());
			vueFinale.setVisible(true);
		} else if (bouton.getName().equals("Mot de passe")) {
            String id = "Login : "+vue.getTournoi().getCompte().getLogin()+"\n"+"Mot de passe : "+vue.getTournoi().getCompte().getMotDePasse();
            afficherPopUpMotDePasse(id);
		}
	}

	private void afficherPopUpMotDePasse(String id) {
		JTextArea textArea = contenuPopup(id);
		Object paneBG = UIManager.get("OptionPane.background");
		Object panelBG = UIManager.get("Panel.background");
		UIManager.put("OptionPane.background", new Color(255,0,0));
		UIManager.put("Panel.background", new Color(255,0,0));

		UIManager.put("OptionPane.background", paneBG);
		UIManager.put("Panel.background", panelBG);
		JOptionPane.showMessageDialog(null, textArea, "Mot de passe", JOptionPane.INFORMATION_MESSAGE);
	}

	private JTextArea contenuPopup(String id) {
		JTextArea textArea = new JTextArea(id);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBackground(new Color(240, 236, 236));
		return textArea;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				try {
					Equipe equipeBDD = new Equipe();
		            JTable table = (JTable) e.getSource();
		            int row = table.getSelectedRow();
		            List<Equipe> equipes = (equipeBDD.getToutesLesEquipes());
					
		            Ecran.update(this.vue);
					VueEquipe vue = new VueEquipe(equipes, equipeBDD.getEquipeParNom(table.getValueAt(row, 0).toString()), this.vue.getTournoi());
					vue.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	         }
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			b.setBackground(b.getBackground().brighter());
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			b.setBackground(b.getBackground().darker());
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}	
	}
	
	private int afficherPopUpConfirmation() {
		String[] options = { "Oui", "Non"}; 
		int choix = JOptionPane.showOptionDialog( 
		        null,
		        "Voulez vous supprimer ce tournoi ?",
		        "Suppression du tournoi",
		        JOptionPane.YES_NO_OPTION,
		        JOptionPane.QUESTION_MESSAGE,
		        null,
		        options,
		        options[1] 
		);
		return choix;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		Ecran.closeLast();
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
