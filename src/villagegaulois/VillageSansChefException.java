package villagegaulois;

public class VillageSansChefException extends Exception {
	// Constructeur par défaut
	public VillageSansChefException() {
		super();
	}

	// Constructeur avec un message d'erreur
	public VillageSansChefException(String message) {
		super(message);
	}

	// Constructeur avec un message d'erreur et une cause (une autre exception)
	public VillageSansChefException(String message, Throwable cause) {
		super(message, cause);
	}

	// Constructeur avec une cause (une autre exception)
	public VillageSansChefException(Throwable cause) {
		super(cause);
	}
}