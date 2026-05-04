import java.util.Scanner;

public class ColumnarTransposition {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter text: ");
        String pt = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
        
        System.out.print("Enter keyword: ");
        String key = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
        
        if (key.length() == 0) {
            System.out.println("Error: Keyword cannot be empty!");
            return;
        }

        // Pad the text with 'x' so it forms a perfect grid
        while (pt.length() % key.length() != 0) {
            pt += "x";
        }

        String ct = encrypt(pt, key);
        System.out.println("Encryption : " + ct);
        
        String dt = decrypt(ct, key);
        System.out.println("Decryption : " + dt);
        
        sc.close();
    }
    private static int[] getColumnOrder(String key) {
        int[] order = new int[key.length()];
        boolean[] used = new boolean[key.length()];
        int currentRank = 0;
        
        for (char ch = 'a'; ch <= 'z'; ch++) {
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == ch && !used[i]) {
                    order[currentRank++] = i;
                    used[i] = true;
                }
            }
        }
        return order;
    }
    private static String encrypt(String text, String key) {
        int cols = key.length();
        int rows = text.length() / cols;
        char[][] grid = new char[rows][cols];
        
        // 1. Write text in row by row
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = text.charAt(index++);
            }
        }
 
        StringBuilder ct = new StringBuilder();
        int[] order = getColumnOrder(key);
        
        for (int c : order) {
            for (int r = 0; r < rows; r++) {
                ct.append(grid[r][c]);
            }
        }
        return ct.toString();
    }
    private static String decrypt(String text, String key) {
        int cols = key.length();
        int rows = text.length() / cols;
        char[][] grid = new char[rows][cols];
        int[] order = getColumnOrder(key);
 
        int index = 0;
        for (int c : order) {
            for (int r = 0; r < rows; r++) {
                grid[r][c] = text.charAt(index++);
            }
        }
        StringBuilder dt = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                dt.append(grid[r][c]);
            }
        }
        return dt.toString();
    }
}
