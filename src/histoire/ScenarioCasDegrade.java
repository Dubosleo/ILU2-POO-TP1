package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		// Test 1 : Libérer un étal non occupé
		try {
			Etal etal = new Etal();
			etal.libererEtal(); // Doit lever une IllegalStateException
		} catch (IllegalStateException e) {
			System.err.println("Test 1 : " + e.getMessage());
		}

		// Test 2 : Acheter avec un acheteur null
		try {
			Etal etal = new Etal();
			Gaulois acheteur = null;
			etal.acheterProduit(5, acheteur); // Doit lever une IllegalArgumentException
		} catch (IllegalArgumentException e) {
			System.err.println("Test 2 : " + e.getMessage());
		}

		// Test 3 : Acheter avec une quantité négative
		try {
			Etal etal = new Etal();
			Gaulois acheteur = new Gaulois("Astérix", 8);
			etal.acheterProduit(-5, acheteur); // Doit lever une IllegalArgumentException
		} catch (IllegalArgumentException e) {
			System.err.println("Test 3 : " + e.getMessage());
		}

		// Test 4 : Acheter sur un étal non occupé
		try {
			Etal etal = new Etal();
			Gaulois acheteur = new Gaulois("Astérix", 8);
			etal.acheterProduit(5, acheteur); // Doit lever une IllegalStateException
		} catch (IllegalStateException e) {
			System.err.println("Test 4 : " + e.getMessage());
		}

		System.out.println("Fin des tests de cas dégradés.");
	}
}