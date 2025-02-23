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
    private int count = 0;

    public int getKasus(){
        return count;
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
            reader.close();
            throw new IOException("Format file salah: Baris pertama harus berisi N M P.");
        }
        int N = Integer.parseInt(barisPertama[0]); // Jumlah baris papan
        int M = Integer.parseInt(barisPertama[1]); // Jumlah kolom papan
        int P = Integer.parseInt(barisPertama[2]); // Jumlah puzzle
        if (P < 1 || P > 26) {
            reader.close();
            throw new IllegalArgumentException("P harus berada di antara 1 dan 26");
        }

        // Membaca baris kedua (S)
        String S = reader.readLine().trim(); 
        if (!S.equals("DEFAULT")) {
            reader.close();
            throw new IllegalArgumentException("S hanya berjenis 'DEFAULT'");
        }

        // Membaca P puzzle shapes
        List<char[][]> blocks = new ArrayList<>();
        String line;
        List<String> currentBlock = new ArrayList<>();
        char currentChar = '\0';
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (currentBlock.isEmpty() || line.charAt(0) == currentChar) {
                    currentBlock.add(line);
                    currentChar = line.charAt(0);
                } else {
                    blocks.add(convertToCharArray(currentBlock, currentChar));
                    currentBlock.clear();
                    currentBlock.add(line);
                    currentChar = line.charAt(0);
                }
            }
        }
        if (!currentBlock.isEmpty()) {
            blocks.add(convertToCharArray(currentBlock, currentChar));
        }
        reader.close();
        return new Puzzle(N,M,P,S,blocks);
    }
    public static char[][] convertToCharArray(List<String> block, char blockId) {
        int rows = block.size();
        int cols = block.stream().mapToInt(String::length).max().orElse(0);
        char[][] array = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(array[i], '.'); 
            for (int j = 0; j < block.get(i).length(); j++) {
                if (block.get(i).charAt(j) == blockId) {
                    array[i][j] = blockId;
                }
            }
        }
        return array;
    }
    /* Fungsi untuk menyimpan solusi dalam file */
    public static void outputFile (String content, Scanner scanner) throws IOException{
        System.out.printf("Masukkan nama file penyimpanan solusi (.txt): ");
        String fileSimpan = scanner.nextLine();

        FileWriter writer = new FileWriter(fileSimpan);
        writer.write(content);
        writer.close();
        System.out.println("Solusi berhasil disimpan dalam file: " + fileSimpan);
    }

    //ambil blok shape pertama dari list dicoba posisi normal ditaruh di papan, bila tidak bisa dirotasi per 90 derajat, 
    //bila tetap tidak bisa direfleksikan secara horizontal, bila tetap tidak bisa direfleksikan secara vertikal
    //bila tetap tidak bisa kembali ke posisi semula dan ambil blok shape selanjutnya
    //bila blok shape habis dan tidak ada yang bisa diletakkan maka hapus blok sebelumnya dan coba ulangi langkah sebelumnya
    //ulangi langkah sebelumnya hingga semua blok habis atau papan penuh
    //bila papan penuh maka selesai
    //bila semua blok habis dan papan belum penuh maka tidak ada solusi
    public boolean solve(Papan board, List<Blok> pieces, int index) {
        count++;
        if (index == pieces.size()) {
            return board.isFull();
        }

        Blok currentPiece = pieces.get(index);
        List<char[][]> orientations = currentPiece.getShape();

        for (char[][] orientation : orientations) {
            for (int row = 0; row < this.N; row++) {
                for (int col = 0; col < this.M; col++) {
                    if (board.cekPapan(row, col, orientation)) {
                        board.taruhBlok(row, col, orientation, (char) ('A' + index));
                        if (solve(board, pieces, index + 1)) {
                            return true;
                        }
                        board.hapusBlok(row, col, orientation);
                    }
                }
            }
        }
        return false;
    }
}
