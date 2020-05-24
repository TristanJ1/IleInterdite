
public class Selecteur {
	
	private CModele modele;
	private int x,y;
	
	public Selecteur(CModele modele) {
		this.modele = modele;
		this.x = 3;
		this.y = 3;
	}
	
	public void deplacement(String direction) { 
		 
		modele.getCase(this.x,this.y).supprimerSelecteur();
			 
		 switch (direction) {
		 case "bas":  
			 if (this.y < CModele.HAUTEUR) {
				 this.y++;
			 }break;
		 case "droite":
			 if (this.x < CModele.LARGEUR) {
				 this.x++;
			 }break;
		 case "haut":
			 if (this.y > 1) {
				 this.y--;
			 }break;
		 case "gauche":
			 if (this.x > 1) {
				this.x--;
			 }break;	
		 }
			 
	 modele.getCase(this.x,this.y).ajouterSelecteur();
	 }
	
	public void pouvoir() {
		
		switch(this.modele.getJoueurs(this.modele.getTour()).getPouvoir()){
			case Pilote:
				this.modele.getCase(this.modele.getJoueurs(this.modele.getTour()).getX(), this.modele.getJoueurs(this.modele.getTour()).getY()).supprimerJoueur(this.modele.getTour());
				this.modele.getCase(this.x, this.y).ajouterJoueur(modele.getTour());
				this.modele.getJoueurs(this.modele.getTour()).setPosition(this.x, this.y);
				this.modele.getJoueurs(this.modele.getTour()).moinsNbAction();
				break;
			case Explorateur:
				Joueur j = modele.getJoueurs(modele.getTour());
				
				if (j.getX() < modele.LARGEUR && j.getY() > 1) {  //hd
					if (this.getCase() == modele.getCase(j.getX()+1,j.getY()-1) && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge  ) {
						modele.getCase(this.x-1,this.y+1).supprimerJoueur(j.getNumero());
						j.setPosition(j.getX()+1, j.getY()-1);
						modele.getCase(this.x,this.y).ajouterJoueur(j.getNumero());
						j.moinsNbAction();
						break;
					}
				}
				if (j.getX() < modele.LARGEUR && j.getY() < modele.HAUTEUR) {  //bd
					if (this.getCase() == modele.getCase(j.getX()+1,j.getY()+1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge  ) {
						modele.getCase(this.x-1,this.y-1).supprimerJoueur(j.getNumero());
						j.setPosition(j.getX()+1, j.getY()+1);
						modele.getCase(this.x,this.y).ajouterJoueur(j.getNumero());
						j.moinsNbAction();
						break;
					}
				}
				if (j.getX() > 1 && j.getY() < modele.HAUTEUR) {  //bg
					if (this.getCase() == modele.getCase(j.getX()-1,j.getY()+1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge ) {
						modele.getCase(this.x+1,this.y-1).supprimerJoueur(j.getNumero());
						j.setPosition(j.getX()-1, j.getY()+1);
						modele.getCase(this.x,this.y).ajouterJoueur(j.getNumero());
						j.moinsNbAction();
						break;
					}
				}
				if (j.getX() > 1 && j.getY() > 1) {  //hg
					if (this.getCase() == modele.getCase(j.getX()-1,j.getY()-1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge ) {
						modele.getCase(this.x+1,this.y+1).supprimerJoueur(j.getNumero());
						j.setPosition(j.getX()-1, j.getY()-1);
						modele.getCase(this.x,this.y).ajouterJoueur(j.getNumero());
						j.moinsNbAction();
						break;
					}
				}
				break;
	
			case Navigateur: 
				if(!this.getCase().getJoueurs().isEmpty() && modele.getJoueurs(modele.getTour()).getNbAction() > 0) {
				Joueur joueur = this.getCase().getJoueurs().get(0); 
				modele.tourAvantMessager = modele.getTour();
				modele.nbActionAvantMessager = modele.getJoueurs(modele.getTour()).getNbAction()-1;
				modele.setTour(joueur.getNumero());
				joueur.setNbAction(1);
				}
			default: break;
		}
	}
	
	public void helicoptere() {
		this.modele.getCase(this.modele.getJoueurs(this.modele.getTour()).getX(), this.modele.getJoueurs(this.modele.getTour()).getY()).supprimerJoueur(this.modele.getTour());
		this.modele.getCase(this.x, this.y).ajouterJoueur(modele.getTour());
		this.modele.getJoueurs(this.modele.getTour()).setPosition(this.x, this.y);
	}
	
	public void sac() {
		this.modele.getCase(this.x, this.y).asseche();
	}
	
	public Case getCase() {
		return this.modele.getCase(this.x, this.y);
	}

	public void explorateurAsseche() {
		Joueur j = modele.getJoueurs(modele.getTour());
		
		if (j.getX() < modele.LARGEUR && j.getY() > 1) {  //hd
			if (this.getCase() == modele.getCase(j.getX()+1,j.getY()-1) && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge  ) {
				if(this.getCase().asseche()) j.moinsNbAction();
			}
		}
		if (j.getX() < modele.LARGEUR && j.getY() < modele.HAUTEUR) {  //bd
			if (this.getCase() == modele.getCase(j.getX()+1,j.getY()+1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge  ) {
				if(this.getCase().asseche()) j.moinsNbAction();
			}
		}
		if (j.getX() > 1 && j.getY() < modele.HAUTEUR) {  //bg
			if (this.getCase() == modele.getCase(j.getX()-1,j.getY()+1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge ) {
				if(this.getCase().asseche()) j.moinsNbAction();
			}
		}
		if (j.getX() > 1 && j.getY() > 1) {  //hg
			if (this.getCase() == modele.getCase(j.getX()-1,j.getY()-1)  && j.getNbAction() > 0 && this.getCase().getEtat() != Case.inondation.Submerge ) {
				if(this.getCase().asseche()) j.moinsNbAction();
			}
		}
		
	}
}
