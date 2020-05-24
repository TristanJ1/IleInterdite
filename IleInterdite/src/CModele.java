import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

class CModele extends Observable {
	
	
    /** On fixe la taille de la grille. */
    public static final int HAUTEUR=6, LARGEUR=6;
    public static final int NBJOUEURS = 4;   //entre 1 et 4
    
    
    /** On stocke un tableau de cases. */
    private Case[][] cases;
    
    private Joueur[] joueurs = new Joueur[NBJOUEURS]; 
    
    private Selecteur selecteur;
    private int tour;	
    private Case heliport;
    
    public boolean defaite = false;
    public int noyade = -1;
    public int tourAvantMessager = -1;
    public int nbActionAvantMessager = 3;
    
    /** Construction : on initialise un tableau de cases. */
    public CModele() {  	
    	
    ArrayList<Joueur.role> r = new ArrayList<Joueur.role>();
    r.add(Joueur.role.Pilote);
    r.add(Joueur.role.Ingenieur);
    r.add(Joueur.role.Explorateur);
    r.add(Joueur.role.Navigateur);
    r.add(Joueur.role.Plongeur);
    r.add(Joueur.role.Messager);
    
    for (int i=0;i<NBJOUEURS;i++) {
    	int nb;
    	do {
    		nb = (int)(Math.random()*6);
    	}while(nb>=r.size());
    
    	joueurs[i] = new Joueur(this,i,r.get(nb));
    	r.remove(nb);
    }
	 
    cases = new Case[LARGEUR][HAUTEUR];
	for(int i=0; i<LARGEUR; i++) {
	    for(int j=0; j<HAUTEUR; j++) {
	    	cases[i][j] = new Case(this,i, j);
	    }
	}
	init();
}
    
    public void init() {
    	
    	this.selecteur = new Selecteur(this);
    	this.tour = 0;
    	
    	int[] tab = {0,0,1,1,2,2,3,3,4}; 
    	Case c;
    	
		for(int i=0; i<tab.length; i++) {
			do {
    			c = getCase( (int)(Math.random()*LARGEUR+1), (int)(Math.random()*HAUTEUR+1));
    		}while(c.getSpeciale() != Case.element.Rien);
		   
		   switch (tab[i]) {
		   case 0: c.setSpeciale(Case.element.Feu);break;
		   case 1: c.setSpeciale(Case.element.Eau);break;
		   case 2: c.setSpeciale(Case.element.Air);break;
		   case 3: c.setSpeciale(Case.element.Terre);break;
		   default:{ c.setSpeciale(Case.element.Heliport); this.heliport = c;}
		    		
		    }
		}
		
		for(int i=0; i<NBJOUEURS; i++) {
			int x = (int)(Math.random()*LARGEUR+1);
			int y = (int)(Math.random()*HAUTEUR+1);
			c = getCase(x,y);
			c.ajouterJoueur(i);
			joueurs[i].setPosition(x, y);
		}
    }

    /**
     * Inondation de l'ile
     */
    public void inondationIle() {
	
    	for (int i=0;i<3;i++) {
    		Case c;
    		do {
    			c = getCase( (int)(Math.random()*LARGEUR+1), (int)(Math.random()*HAUTEUR+1));
    		}while(c.getEtat() == Case.inondation.Submerge);
    		
    		c.inonde();
    	}
  
    	
	/**
	 *le modèle ayant changé, on signale aux observateurs
	 * qu'ils doivent se mettre à jour.
	 */
	notifyObservers();
    }

    public void prochainTour() {
    	if (this.tour < NBJOUEURS-1) {
    		this.tour++;
    	}else {
    		this.tour = 0;
    	}
    	System.out.println("tour =" + this.tour);
    }
    
    /**
     * Une méthode pour renvoyer la case aux coordonnées choisies (sera
     * utilisée par la vue).
     */
    public Case getCase(int x, int y) {
	return cases[x-1][y-1];   //cases[x][y]  
    }
    
    public Joueur getJoueurs(int numero) {
    	return this.joueurs[numero];
    }
    
    public Selecteur getSelecteur() {
    	return this.selecteur;
    }
    
    public Case getHeliport() {
    	return this.heliport;
    }
    
    public int getTour() {
    	return this.tour;
    }
    
    public void setTour(int t) {
    	this.tour = t;
    }
      

}
