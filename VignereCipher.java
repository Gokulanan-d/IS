package ex1;

import java.util.Scanner;

public class VignereCipher {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Get user input for plaintext
        System.out.print("Enter the input text : ");
        String pt = scan.nextLine().toLowerCase();

        // Get user input for key
        System.out.print("Enter the key : ");
        String key = scan.nextLine().toLowerCase();

        int currKey = 0, shift = 0;

        // Encryption
        StringBuilder ct = new StringBuilder();
        for(char c : pt.toCharArray()) {
            if (Character.isLowerCase(c)) {
                shift = key.charAt(currKey) - 'a';
                int ind = ((c - 'a') + shift) % 26;
                currKey = (currKey + 1) % key.length();
                ct.append((char) (ind + 'a'));
            } else {
                ct.append(c); // Keeps spaces and punctuation as is
            }
        }
        System.out.println("Vigenere Encryption : " + ct.toString());

        // Decryption
        currKey = 0; // Reset key index for decryption
        StringBuilder dt = new StringBuilder();
        for (char c : ct.toString().toCharArray()) {
            if (Character.isLowerCase(c)) {
                shift = key.charAt(currKey) - 'a';
                int ind = ((c - 'a') - shift + 26) % 26;
                currKey = (currKey + 1) % key.length();
                dt.append((char) (ind + 'a'));
            } else {
                dt.append(c);
            }
        }

        System.out.println("Vigenere Decryption : " + dt.toString());
        scan.close();
    }
}
