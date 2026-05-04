package ex1;

import java.util.Scanner;

public class PlayfairCipher {

    // Our 5x5 visual grid
    private static char[][] matrix = new char[5][5];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the input text : ");
        // Playfair only uses letters. We remove spaces/punctuation and replace 'j' with 'i'
        String pt = scan.nextLine().toLowerCase().replaceAll("[^a-z]", "").replace('j', 'i');

        System.out.print("Enter the key : ");
        String key = scan.nextLine().toLowerCase().replaceAll("[^a-z]", "").replace('j', 'i');

        // Step 1: Build and display the 5x5 grid
        generateMatrix(key);
        System.out.println("\n--- Playfair 5x5 Grid ---");
        printMatrix();

        // Step 2: Format the text into pairs (digraphs)
        String formattedPt = formatText(pt);
        System.out.println("\nFormatted Text (Pairs) : " + formattedPt);

        // Step 3: Encrypt
        String encrypted = encrypt(formattedPt);
        System.out.println("Playfair Encryption    : " + encrypted);

        // Step 4: Decrypt
        String decrypted = decrypt(encrypted);
        System.out.println("Playfair Decryption    : " + decrypted);

        scan.close();
    }

    // --- HELPER METHODS BELOW ---

    // 1. Generates the 5x5 grid using the key, then the rest of the alphabet
    private static void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        int row = 0, col = 0;

        // First, fill in the key
        for (char c : key.toCharArray()) {
            if (!used[c - 'a']) {
                matrix[row][col] = c;
                used[c - 'a'] = true;
                col++;
                if (col == 5) { col = 0; row++; } // Move to next row
            }
        }

        // Next, fill in the remaining alphabet (skipping 'j')
        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue;

            if (!used[c - 'a']) {
                matrix[row][col] = c;
                used[c - 'a'] = true;
                col++;
                if (col == 5) { col = 0; row++; }
            }
        }
    }

    // 2. Prepares text by splitting double letters with 'x' and making it an even length
    private static String formatText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c1 = text.charAt(i);
            sb.append(c1);

            // If there's another letter, check if it's a duplicate
            if (i + 1 < text.length()) {
                char c2 = text.charAt(i + 1);
                if (c1 == c2) {
                    sb.append('x'); // Insert filler 'x'
                } else {
                    sb.append(c2);
                    i++; // Skip the next letter since we just paired it
                }
            }
        }
        // If the total length is odd, add an 'x' to the end to complete the final pair
        if (sb.length() % 2 != 0) {
            sb.append('x');
        }
        return sb.toString();
    }

    // 3. Applies the Playfair geometric rules to encrypt
    private static String encrypt(String text) {
        StringBuilder ct = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) { // Same Row: shift right
                ct.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                ct.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) { // Same Column: shift down
                ct.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                ct.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            } else { // Rectangle: swap columns
                ct.append(matrix[posA[0]][posB[1]]);
                ct.append(matrix[posB[0]][posA[1]]);
            }
        }
        return ct.toString();
    }

    // 4. Reverses the geometric rules to decrypt
    private static String decrypt(String text) {
        StringBuilder dt = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) { // Same Row: shift left (add 4 to avoid negative modulo)
                dt.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                dt.append(matrix[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) { // Same Column: shift up
                dt.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                dt.append(matrix[(posB[0] + 4) % 5][posB[1]]);
            } else { // Rectangle: swap columns (same as encryption)
                dt.append(matrix[posA[0]][posB[1]]);
                dt.append(matrix[posB[0]][posA[1]]);
            }
        }
        return dt.toString();
    }

    // Helper: Finds the row and column of a letter in the 5x5 grid
    private static int[] findPosition(char target) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (matrix[r][c] == target) {
                    return new int[]{r, c};
                }
            }
        }
        return null;
    }

    // Helper: Prints the grid so the user can see it
    private static void printMatrix() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                System.out.print(matrix[r][c] + " ");
            }
            System.out.println();
        }
    }
}