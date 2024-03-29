package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import Images.ImagesIcons;
import controleur.ControleurMenu;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class MenuBar extends JPanel {

	private static final long serialVersionUID = 1L;

	public MenuBar(JFrame parent) {
		
		ControleurMenu controleur = new ControleurMenu(parent);
		
		setBackground(Palette.DARK_GRAY);
		setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 5, (Color) new Color(32, 28, 44)), new EmptyBorder(25, 0, 25, 0)));
		setPreferredSize(new Dimension(125, 600));
		setLayout(new GridLayout(5, 0));
		
		///// PANEL EQUIPE \\\\\
		JPanel panelEquipe = new JPanel();
		panelEquipe.setName("Equipes");
		panelEquipe.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelEquipe.addMouseListener(controleur);
		panelEquipe.setBackground(Palette.DARK_GRAY);
		panelEquipe.setLayout(new BorderLayout(0, 0));
		add(panelEquipe);
		
		// Logo Equipe
		JLabel iconEquipe = new JLabel();
		iconEquipe.setHorizontalAlignment(SwingConstants.CENTER);
		iconEquipe.setIcon(ImagesIcons.EQUIPE_MENU);
		iconEquipe.setSize(20, 80);
		panelEquipe.add(iconEquipe, BorderLayout.CENTER);
		
		
		///// PANEL TOURNOIS \\\\\
		JPanel panelTournois = new JPanel();
		panelTournois.setName("Tournois");
		panelTournois.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelTournois.addMouseListener(controleur);
		panelTournois.setBackground(Palette.DARK_GRAY);
		panelTournois.setLayout(new BorderLayout(0, 0));
		add(panelTournois);
		
		// Logo Tournois
		JLabel iconTournois = new JLabel();
		iconTournois.setHorizontalAlignment(SwingConstants.CENTER);
		iconTournois.setIcon(ImagesIcons.TOURNOI_MENU);
		iconTournois.setSize(20, 80);
		panelTournois.add(iconTournois, BorderLayout.CENTER);
		
		
		///// PANEL ARBITRES \\\\\
		JPanel panelArbitres = new JPanel();
		panelArbitres.setName("Arbitres");
		panelArbitres.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelArbitres.addMouseListener(controleur);
		panelArbitres.setBackground(Palette.DARK_GRAY);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		add(panelArbitres);
		
		// Logo arbitres
		JLabel iconArbitres = new JLabel();
		iconArbitres.setHorizontalAlignment(SwingConstants.CENTER);
		iconArbitres.setIcon(ImagesIcons.ARBITRE_MENU);
		iconArbitres.setSize(20, 80);
		panelArbitres.add(iconArbitres, BorderLayout.CENTER);
		
		
		///// PANEL HISTORIQUE \\\\\
		JPanel panelHistorique = new JPanel();
		panelHistorique.setName("Historique");
		panelHistorique.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelHistorique.addMouseListener(controleur);
		panelHistorique.setBackground(Palette.DARK_GRAY);
		panelHistorique.setLayout(new BorderLayout(0, 0));
		add(panelHistorique);
		
		// Logo historique
		JLabel iconHistorique = new JLabel();
		iconHistorique.setHorizontalAlignment(SwingConstants.CENTER);
		iconHistorique.setIcon(ImagesIcons.HISTORIQUE_MENU);
		iconHistorique.setSize(20, 80);
		panelHistorique.add(iconHistorique, BorderLayout.CENTER);
		
		
		///// PANEL DECONNEXION \\\\\
		JPanel panelDeconnexion = new JPanel();
		panelDeconnexion.setName("Deconnexion");
		panelDeconnexion.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelDeconnexion.addMouseListener(controleur);
		panelDeconnexion.setBackground(Palette.DARK_GRAY);
		panelDeconnexion.setLayout(new BorderLayout(0, 0));
		add(panelDeconnexion);
		
		// Logo déco
		JLabel iconDeconnexion = new JLabel();
		iconDeconnexion.setHorizontalAlignment(SwingConstants.CENTER);
		iconDeconnexion.setIcon(ImagesIcons.LOGOUT_MENU);
		iconDeconnexion.setSize(20, 80);
		panelDeconnexion.add(iconDeconnexion, BorderLayout.CENTER);
	}

}
