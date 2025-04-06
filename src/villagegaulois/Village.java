package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	// Constructeur
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	// Getter pour le nom du village
	public String getNom() {
		return nom;
	}

	// Setter pour le chef du village
	public void setChef(Chef chef) {
		this.chef = chef;
	}

	// Ajouter un habitant au village
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	// Trouver un habitant par son nom
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

	// Afficher les villageois (lève une exception si le village n'a pas de chef)
	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef !");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	// Installer un vendeur sur un étal
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal != -1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			return "Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + indiceEtal + ".\n";
		} else {
			return "Tous les étals sont occupés.";
		}
	}

	// Rechercher les vendeurs d'un produit donné
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

	// Rechercher l'étal d'un vendeur donné
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	// Faire partir un vendeur de son étal
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal != null) {
			return etal.libererEtal();
		} else {
			return vendeur.getNom() + " n'est pas sur un étal.";
		}
	}

	// Afficher l'état du marché
	public String afficherMarche() {
		return marche.afficherMarche();
	}

	// Classe interne Marche
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
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
}