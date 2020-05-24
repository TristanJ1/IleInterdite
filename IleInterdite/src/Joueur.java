import java.util.ArrayList;

public class Joueur {

	 public enum elements {Air, Eau, Terre, Feu;}
	 public enum objets {Air, Eau, Terre, Feu,SacDeSable, Helicoptere ;}
	 public enum role {Pilote, Ingenieur, Explorateur, Navigateur, Plongeur, Messager;}
	
	 private CModele modele;
	 
	 private int numero;
	 private role pouvoir;
	 private int nbAction;
	 private ArrayList<objets> clees;
	 private ArrayList<elements> artefact;
	 private boolean pouvoirIngenieur = true;
	
	 private int x,y;
	 
	 
	 public Joueur(CModele modele,int numero,role r) {
		 this.modele = modele;
		 this.numero = numero;
		 this.pouvoir = r;
		 this.clees = new ArrayList<objets>();
		 this.artefact = new ArrayList<elements>();
		 this.nbAction = 3;
	 }
	
	 public void deplacement(String direction) { 
		 
		 if (this.nbAction >0) {
			 this.pouvoirIngenieur = true;
		 
			 modele.getCase(this.x,this.y).supprimerJoueur(this.numero);
			 
			 switch (direction) {
			 case "bas":  
				 if (this.y < CModele.HAUTEUR) {
					 if(modele.getCase(x, y+1).getEtat() != Case.inondation.Submerge || this.pouvoir == role.Plongeur) {
						 this.y++;
						 nbAction--;
					 }
				 }break;
			 case "droite":
				 if (this.x < CModele.LARGEUR) {
					 if(modele.getCase(x+1, y).getEtat() != Case.inondation.Submerge || this.pouvoir == role.Plongeur) {
						 this.x++;
						 nbAction--;
					 }
				 }break;
			 case "haut":
				 if (this.y > 1) {
					 if(modele.getCase(x, y-1).getEtat() != Case.inondation.Submerge || this.pouvoir == role.Plongeur) {
						 this.y--;
						 nbAction--;
					 }
				 }break;
			 case "gauche":
				 if (this.x > 1) {
					 if(modele.getCase(x-1, y).getEtat() != Case.inondation.Submerge || this.pouvoir == role.Plongeur) {
						 this.x--;
						 nbAction--;
					 }
				 }break;
				
			 }
			 
			 modele.getCase(this.x,this.y).ajouterJoueur(this.numero);
			 
			 System.out.println("Nbaction = " + this.nbAction);
		 }else {
			 System.out.println("erreur: plus assez d'actions");
		 }
	 }
	 
	 
	 public void asseche(int x,int y) {
		 
		 if (this.nbAction >0  || (this.pouvoir == role.Ingenieur && !this.pouvoirIngenieur)) {
		 
			 if(x > 0 && x < modele.LARGEUR+1 && y > 0 && y < modele.HAUTEUR+1) { //case existe
			 
				 if ((x == this.x && y == this.y)   //meme case
					|| (x == this.x && y == this.y + 1)  //bas
					|| (x == this.x + 1 && y == this.y)  //droite
					|| (x == this.x && y == this.y - 1) //haut
					|| (x == this.x - 1 && y == this.y)) {  //gauche
					 
					if( this.modele.getCase(x, y).asseche()) {
						
						if (this.pouvoir == role.Ingenieur) {
							if(this.pouvoirIngenieur) { 
								this.pouvoirIngenieur = false;
								this.nbAction--;
							}else this.pouvoirIngenieur = true;
						}else {
							this.nbAction--;
						}
						System.out.println("Nbaction = " + this.nbAction);
						}
					}		
			 }else {
				 System.out.println("erreur: case inexistante");
			 }
		 }else {
			 System.out.println("erreur: plus assez d'actions");
		 }
	 }
	 
	 public void donnerClee(objets e) {
		 if(this.clees.contains(e)) {
			 this.pouvoirIngenieur = true;
			 
			 if(e == objets.Helicoptere) {this.modele.getSelecteur().helicoptere();this.clees.remove(e);}
			 else if(e == objets.SacDeSable) {this.modele.getSelecteur().sac();this.clees.remove(e);}
			 else {
				 for (int i=0;i<modele.NBJOUEURS;i++) {
					 if((i != this.numero && this.modele.getCase(this.x, this.y).getJoueurs().contains(modele.getJoueurs(i))  ||   this.modele.getSelecteur().getCase().getJoueurs().contains(modele.getJoueurs(i)) && this.pouvoir == role.Messager    )){
						 this.clees.remove(e);
						 modele.getJoueurs(i).recoitClee(e);
						 this.nbAction--;
						 break;
					 } 
				 }
			 }
			 
		 }
		 
	 }
	 
