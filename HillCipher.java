package ex1;

import java.util.Scanner;

public class HillCipher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        // Remove spaces/symbols. If length is odd, pad with 'x' to make it even
        String pt = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
        if (pt.length() % 2 != 0) pt += "x";

        System.out.print("Enter 4-letter key: ");
        String key = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");

        if (key.length() != 4) {
            System.out.println("Error: Key must be exactly 4 letters for a 2x2 matrix!");
            return;
        }

        // Build the 2x2 Key Matrix from the 4 letters
        int[][] K = {
                {key.charAt(0) - 'a', key.charAt(1) - 'a'},
                {key.charAt(2) - 'a', key.charAt(3) - 'a'}
        };

        String ct = encrypt(pt, K);
        System.out.println("Encryption : " + ct);

        // Decrypt using the Inverse Matrix
        String dt = decrypt(ct, K);
        System.out.println("Decryption : " + dt);

        sc.close();
    }

    // Encrypts text using standard 2x2 matrix multiplication
    private static String encrypt(String text, int[][] keyMatrix) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            int p1 = text.charAt(i) - 'a';     // First letter of pair
            int p2 = text.charAt(i + 1) - 'a'; // Second letter of pair

            // Matrix Multiplication: (Row * Column)
            int c1 = (keyMatrix[0][0] * p1 + keyMatrix[0][1] * p2) % 26;
            int c2 = (keyMatrix[1][0] * p1 + keyMatrix[1][1] * p2) % 26;

            res.append((char) (c1 + 'a')).append((char) (c2 + 'a'));
        }
        return res.toString();
    }

    // Decrypts by finding the inverse matrix, then running it through the encrypt method!
    private static String decrypt(String text, int[][] K) {
        // Step 1: Find Determinant (ad - bc)
        int det = (K[0][0] * K[1][1] - K[0][1] * K[1][0]) % 26;
        if (det < 0) det += 26; // Fix negative Java modulos

        // Step 2: Find Modular Inverse of the Determinant (number * det = 1 mod 26)
        int detInv = -1;
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }

        if (detInv == -1) {
            return "ERROR: This key matrix cannot be inverted! Choose a different key.";
        }

        // Step 3: Build the Inverse Matrix -> detInv * [d, -b; -c, a]
        int[][] invK = {
                {(K[1][1] * detInv) % 26,  (-K[0][1] * detInv) % 26},
                {(-K[1][0] * detInv) % 26, (K[0][0] * detInv) % 26}
        };

        // Fix any negative numbers in the new inverse matrix
        for(int r = 0; r < 2; r++){
            for(int c = 0; c < 2; c++){
                if(invK[r][c] < 0) invK[r][c] += 26;
            }
        }

        // Step 4: Re-use the encrypt method, but pass in the Inverse Matrix!
        return encrypt(text, invK);
    }
}
