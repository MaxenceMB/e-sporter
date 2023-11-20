package controleur;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;

import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;

public class ControleurListeEquipe implements MouseListener{
	private VueListeEquipe vue;

	public ControleurListeEquipe(VueListeEquipe vue) {
		this.vue = vue;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		JList list = (JList) e.getSource();
		if (e.getClickCount() == 2) {
			try {
			List<Equipe> equipes = (new EquipeJDBC(ConnectionJDBC.getConnection()).getAll());
			VueEquipe vue = new VueEquipe(equipes,new EquipeJDBC(ConnectionJDBC.getConnection()).getByNom((String)list.getSelectedValue()));
			vue.setVisible(true);
			this.vue.dispose();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
