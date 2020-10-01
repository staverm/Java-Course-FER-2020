package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Program koji pomoću unesenih vrijednosti za širinu i visinu pravokutnika
 * računa pripadnu površinu i opseg, te iste ispisuje.
 * 
 * @author Mauro Staver
 *
 */
public class Rectangle {
/**
 * Metoda koja se poziva prilikom pokretanja programa.
 * Ako je polje argumenata prazno, metoda čita
 * unos sa tipkovnice, te za unese vrijednosti širine i visine pravokutnika
 * računa pripadnu površinu i opseg te ih ispisuje.
 * Ako polje argumenata nije prazno, ništa se ne čita sa tipkovnice, 
 * nego se izravno računaju opseg i površina ukoliko je broj argumenata ispravan(2).
 * 
 * 
 * @param args prvi element polja argumenata predstavlja širinu, a drugi visinu pravokutnika.
 */
	public static void main(String[] args) {
		if(args.length==2) {			
			try {
				double sirina = pretvoriUDouble(args[0]);
				double visina = pretvoriUDouble(args[1]);
				izracunajIspisi(sirina,visina);
			} catch(IllegalArgumentException ex) {
					return; //argumenti krivog formata
			}			
		} else if(args.length==0) {
			Scanner sc = new Scanner(System.in);
		
			try {
				double sirina = ucitajBroj("širina", sc);
				double visina = ucitajBroj("visina", sc);
				izracunajIspisi(sirina,visina);
			} catch(IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}			
		
			sc.close();
		} else {
			System.out.println("Krivi broj argumenata.");
		}
	}

/**
 * Metoda koja pretvara argument(string) u double te vraća tu vrijednost.
 * Ako argument ne zadovoljava uvjete, ispisuje se odgovarajuća poruka i baca iznimka.
 * 
 * @param input string kojeg želimo pretvoriti u double
 * @return double vrijednost argumenta
 * @throws IllegalArgumentException ako se argument nemože pretvoriti u broj ili ako
 * je pretvoreni broj manji od nule.
 */
	public static double pretvoriUDouble(String input) throws IllegalArgumentException{
		try {	
			double broj = Double.parseDouble(input.trim());
			
			if(broj<0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				throw new IllegalArgumentException("Promatrana vrijednost je negativna%n");
			} else {
				return broj;
			}
		} catch(NumberFormatException ex) {
			System.out.format("'%s' se nemože protumačiti kao broj.%n", input);
			throw new IllegalArgumentException("Promatrana vrijednost se ne može"
					+ " protumačiti kao broj.%n");
		}
	}

/**
 * Metoda koja ispituje korisnika da unese širinu/visinu dok je ne unese ispravno,
 *  te nakon ispravnog unosa, metoda vraća tu unesenu vrijednost.  
 * 
 * @param tipUnosa string pomoću kojeg biramo što će se čitati sa ulaza:
 *  "širina" predstavlja čitanje širine, a "visina" čitanje visine.
 * @param sc referenca na otvoreni Scanner.
 * @return ispravno učitani broj koji predstavlja širinu/visinu
 * @throws IllegalArgumentException ako je za argument tipUnosa predano nešto što nije
 *  "širina" ili "visina"
 */
	public static double ucitajBroj(String tipUnosa, Scanner sc) throws IllegalArgumentException{
		
		while(true) {
			if(tipUnosa.equals("širina")) {
				System.out.print("Unesite širinu > ");
			} else if(tipUnosa.equals("visina")) {
				System.out.print("Unesite visinu > ");
			} else {
				throw new IllegalArgumentException("Nepodržani argument za tip unosa: "
						+ "Podržani su samo 'širina' i 'visina'");
			}
			
			String input = sc.next();
			try {
				return pretvoriUDouble(input);
			} catch(IllegalArgumentException ex) {
				//probaj opet
			}
			
		}
	}
/**
 * Računa površinu i opseg pravokutnika, te ih ispisuje.
 * 
 * @param sirina broj koji predstavlja širinu pravokutnika.
 * @param visina broj koji predstavlja visinu pravokutnika.
 */	
	public static void izracunajIspisi(double sirina, double visina) {
		double povrsina = sirina*visina;
		double opseg = 2*(sirina+visina);
		
		System.out.format("Pravokutnik širine %s i visine %s ima "
				+ "površinu %s te opseg %s.%n", Double.toString(sirina), 
				Double.toString(visina), Double.toString(povrsina), 
				Double.toString(opseg));
	}
}

