package ex1;

import java.util.Scanner;

public class CasesarCipher {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Get user input for plaintext
        System.out.print("Enter the input text : ");
        String pt = scan.nextLine().toLowerCase();

        // Get user input for key
        System.out.print("Enter the key : ");
        int key = scan.nextInt();

        // Encryption
        StringBuilder ct = new StringBuilder();
        for(char c : pt.toCharArray()) {
            if (Character.isLowerCase(c)) {
                int ind = (c - 'a' + key) % 26;
                ct.append((char) (ind + 'a'));
            } else {
                ct.append(c); // Keeps spaces and punctuation as is
            }
        }
        System.out.println("Caesar Encryption : " + ct.toString());

        // Decryption
        StringBuilder dt = new StringBuilder();
        for (char c : ct.toString().toCharArray()) {
            if (Character.isLowerCase(c)) {
                // Ensure the negative modulo calculation is safe in Java
                int ind = (c - 'a' - (key % 26) + 26) % 26;
                dt.append((char) (ind + 'a'));
            } else {
                dt.append(c);
            }
        }

        System.out.println("Caesar Decryption : " + dt.toString());
        scan.close();
    }
}
