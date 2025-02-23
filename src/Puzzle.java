import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle {
    Scanner scan = new Scanner (System.in);
    public int N,M,P;
    public String S;
    public List<char[][]> puzzleShape;
    private int checkedCases = 0;

    public int getCheckedCases() {
        return checkedCases;
}

    /* Konstruktor */
    public Puzzle (int N, int M, int P, String S, List<char[][]> puzzleShape){
        this.N = N;
        this.M = M;
        this.P = P;
        this.S = S;
        this.puzzleShape = puzzleShape;
    }

    /* Pembacaan Input File */
    public static Puzzle inputFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Membaca baris pertama (N M P)
        String[] barisPertama = reader.readLine().trim().split("\\s+");
        if (barisPertama.length < 3) {
            throw new IOException("Format file salah: Baris pertama harus berisi N M P.");
        }
        int N = Integer.parseInt(barisPertama[0]); // Jumlah baris papan
        int M = Integer.parseInt(barisPertama[1]); // Jumlah kolom papan
        int P = Integer.parseInt(barisPertama[2]); // Jumlah puzzle

        // Membaca baris kedua (S)
        String S = reader.readLine().trim(); 

        //Debugging Print
        System.out.println("Baca N: " + N + ", M: " + M + ", P: " + P);
        System.out.println("Baca S: " + S);
        // Membaca P puzzle shapes
        List<char[][]> puzzleShape = new ArrayList<>();
        List<String> currentPiece = new ArrayList<>();
        char lastBlockChar = '\0'; // Menyimpan karakter awal blok sebelumnya
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // Lewati baris kosong
            }

            char currentChar = line.charAt(0);
            if (currentChar != lastBlockChar&& !currentPiece.isEmpty()) {
                // Jika menemukan blok baru, simpan blok lama (jika ada)
                puzzleShape.add(convertToCharArray(currentPiece));
                currentPiece.clear();
            }

            currentPiece.add(line); // Tambahkan baris ke blok yang sedang diproses
            lastBlockChar = currentChar;
        }

        // Tambahkan blok terakhir setelah selesai membaca file
        if (!currentPiece.isEmpty()) {
            puzzleShape.add(convertToCharArray(currentPiece));
        }

        reader.close();
        return new Puzzle(N, M, P, S, puzzleShape);
    }

    private static char[][] convertToCharArray(List<String> piece) {
        int rows = piece.size();
        int cols = piece.stream().mapToInt(String::length).max().orElse(0);
        char[][] shape = new char[rows][cols];

        System.out.println("Convert to char array:");
        for (int i = 0; i < rows; i++) {
            shape[i] = new char[cols];
            Arrays.fill(shape[i], ' '); // Isi dengan spasi untuk menghindari null
            char[] rowChars = piece.get(i).toCharArray();
            System.arraycopy(rowChars, 0, shape[i], 0, rowChars.length);
        }
        return shape;
    }

    public static void outputFile (String content) throws IOException{
        Scanner scan = new Scanner(System.in);
        System.out.printf("Simpan dengan Nama File (sertakan akhiran .txt): ");
        String fileSimpan = scan.nextLine();

        FileWriter writer = new FileWriter(fileSimpan);
        writer.write(content);
        writer.close();
        scan.close();
    }

    //ambil blok shape pertama dari list dicoba posisi normal ditaruh di papan, bila tidak bisa dirotasi per 90 derajat, 
    //bila tetap tidak bisa direfleksikan secara horizontal, bila tetap tidak bisa direfleksikan secara vertikal
    //bila tetap tidak bisa kembali ke posisi semula dan ambil blok shape selanjutnya
    //bila blok shape habis dan tidak ada yang bisa diletakkan maka hapus blok sebelumnya dan coba ulangi langkah sebelumnya
    //ulangi langkah sebelumnya hingga semua blok habis atau papan penuh
    //bila papan penuh maka selesai
    //bila semua blok habis dan papan belum penuh maka tidak ada solusi
    public boolean bruteforce(Papan board, List<Blok> pieces, Puzzle puzzle, boolean[] visited) {
        if (board.isFull()) { //jika papan sudah penuh maka solusi ditemukan
            System.out.println("Solution found:");
            return true;
        }
        System.out.println("Bruteforce");
        for (int i = 0; i < puzzle.P; i++) { // Mencoba semua blok yang ada
            checkedCases++; // Tambahkan jumlah kasus yang ditinjau
            if (visited[i]) continue; // Lewati blok yang sudah digunakan

            Blok piece = pieces.get(i);
            char[][][] bentuk = piece.perubahanBentuk(); // Ambil semua bentuk rotasi dan refleksi blok
            System.out.println("a");
            for (char[][] shape : bentuk) { // Coba semua bentuk rotasi/refleksi
                for (int k = 0; k < puzzle.N; k++) { // Coba semua posisi baris di papan
                    for (int l = 0; l < puzzle.M; l++) { // Coba semua posisi kolom di papan
                        if (board.cekPapan(k, l, piece)) {
                            board.taruhBlok(k, l, piece); // Taruh blok dengan ID
                            visited[i] = true; // Tandai blok ini sudah digunakan

                            if (bruteforce(board, pieces, puzzle, visited)) {
                                return true;
                            }
                            System.out.println("cc");
                            board.hapusBlok(k, l, piece); // Hapus blok jika tidak valid
                            visited[i] = false; // Tandai blok bisa digunakan lagi
                        }
                    }
                }
            }
        }
        return false;
    }
    public void solve(Papan board, List<Blok> pieces, Puzzle puzzle) {
        boolean[] visited = new boolean[puzzle.P]; // Array untuk menandai blok yang sudah digunakan
        
        if (!bruteforce(board, pieces, puzzle, visited)) {
            System.out.println("No solution found.");
        }
    }
}
