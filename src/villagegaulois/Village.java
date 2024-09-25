package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) { 
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        this.marche = new Marche(nbEtals); 
    }
	private static class Marche {
        private Etal[] etals;
        private Marche(int nbEtals) {
            etals = new Etal[nbEtals];
            for (int i = 0; i < nbEtals; i++) {
                etals[i] = new Etal();  
            }
        }
        private void libererEtal(int indiceEtal) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].libererEtal();
				System.out.println("L'étal " + indiceEtal + " a été libéré.");
			} else {
				System.out.println("L'indice d'étal est invalide.");
			}
		}
        private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal >= 0 && indiceEtal < etals.length) {
                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit); 
                System.out.println(vendeur.getNom() + " s'est installé à l'étal " + indiceEtal + " pour vendre " + nbProduit + " " + produit + "(s).");
            } else {
                System.out.println("L'indice d'étal est invalide.");
            }
        }
        private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) { 
					return i;
				}
			}
			return -1;
		}
        private Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsAvecProduit++;
				}
			}
			if (nbEtalsAvecProduit == 0) {
				return new Etal[0];
			}
			Etal[] etalsAvecProduit = new Etal[nbEtalsAvecProduit];
			int index = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProduit[index++] = etals[i];
				}
			}
			return etalsAvecProduit;
		}
        private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() != null && etals[i].getVendeur().equals(gaulois)) {
					return etals[i]; 
				}
			}
			return null;
		}
        private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal()).append("\n");
				} else {
					nbEtalsVides++;
				}
			}
			if (nbEtalsVides > 0) {
				chaine.append("Il reste ").append(nbEtalsVides).append(" étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}  
	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int indiceEtal = marche.trouverEtalLibre();
        if (indiceEtal != -1) {
            marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
            return vendeur.getNom() + " s'est installé à l'étal " + indiceEtal + " pour vendre " + nbProduit + " " + produit + "(s).";
        } else {
            return "Tous les étals sont occupés.";
        }
    }
	public String rechercherVendeursProduit(String produit) {
        StringBuilder chaine = new StringBuilder();
        Etal[] etals = marche.trouverEtals(produit);
        if (etals.length == 0) {
            chaine.append("Aucun vendeur ne propose ").append(produit).append(".\n");
        } else {
            chaine.append("Vendeurs proposant ").append(produit).append(" :\n");
            for (Etal etal : etals) {
                chaine.append(etal.afficherEtal()).append("\n");
            }
        }
        return chaine.toString();
    }
	public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }
	public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            etal.libererEtal();
            return vendeur.getNom() + " a quitté son étal.";
        } else {
            return vendeur.getNom() + " n'est pas sur un étal.";
        }
    }

    public String afficherMarche() {
        return marche.afficherMarche();
    }

}