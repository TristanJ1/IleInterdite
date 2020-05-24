import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe pour notre contrôleur rudimentaire.
 * 
 * Le contrôleur implémente l'interface [ActionListener] qui demande
 * uniquement de fournir une méthode [actionPerformed] indiquant la
 * réponse du contrôleur à la réception d'un événement.
 */
class Controleur implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    CModele modele;
    int tourAvantNoyade = -1;
    int noyade = -1;
    
    public Controleur(CModele modele) { this.modele = modele;}
    
    /**
     * Action effectuée à réception d'un événement 
     */
    public void actionPerformed(ActionEvent e) {
    	if(modele.noyade == -1 && modele.tourAvantMessager == -1) {
    		modele.inondationIle();  //l'action a realiser quand on appuie sur le bouton
    		modele.getJoueurs(modele.getTour()).gagneClee();
    		modele.getJoueurs(modele.getTour()).getClee();
    		modele.prochainTour();
    	}
        
        //defaite
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for(int i=1; i<=CModele.LARGEUR; i++) {
    	    for(int j=1; j<=CModele.HAUTEUR; j++) {
    	    	switch(modele.getCase(i, j).getSpeciale()) {
    	    	case Heliport:if(modele.getCase(i, j).getEtat() == Case.inondation.Submerge)  {modele.defaite = true;System.out.println("L'heliport a coule");}
    	    	case Feu:if(modele.getCase(i, j).getEtat() != Case.inondation.Submerge) a=1;
    	    	case Eau:if(modele.getCase(i, j).getEtat() != Case.inondation.Submerge) b=1;
    	    	case Terre:if(modele.getCase(i, j).getEtat() != Case.inondation.Submerge) c=1;
    	    	case Air:if(modele.getCase(i, j).getEtat() != Case.inondation.Submerge) d=1;
    	    	}  
    	    }
        }
        
        int a1=0;
    	int b1=0;
    	int c1=0;
    	int d1=0;
    	for(int i=0;i<modele.NBJOUEURS;i++) {
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Air)) { a1=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Eau)) {b1=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Feu)) {c1=1;}
    		if(modele.getJoueurs(i).getArtefact().contains(Joueur.elements.Terre)) {d1=1;}
    	}
        
        if ((a==0 && a1==0) || (b==0 && b1==0) || (c==0 && c1==0) || (d==0 && d1==0)) {  
        	System.out.println("Impossible de recuperer l'un des elements");
        	modele.defaite = true;
        }
        
        //noyade
        for (int i=0;i<modele.NBJOUEURS;i++) {
        	ArrayList<Case> adjacente = modele.getJoueurs(i).getCasesAdajacentes();
        	boolean libre = false;
        	if(modele.getCase(modele.getJoueurs(i).getX(), modele.getJoueurs(i).getY()).getEtat() == Case.inondation.Submerge) {  
        		if (modele.noyade == i) modele.defaite = true;
        		for (int j = 0; j < adjacente.size(); j++) {
        			if(adjacente.get(j).getEtat() != Case.inondation.Submerge) {
        				libre = true;
        			}
        		}
        		if(!libre) modele.defaite = true;
        			
        		modele.noyade = i;
        		System.out.println("joueur " + i + " va se noyer");
        		this.tourAvantNoyade = modele.getTour();
        		modele.setTour(i);
        		modele.getJoueurs(i).setNbAction(1);
        		return;
        	}
        }
        //retour au cours normale de la partie
        if(modele.noyade != -1 ) {
        	modele.setTour(this.tourAvantNoyade);
        	modele.noyade = -1;
        }
        
        if(modele.tourAvantMessager != -1) {
        	modele.setTour(modele.tourAvantMessager);
        	modele.tourAvantMessager = -1;
        	modele.getJoueurs(modele.getTour()).setNbAction(modele.nbActionAvantMessager);
        }else {
        	modele.getJoueurs(modele.getTour()).setNbAction(3);
        }
    }
}
