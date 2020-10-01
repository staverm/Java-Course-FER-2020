package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A program that can perform SHA256 checksum calculation, encryption and
 * decryption of files based on what the user commands.
 * 
 * @author Mauro Staver
 *
 */
public class Crypto {

	/**
	 * Main method that gets called when the program starts. The method uses command
	 * line arguments and can perform SHA256 checksum calculation, encryption and
	 * decryption. <br>
	 * Expected input:<br>
	 * 1. <code>'checksha filePath'</code> calculates SHA256 hash from the file at
	 * filePath <br>
	 * 2. <code>'encrypt filePath1 filePath2'</code> encrypts the file at filePath1
	 * and generates a new (encrypted) file at filePath2 <br>
	 * 3. <code>'decrypt filePath1 filePath2'</code> decrypts the file at filePath1
	 * and generates a new (decrypted) file at filePath2
	 * 
	 * @param args command line arguments - expected input explained above.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Illegal number of arguments.");
			System.exit(-1);
		}

		Scanner sc = new Scanner(System.in);

		if (args[0].equals("checksha")) {
			
			calculateDigest(args[1], sc);
			
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			
			boolean encrypt = args[0].equals("encrypt") ? true : false;
			encryptOrDecrypt(encrypt, args[1], args[2], sc);

		} else {
			System.out.println("Illegal input");
		}

		sc.close();

	}
	
	/**
	 * Encrypts or decrypts the specified source file into a specified destination file.
	 * 
	 * @param encrypt true to encrypt, false to decrypt
	 * @param source source file
	 * @param destination destination file
	 * @param sc Scanner for input
	 */
	private static void encryptOrDecrypt(boolean encrypt, String source, String destination, Scanner sc) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();

		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
			Cipher cipher;
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(source)));
			OutputStream os = new BufferedOutputStream(
					Files.newOutputStream(Paths.get(destination), StandardOpenOption.CREATE_NEW));
			
			byte[] buff = new byte[4096];

			while (true) {
				int bytesRead = is.read(buff);
				if (bytesRead < 1) {
					break;
				}

				os.write(cipher.update(buff, 0, bytesRead));
			}

			os.write(cipher.doFinal());

			is.close();
			os.close();

			if (encrypt) {
				System.out.println(
						"Encryption completed. Generated file " + destination + " based on file " + source + ".");
			} else {
				System.out.println(
						"Decryption completed. Generated file " + destination + " based on file " + source + ".");
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Calculates the digest of the specified message(String) and compares it with
	 * the user entered digest to see if they match.
	 * 
	 * @param message String whose digest is to be calculated
	 * @param sc      Scanner for input
	 */
	private static void calculateDigest(String message, Scanner sc) {
		System.out.println("Please provide expected sha-256 digest for " + message + ":");
		String expectedDigest = sc.nextLine();

		try {
			MessageDigest md = MessageDigest.getInstance("sha-256");

			try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(message)))) {
				byte[] buff = new byte[4096];

				while (true) {
					int bytesRead = is.read(buff);
					if (bytesRead < 1) {
						break;
					}
					md.update(buff, 0, bytesRead);
				}

				String digest = Util.byteToHex(md.digest());
				System.out.print("Digesting completed. ");

				if (digest.equals(expectedDigest)) {
					System.out.println("Digest of " + message + " matches expected digest.");
				} else {
					System.out.println(
							"Digest of " + message + " does not match the expected digest.\nDigest was: " + digest);
				}

			} catch (IOException ex) {
				System.out.println("Unable to read from the specified file.");
				System.exit(-1);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
