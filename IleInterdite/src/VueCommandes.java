import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Une classe pour représenter la zone contenant le bouton.
 *
 * Cette zone n'aura pas à être mise à jour et ne sera donc pas un observateur.
 * En revanche, comme la zone précédente, celle-ci est un panneau [JPanel].
 */
class VueCommandes extends JPanel implements KeyListener{
    /**
     * Pour que le bouton puisse transmettre ses ordres, on garde une
     * référence au modèle.
     */
    private CModele modele;

    /** Constructeur. */
    public VueCommandes(CModele modele) {
	this.modele = modele;
	
	/**
	 * On crée un nouveau bouton, de classe [JButton], en précisant le
	 * texte qui doit l'étiqueter.
	 * Puis on ajoute ce bouton au panneau [this].
	 */
	JButton boutonAvance = new JButton("Fin de Tour");
	this.add(boutonAvance);
	
	/**
	 * Le bouton, lorsqu'il est cliqué par l'utilisateur, produit un
	 * événement, de classe [ActionEvent].
	 *
	 * On a ici une variante du schéma observateur/observé : un objet
	 * implémentant une interface [ActionListener] va s'inscrire pour
	 * "écouter" les événements produits par le bouton, et recevoir
	 * automatiquements des notifications.
	 * D'autres variantes d'auditeurs pour des événements particuliers :
	 * [MouseListener], [KeyboardListener], [WindowListener].
	 *
	 * Cet observateur va enrichir notre schéma Modèle-Vue d'une couche
	 * intermédiaire Contrôleur, dont l'objectif est de récupérer les
	 * événements produits par la vue et de les traduire en instructions
	 * pour le modèle.
	 * Cette strate intermédiaire est potentiellement riche, et peut
	 * notamment traduire les mêmes événements de différentes façons en
	 * fonction d'un état de l'application.
	 * Ici nous avons un seul bouton réalisant une seule action, notre
	 * contrôleur sera donc particulièrement simple. Cela nécessite
	 * néanmoins la création d'une classe dédiée.
	 */	
	Controleur ctrl = new Controleur(modele);
	/** Enregistrement du contrôleur comme auditeur du bouton. */
	boutonAvance.addActionListener(ctrl);
	boutonAvance.addKeyListener(this);
	
	/**
	 * Variante : une lambda-expression qui évite de créer une classe
         * spécifique pour un contrôleur simplissime.
         *
         JButton boutonAvance = new JButton(">");
         this.add(boutonAvance);
         boutonAvance.addActionListener(e -> { modele.avance(); });
         *
         */

    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		int x = this.modele.getJoueurs(modele.getTour()).getX();
		int y = this.modele.getJoueurs(modele.getTour()).getY();
		
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.modele.getJoueurs(modele.getTour()).deplacement("haut");
			break;
		case KeyEvent.VK_DOWN:
			this.modele.getJoueurs(modele.getTour()).deplacement("bas");
			break;
		case KeyEvent.VK_LEFT:
			this.modele.getJoueurs(modele.getTour()).deplacement("gauche");
			break;
		case KeyEvent.VK_RIGHT:
			this.modele.getJoueurs(modele.getTour()).deplacement("droite");
			break;
		
			
		
		case KeyEvent.VK_E:
			this.modele.getJoueurs(modele.getTour()).asseche(x, y);
			break;
		case KeyEvent.VK_W:
			this.modele.getJoueurs(modele.getTour()).asseche(x, y-1);
			break;
		case KeyEvent.VK_S:
			this.modele.getJoueurs(modele.getTour()).asseche(x, y+1);
			break;
		case KeyEvent.VK_A:
			this.modele.getJoueurs(modele.getTour()).asseche(x-1, y);
			break;
		case KeyEvent.VK_D:
			this.modele.getJoueurs(modele.getTour()).asseche(x+1, y);
			break;
			
			
		case KeyEvent.VK_I:
			this.modele.getSelecteur().deplacement("haut");
			break;
		case KeyEvent.VK_K:
			this.modele.getSelecteur().deplacement("bas");
			break;
		case KeyEvent.VK_J:
			this.modele.getSelecteur().deplacement("gauche");
			break;
		case KeyEvent.VK_L:
			this.modele.getSelecteur().deplacement("droite");
			break;
			
		
			
		case KeyEvent.VK_P:
			this.modele.getJoueurs(modele.getTour()).gagneArtefact();
			break;
			
		case KeyEvent.VK_O:
			this.modele.getSelecteur().pouvoir();
			break;
			
		case KeyEvent.VK_U:
			this.modele.getSelecteur().explorateurAsseche();
			break;
			
			
			
		case KeyEvent.VK_1:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >0)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(0));
			break;
		case KeyEvent.VK_2:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >1)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(1));
			break;
		case KeyEvent.VK_3:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >2)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(2));
			break;
		case KeyEvent.VK_4:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >3)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(3));
			break;
		case KeyEvent.VK_5:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >4)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(4));
			break;
		case KeyEvent.VK_6:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >5)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(5));
			break;
		case KeyEvent.VK_7:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >6)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(6));
			break;
		case KeyEvent.VK_8:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >7)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(7));
			break;
		case KeyEvent.VK_9:
			if(this.modele.getJoueurs(modele.getTour()).getClee().size() >8)
			this.modele.getJoueurs(modele.getTour()).donnerClee(this.modele.getJoueurs(modele.getTour()).getClee().get(8));
			break;
			
		}
	}
		
		
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
