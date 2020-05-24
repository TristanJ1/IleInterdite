import java.util.ArrayList;

public class Case {
	 /** On conserve un pointeur vers la classe principale du modèle. */
    private CModele modele;
    
    public enum inondation {Sec, Inonde, Submerge;}
    public enum element {Air, Eau, Terre, Feu, Heliport, Rien;}
    
    public ArrayList<Joueur> joueurs;
    private inondation etat;
    private element speciale; 
    private boolean selecteur;
    private final int x, y;  //coordonnee
    
    
    public Case(CModele modele, int x, int y) {
        this.modele = modele;
        this.selecteur = false;
        this.joueurs = new ArrayList<>();
        this.etat = inondation.Sec;
        this.speciale = element.Rien;
        this.x = x; this.y = y;
    }
    
    public void inonde() {
    	switch (this.etat) {
    	case Sec: this.etat = inondation.Inonde; break;
    	case Inonde: this.etat = inondation.Submerge;break;
    	case Submerge: System.out.println("erreur: case n'existe plus");	
    	}
    }
    
    public boolean asseche() {
    	switch (this.etat) {
    	case Inonde: this.etat = inondation.Sec;return true;
    	case Submerge: System.out.println("erreur: case deja submerger");return false;	
    	default: System.out.println("erreur: case deja assecher");return false;
    	}
    }
    
    public inondation getEtat() {
    	return this.etat;
    }
    
    public element getSpeciale() {
    	return this.speciale;
    }
    
    public int getX(){
    	return this.x+1;
    }
    
    public int getY(){
    	return this.y+1;
    }
    
    public ArrayList<Joueur> getJoueurs(){
    	return this.joueurs;
    }
    
    public void ajouterJoueur(int numero) {
    	this.joueurs.add(this.modele.getJoueurs(numero));
    }
    
    public void supprimerJoueur(int numero) {
    	this.joueurs.remove(this.modele.getJoueurs(numero));
    }
    
    public void ajouterSelecteur() {
    	this.selecteur = true;
    }
    
    public void supprimerSelecteur() {
    	this.selecteur = false;
    }
      
    public void setEtat(inondation i) {
    	this.etat = i;
    }
    
    public void setSpeciale(element s) {
    	this.speciale = s;
    }
    
    public boolean aSelecteur() {
    	return this.selecteur;
    }
    
}