	 public void recoitClee(objets e) {
		 this.clees.add(e);
	 }
	 
	 
	 public void gagneClee() {
		 int x = (int)(Math.random()*10);
		 if (x<2) {
			 this.clees.add(objets.Air);
		 }else if(x<4) {
			 this.clees.add(objets.Eau);
		 }else if(x<6) {
			 this.clees.add(objets.Feu);
		 }else if(x<8) {
			 this.clees.add(objets.Terre);
		 }else if(x==9){
			 this.clees.add(objets.Helicoptere);
		 }else {
			 this.clees.add(objets.SacDeSable);
		 }
	 }
	 
	 public void gagneArtefact() {
		 if (this.nbAction >0) {
			 
			 this.pouvoirIngenieur = true;
			 
			 if (modele.getCase(this.x, this.y).getSpeciale() == Case.element.Air) {
				 int nb = 0; 
				 for (objets clee: this.clees) {
					 if (clee == objets.Air) nb++;
				 }
				 if (nb >=4) {
					 this.artefact.add(elements.Air);
					 this.clees.remove(objets.Air);
					 this.clees.remove(objets.Air);
					 this.clees.remove(objets.Air);
					 this.clees.remove(objets.Air);
					 this.nbAction--;
					 System.out.println("Nbaction = " + this.nbAction);
					 System.out.println("Artefact recuperer: Air");
				 }else {
					 System.out.println("Il vous manque la clee: Air");
				 }
			 }
			 if (modele.getCase(this.x, this.y).getSpeciale() == Case.element.Eau) {
				 int nb = 0; 
				 for (objets clee: this.clees) {
					 if (clee == objets.Eau) nb++;
				 }
				 if (nb >=4) {
					 this.artefact.add(elements.Eau);
					 this.clees.remove(objets.Eau);
					 this.clees.remove(objets.Eau);
					 this.clees.remove(objets.Eau);
					 this.clees.remove(objets.Eau);
					 this.nbAction--;
					 System.out.println("Nbaction = " + this.nbAction);
					 System.out.println("Artefact recuperer: Eau");
				 }else {
					 System.out.println("Il vous manque la clee: Eau");
				 }
			 }
			 if (modele.getCase(this.x, this.y).getSpeciale() == Case.element.Feu) {
				 int nb = 0; 
				 for (objets clee: this.clees) {
					 if (clee == objets.Feu) nb++;
				 }
				 if (nb >=4) {
					 this.artefact.add(elements.Feu);
					 this.clees.remove(objets.Feu);
					 this.clees.remove(objets.Feu);
					 this.clees.remove(objets.Feu);
					 this.clees.remove(objets.Feu);
					 this.nbAction--;
					 System.out.println("Nbaction = " + this.nbAction);
					 System.out.println("Artefact recuperer: Feu");
				 }else {
					 System.out.println("Il vous manque la clee: Feu");
				 }
			 }
			 if (modele.getCase(this.x, this.y).getSpeciale() == Case.element.Terre) {
				 int nb = 0; 
				 for (objets clee: this.clees) {
					 if (clee == objets.Terre) nb++;
				 }
				 if (nb >=4) {
					 this.artefact.add(elements.Terre);
					 this.clees.remove(objets.Terre);
					 this.clees.remove(objets.Terre);
					 this.clees.remove(objets.Terre);
					 this.clees.remove(objets.Terre);
					 this.nbAction--;
					 System.out.println("Nbaction = " + this.nbAction);
					 System.out.println("Artefact recuperer: Terre");
				 }else {
					 System.out.println("Il vous manque la clee: Terre");
				 }
			 } 
		 }else {
			 System.out.println("erreur: plus assez d'actions");
		 }
	 }
	 
	 
	 //getter
	 public int getNbAction() {
		 return this.nbAction;
	 }
	 
	 public role getPouvoir() {
		 return this.pouvoir;
	 }
	 
	 public int getNumero() {
		 return this.numero;
	 }
	 
	 public int getX() {
		 return this.x;
	 }
	 
	 public int getY() {
		 return this.y;
	 }
	 
	 public ArrayList<Case> getCasesAdajacentes() {
		 ArrayList<Case> c = new ArrayList<Case>();
		 if (x < modele.LARGEUR) c.add(new Case(modele,x+1,y));
		 if (x > 0) c.add(new Case(modele,x-1,y));
		 if (y < modele.HAUTEUR) c.add(new Case(modele,x,y+1));
		 if (y > 0) c.add(new Case(modele,x,y-1));
		 return c;
	 }
	 
	 public ArrayList<objets> getClee() {
		 return this.clees;
	 }
	 
	 public ArrayList<elements> getArtefact() {
		 return this.artefact;
	 }
	 
	 //setter
	 public void setPosition(int x, int y) {
		 this.x = x;
		 this.y = y;
	 }
	 
	 public void setNbAction(int x) {
		 this.nbAction = x;
	 }
	 
	 public void moinsNbAction() {
		 this.nbAction--;
	 }
	
}
