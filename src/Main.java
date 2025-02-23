import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("IQ Puzzler Pro Solver");
                System.out.println("1. Solve Puzzle");
                System.out.println("2. Exit");
                System.out.printf("Enter your choice: ");
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                if (pilihan == 1){
                    solvePuzzle();
                }
                else if (pilihan == 2){
                    System.out.println("Thank you for using IQ Puzzler Pro Solver!");
                    scanner.close();
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
                System.out.println("Please try again.");
                scanner.nextLine();
            }
        }
    }

    private static void solvePuzzle() throws IOException {
        System.out.print("Enter the input file name: ");
        String filename = scanner.nextLine().trim();
        System.out.println("Membaca file: " + filename);
        Puzzle puzzleInput = Puzzle.inputFile(filename);
        System.out.println("File berhasil dibaca.");
        Papan board = new Papan(puzzleInput.N, puzzleInput.M, puzzleInput.S);
        List<Blok> pieces = new ArrayList<>();
        for (int i = 0; i < puzzleInput.puzzleShape.size(); i++) {
            pieces.add(new Blok(i, puzzleInput.puzzleShape.get(i)));
        }
        System.out.println("File berhasil dibaca.");
        boolean[] visited = new boolean[puzzleInput.P]; // Tambahkan array untuk menandai blok yang digunakan
        long startTime = System.currentTimeMillis(); // Catat waktu mulai
        if (puzzleInput.bruteforce(board, pieces, puzzleInput, visited)) {
            System.out.println("Solution found:");
            board.printPapan(board);
            long endTime = System.currentTimeMillis();
            System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
            System.out.println("Banyak kasus yang ditinjau: " + puzzleInput.getCheckedCases());
        } else {
            System.out.println("No solution found.");
        }
    }
}