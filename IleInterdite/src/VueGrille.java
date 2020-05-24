import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Une classe pour représenter la zone d'affichage des cases.
 *
 * JPanel est une classe d'éléments graphiques, pouvant comme JFrame contenir
 * d'autres éléments graphiques.
 *
 * Cette vue va être un observateur du modèle et sera mise à jour à chaque
 * nouvelle génération des cases.
 */
class VueGrille extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private CModele modele;
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    private final static int TAILLE = 50;
    
    Image imgfeu = null;
    Image imgeau = null;
    Image imgterre= null;
    Image imgair = null;
    Image imghelicoptere = null;
    Image imgsac = null;
    Image imgselecteur = null;
    Image imgjoueur1 = null;
    Image imgjoueur2 = null;
    Image imgjoueur3 = null;
    Image imgjoueur4 = null;
    
    

    /** Constructeur. */
    public VueGrille(CModele modele) {
	this.modele = modele;
	/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
	modele.addObserver(this);
	
	try {
		imgfeu = ImageIO.read(new File("feu.png"));
		imgeau = ImageIO.read(new File("eau.png"));
		imgterre = ImageIO.read(new File("terre.png"));
		imgair = ImageIO.read(new File("air.png"));
		imghelicoptere = ImageIO.read(new File("helicoptere.png"));
		imgsac = ImageIO.read(new File("sac.png"));
		imgselecteur = ImageIO.read(new File("selecteur.png"));
		imgjoueur1 = ImageIO.read(new File("joueur1.png"));
		imgjoueur2 = ImageIO.read(new File("joueur2.png"));
		imgjoueur3 = ImageIO.read(new File("joueur3.png"));
		imgjoueur4 = ImageIO.read(new File("joueur4.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	/**
	 * Définition et application d'une taille fixe pour cette zone de
	 * l'interface, calculée en fonction du nombre de cellules et de la
	 * taille d'affichage.
	 */
	Dimension dim = new Dimension(TAILLE*CModele.LARGEUR+100,
				      TAILLE*CModele.HAUTEUR+150);
	this.setPreferredSize(dim);
    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se content de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() { repaint(); }

    /**
     * Les éléments graphiques comme [JPanel] possèdent une méthode
     * [paintComponent] qui définit l'action à accomplir pour afficher cet
     * élément. On la redéfinit ici pour lui confier l'affichage des cellules.
     *
     * La classe [Graphics] regroupe les éléments de style sur le dessin,
     * comme la couleur actuelle.
     */
    public void paintComponent(Graphics g) {
	super.repaint();
	
	
	/** Pour chaque cellule... */
	for(int i=1; i<=CModele.LARGEUR; i++) {
	    for(int j=1; j<=CModele.HAUTEUR; j++) {
		/**
		 * ... Appeler une fonction d'affichage auxiliaire.
		 * On lui fournit les informations de dessin [g] et les
		 * coordonnées du coin en haut à gauche.
		 */
		paint(g, modele.getCase(i, j), (i-1)*TAILLE, (j-1)*TAILLE);
	    }
	}
	
	write(g);
}
    
    private void write(Graphics g) {
    	g.setColor(Color.LIGHT_GRAY);
    	g.fillRect(TAILLE*CModele.LARGEUR, 0, 3*TAILLE, CModele.HAUTEUR*TAILLE);
    	g.fillRect(0, TAILLE*CModele.HAUTEUR, (CModele.LARGEUR+3)*TAILLE, 12*TAILLE);
    	
    	Color[] tab = {Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
    	
    	//joueur
    	Font font = new Font("Courier", Font.BOLD, 17);
        g.setFont(font);
    	g.setColor(tab[modele.getTour()]);
    	g.drawString("Joueur",TAILLE*CModele.LARGEUR,20);
    	
    	//action
        g.setColor(Color.BLACK);
    	g.drawString("Action: " + modele.getJoueurs(modele.getTour()).getNbAction(),TAILLE*CModele.LARGEUR,40);
    	
    	//afficher noyade
    	if(modele.noyade != -1) {
    		Font s1 = new Font("Courier", Font.BOLD, 28);
            g.setFont(s1);
    		g.setColor(tab[modele.noyade]);
    		g.drawString("NOYADE", TAILLE*CModele.LARGEUR, 300);
    		g.setFont(font);
    	}
    	
    	
    	//artefact
    	int x = TAILLE*CModele.LARGEUR;
    	int y = 50;
    	int a=0;
    	int b=0;
    	int c=0;
    	int d=0;
    	for(int i=0;i<modele.NBJOUEURS;i++) {
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Air)) { a=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Eau)) {b=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Feu)) {c=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Terre)) {d=1;}
    	}
    	
    	if (a==1) { g.drawImage(imgair, x, y,30,30, this);y+=30;}
    	if (b==1) { g.drawImage(imgeau, x, y,30,30, this);y+=30;}
    	if (c==1) { g.drawImage(imgfeu, x, y,30,30, this);y+=30;}
    	if (d==1) { g.drawImage(imgterre, x, y,30,30, this);y+=30;}  
    	
    	
    	//victoire
    	if(a==1 && b==1 && c==1 && d==1 && modele.getHeliport().getJoueurs().size() == CModele.NBJOUEURS) {
    			g.setColor(Color.YELLOW);
    	    	g.fillRect(0, 0, TAILLE*CModele.LARGEUR+100, TAILLE*CModele.HAUTEUR+150);
    	    	
    	    	Font f = new Font("Courier", Font.BOLD, 30);
    	        g.setFont(f);
    	    	g.setColor(Color.BLACK);
    	    	g.drawString("VICTOIRE !",(TAILLE*CModele.LARGEUR)/2, (TAILLE*CModele.HAUTEUR+150)/2);
    	    	return;
    	}
    	
    	//defaite
    	if (modele.defaite) {
    		defaite(g);
    		return;
    	}
    	
    	
        y = TAILLE*CModele.HAUTEUR+20;
    	for(int i=0;i<modele.NBJOUEURS;i++) {
    		x = 110;
    		g.setColor(tab[i]);
    		
    		//role
    		switch(modele.getJoueurs(i).getPouvoir()) {
    		case Pilote: g.drawString("Pilote",0, y);break;
    		case Ingenieur: g.drawString("Ingenieur",0, y);break;
    		case Navigateur: g.drawString("Navigateur",0, y);break;
    		case Messager: g.drawString("Messager",0, y);break;
    		case Plongeur: g.drawString("Plongeur",0, y);break;
    		default: g.drawString("Explorateur",0, y);break;
    		}
    
	    	//inventaire
	    	for (Joueur.objets o: modele.getJoueurs(i).getClee()) {
	    		switch(o) {
	    		case Eau: g.drawImage(imgeau, x, y-20,30,30, this);break;
	        	case Feu: g.drawImage(imgfeu, x, y-20,30,30, this);break;
	        	case Terre: g.drawImage(imgterre, x, y-20,30,30, this);break;
	        	case Air: g.drawImage(imgair, x, y-20,30,30, this);break;
	        	case Helicoptere: g.drawImage(imghelicoptere, x, y-20,30,30, this);break;
	        	case SacDeSable: g.drawImage(imgsac, x, y-20,30,30, this);break;
	    		}
	    		x+=30;
	    	}
	    	y+=30;
    	}
    }
    
    public void defaite(Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, TAILLE*CModele.LARGEUR+100, TAILLE*CModele.HAUTEUR+150);
    	
    	Font f = new Font("Courier", Font.BOLD, 30);
        g.setFont(f);
    	g.setColor(Color.WHITE);
    	g.drawString("DEFAITE !",(TAILLE*CModele.LARGEUR)/2, (TAILLE*CModele.HAUTEUR+150)/2);
    }
    
    /**
     * Fonction auxiliaire de dessin d'une case.
     * Ici, la classe [Cellule] ne peut être désignée que par l'intermédiaire
     * de la classe [CModele] à laquelle elle est interne, d'où le type
     * [CModele.Cellule].
     * Ceci serait impossible si [Cellule] était déclarée privée dans [CModele].
     */
    private void paint(Graphics g, Case c, int x, int y) {
        /** Sélection d'une couleur. */
    	
    	switch (c.getEtat()) {
	    	case Sec: g.setColor(Color.WHITE);break;  //les couleurs que prennent les cases selon leurs etats
	    	case Inonde: g.setColor(Color.BLUE);break;
	    	case Submerge: g.setColor(Color.GRAY);
    	}
    	
    	
    	
    	
    	
        /** Coloration d'un rectangle. */
        g.fillRect(x, y, TAILLE, TAILLE);
        
        
		switch (c.getSpeciale()) {
        case Eau: g.drawImage(imgeau, x+5, y+5,30,30, this);break; //Affichage des cases speciales avec string
    	case Feu: g.drawImage(imgfeu, x+5, y+5,30,30, this);break;
    	case Terre: g.drawImage(imgterre, x+5, y+5,30,30, this);break;
    	case Air: g.drawImage(imgair, x+5, y+5,30,30, this);break;
    	case Heliport: g.drawImage(imghelicoptere, x+5, y+5,30,30, this);break;
    	default:break;
        }  
		
		if(c.aSelecteur()) g.drawImage(imgselecteur, x+5, y+5,30,30, this);
     
		ArrayList<Joueur> joueurs = c.getJoueurs();
		
		Image[] tab = {imgjoueur1,imgjoueur2,imgjoueur3,imgjoueur4};
		
		int nb = 0;
		for (int i=0;i<modele.NBJOUEURS;i++) {
			if (joueurs.contains(this.modele.getJoueurs(i))) {
				g.drawImage(tab[i], x+nb+5, y+5,30,30, this);
				nb+=10;
			}
		}
        
    }
}