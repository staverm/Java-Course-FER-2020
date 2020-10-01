package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Program koji čita unos korisnika sa tipkovnice,
 *  te za unesene brojeve u rasponu [3, 20] računa njihove faktorijele.
 *   
 * @author Mauro Staver
 */
public class Factorial {

/**
 * Metoda koja se poziva prilikom pokretanja programa.
 * Metoda čita unos sa tipkovnice, te za unesene brojeve 
 * u rasponu [3, 20] računa i ispisuje njihove faktorijele.
 * Rad programa se prekida kada korisnik unese "kraj".
 * 
 * @param args Argumenti iz komandne linije(ne koriste se).
 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			
			if(input.equals("kraj")) {
				break;
			}
			
			try {
				int broj = Integer.parseInt(input);
				
				if(broj>=3 && broj<=20) {
					try {
						System.out.format("%d! = %d%n", broj, factorial(broj));
					} catch(IllegalArgumentException ex) {
						System.out.format("'%d' je izvan raspona domene.%n", broj);
					}
				} else {
					System.out.format("'%d' nije broj u dozvoljenom rasponu.%n", broj);
				}
				
			} catch(NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj.%n", input);
			}
		}
		
		System.out.println("Doviđenja.");
		sc.close();
	}
	
/**
 * Metoda računa faktorijel cijelog broja predanog kao argument
 * ukoliko je broj u rasponu [0, 20].
 * 
 * @param x cijeli broj čiji se faktorijel računa.
 * @return faktorijel broja predanog kao argument.
 * @throws IllegalArgumentException ako je argument izvan raspona [0, 20].
 */
	public static long factorial(int x) throws IllegalArgumentException{
		if(x>=0 && x<=20) {
			if(x==0) {
				return 1L;
			} else {
				return x*factorial(x-1);
			}	
		} else {
			throw new IllegalArgumentException("Argument je izvan raspona domene");
		}
	}
}
