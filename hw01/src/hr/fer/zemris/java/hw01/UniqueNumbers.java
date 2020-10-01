package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Klasa koja implementira binarno stablo i metode za rad sa njime.
 * Implementirana je metoda za dodavanje čvorova u stablo, te metode za 
 * računanje raznih informacija o stablu.
 * Program izgrađuje stablo sa vrijednostima koje unosi korisnik.
 * 
 * @author Mauro Staver
 *
 */
public class UniqueNumbers {

/**
 * Struktura za čvor stabla. Čvor ima vrijednost, te sadrži reference na svoje 
 * lijevo i desno dijete.
 * 
 * @author Mauro Staver
 */
	static class TreeNode{
		TreeNode left;
		TreeNode right;
		int value;
	}
/**
 * 	Metoda koja se poziva prilikom pokretanja programa.
 * 	Metoda čita unos sa tipkovnice, te gradi binarno stablo sa unesenim 
 * 	vrijednostima(manja vrijednost lijevo, veća desno).
 *  Nakon što korisnik unese "kraj", ispisuju se čvorovi stabla sortirano
 *   i obrnuto sortirano, te rad programa završi.
 * 
 * @param args Argumenti iz komandne linije(ne koriste se).
 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		
		while(true) {
			System.out.print("Unesite broj > ");			
			String input = sc.next();
			
			if(input.equals("kraj")) {
				System.out.print("Ispis od najmanjeg:"); 	
				printSorted(glava);
				System.out.format("%nIspis od najvećeg:");
				printSortedReversed(glava);
				System.out.println(); //novi red
				
				break;
			}
			
			try {
				int value = Integer.parseInt(input);
				if(containsValue(glava, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, value);
					System.out.println("Dodano.");
				}
			} catch(NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj%n", input);
			}	
		}
		
		sc.close();
	}
	
	/**
	 * Metoda koja dodaje čvor sa vrijednosti argumenta value u stablo.
	 * Ako takav čvor već postoji, metoda ne radi ništa.
	 * Ako je stablo prazno, metoda izgradi čvor koji predstavlja korijen stabla,
	 * a ako nije prazno novi čvorovi se dodaju tako da je lijevo dijete manje od korijena,
	 * a desno dijete veće od korijena.
	 * 
	 * @param glava referenca na korijen stabla.
	 * @param value vrijednost čvora kojeg želimo dodati.
	 * @return referenca na korijen stabla.
	 */
	public static TreeNode addNode(TreeNode glava, int value) {
		if(glava==null) {
			glava = new TreeNode();
			glava.value = value;
		} else if(value<glava.value) {
			glava.left = addNode(glava.left, value);
		} else if(value>glava.value) {
			glava.right = addNode(glava.right, value);
		} 
		
		return glava;
	}
	
	/**
	 * Metoda koja računa i vraća veličinu stabla(broj čvorova u stablu).
	 * 
	 * @param glava referenca na korijen stabla
	 * @return broj koji predstavlja veličinu stabla
	 */
	public static int treeSize(TreeNode glava) {
		if(glava==null) {
			return 0;
		}
		return treeSize(glava.left) + treeSize(glava.right) + 1;
	}
	
	/**
	 * Metoda koja provjerava ako čvor sa vrijednosti argumenta value 
	 * postoji u stablu i ako postoji vraća istinu, a u suprotnom vraća laž.
	 * 
	 * @param glava referenca na korijen stabla
	 * @param value vrijednost čvora kojeg tražimo u stablu
	 * @return boolean vrijednost true ako traženi čvor postoji u stablu,
	 * false ako ne postoji.  
	 */
	public static boolean containsValue(TreeNode glava, int value) {
		if(glava==null) {
			return false;
		}
		if(glava.value==value) {
			return true;
		}
		if(value<glava.value) {
			return containsValue(glava.left, value);
		}
		return containsValue(glava.right, value);
	}
	
	/**
	 * Metoda koja ispisuje čvorove stabla od manjeg prema većem. 
	 * 
	 * @param glava referenca na korijen stabla.
	 */
	public static void printSorted(TreeNode glava) {
		if(glava!=null) {
			printSorted(glava.left);
			System.out.format(" %d", glava.value);
			printSorted(glava.right);	
		}
	}
	
	/**
	 * Metoda koja ispisuje čvorove stabla od većeg prema manjem.
	 * 
	 * @param glava referenda na korijen stabla.
	 */
	public static void printSortedReversed(TreeNode glava) {
		if(glava!=null) {
			printSortedReversed(glava.right);
			System.out.format(" %d", glava.value);
			printSortedReversed(glava.left);
		}	
	}
}
