import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\r\n" + //

                                        "██╗ ██████╗     ██████╗ ██╗   ██╗███████╗███████╗██╗     ███████╗██████╗     ██████╗ ██████╗  ██████╗     ███████╗ ██████╗ ██╗    ██╗   ██╗███████╗██████╗     \r\n" + //
                                        "██║██╔═══██╗    ██╔══██╗██║   ██║╚══███╔╝╚══███╔╝██║     ██╔════╝██╔══██╗    ██╔══██╗██╔══██╗██╔═══██╗    ██╔════╝██╔═══██╗██║    ██║   ██║██╔════╝██╔══██╗    \r\n" + //
                                        "██║██║   ██║    ██████╔╝██║   ██║  ███╔╝   ███╔╝ ██║     █████╗  ██████╔╝    ██████╔╝██████╔╝██║   ██║    ███████╗██║   ██║██║    ██║   ██║█████╗  ██████╔╝    \r\n" + //
                                        "██║██║▄▄ ██║    ██╔═══╝ ██║   ██║ ███╔╝   ███╔╝  ██║     ██╔══╝  ██╔══██╗    ██╔═══╝ ██╔══██╗██║   ██║    ╚════██║██║   ██║██║    ╚██╗ ██╔╝██╔══╝  ██╔══██╗    \r\n" + //
                                        "██║╚██████╔╝    ██║     ╚██████╔╝███████╗███████╗███████╗███████╗██║  ██║    ██║     ██║  ██║╚██████╔╝    ███████║╚██████╔╝███████╗╚████╔╝ ███████╗██║  ██║    \r\n" + //
                                        "╚═╝ ╚══▀▀═╝     ╚═╝      ╚═════╝ ╚══════╝╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝    ╚═╝     ╚═╝  ╚═╝ ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝ ╚═══╝  ╚══════╝╚═╝  ╚═╝    \r\n" + //
                                        "                                                                                                                                                               \r\n" + //
                                        "");
                System.out.println("Welcome to IQ Puzzler Pro Solver!");
                System.out.println("1. Solve Puzzle");
                System.out.println("2. Exit");
                System.out.printf("Enter your choice: ");
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                if (pilihan == 1){
                    solvePuzzle();
                }
                else if (pilihan == 2){
                    System.out.println("\r\n" + //
                                                "                                        __ \r\n" + //
                                                " _____ _           _      __ __        |  |\r\n" + //
                                                "|_   _| |_ ___ ___| |_   |  |  |___ _ _|  |\r\n" + //
                                                "  | | |   | .'|   | '_|  |_   _| . | | |__|\r\n" + //
                                                "  |_| |_|_|__,|_|_|_,_|    |_| |___|___|__|\r\n" + //
                                                "                                           \r\n" + //
                                                "");
                    scanner.close();
                    return;
                }
            } catch (Exception e) {
                System.out.println("Terjadi Error: " + e.getMessage());
                System.out.println("Silahkan dicoba lagi.");
                scanner.nextLine();
            }
        }
    }

    private static void solvePuzzle() throws IOException {
        System.out.print("Masukkan nama file input (.txt): ");
        String filename = scanner.nextLine().trim();
        Puzzle puzzleInput = Puzzle.inputFile(filename);
        Papan board = new Papan(puzzleInput.N, puzzleInput.M, puzzleInput.S);
        List<Blok> pieces = new ArrayList<>();

        for (int i = 0; i < puzzleInput.puzzleShape.size(); i++) {
            pieces.add(new Blok(puzzleInput.puzzleShape.get(i)));
        }
        
        long startTime = System.currentTimeMillis(); // Catat waktu mulai
        if (puzzleInput.solve(board, pieces,0)) { //ada solusi
            System.out.println("Solusi Ditemukan!");
            board.printPapan(board);
            long endTime = System.currentTimeMillis();
            System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
            System.out.println("Banyak kasus yang ditinjau: " + puzzleInput.getKasus());
            System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
            String file = scanner.nextLine();
            if (file.equals("ya")) {
                Puzzle.outputFile(board.gridToString(), scanner);
            }
        } else {
            System.out.println("Tidak ada solusi.");
        }
    }
}