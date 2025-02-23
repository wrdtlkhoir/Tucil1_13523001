public class Papan {
    private int N, M;
    private char[][] grid;
    private static final String RESET = "\u001B[0m";
    private static final String[] COLORS = {
        "\u001B[31m", // A - Merah
        "\u001B[32m", // B - Hijau
        "\u001B[33m", // C - Kuning
        "\u001B[34m", // D - Biru
        "\u001B[35m", // E - Magenta
        "\u001B[36m", // F - Cyan
        "\u001B[91m", // G - Merah terang
        "\u001B[92m", // H - Hijau terang
        "\u001B[93m", // I - Kuning terang
        "\u001B[94m", // J - Biru terang
        "\u001B[95m", // K - Magenta terang
        "\u001B[96m", // L - Cyan terang
        "\u001B[37m", // M - Putih
        "\u001B[90m", // N - Abu-abu
        "\u001B[31;1m", // O - Merah bold
        "\u001B[32;1m", // P - Hijau bold
        "\u001B[33;1m", // Q - Kuning bold
        "\u001B[34;1m", // R - Biru bold
        "\u001B[35;1m", // S - Magenta bold
        "\u001B[36;1m", // T - Cyan bold
        "\u001B[97m", // U - Putih terang
        "\u001B[90;1m", // V - Abu-abu terang
        "\u001B[91;1m", // W - Merah sangat terang
        "\u001B[92;1m", // X - Hijau sangat terang
        "\u001B[93;1m", // Y - Kuning sangat terang
        "\u001B[94;1m"  // Z - Biru sangat terang
    };

    public int getN() {
        return N;
    }       

    public int getM() {
        return M;
    }

    public char[][] getGrid() { // Metode baru untuk mengakses papan
        return grid;
    }

    public Papan(int N, int M, String S) {
        this.N = N;
        this.M = M;
        this.grid = new char[N][M];
        createPapan();
    }

    private void createPapan() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                grid[i][j] = '.'; //inisialisasi papan dengan titik
            }
        }
    }

    public void printPapan(Papan papan) {
        char[][] grid = papan.getGrid();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char c = grid[i][j];
                if (c == '.') {
                    System.out.print('.');
                } else {
                    int colorIndex = (c - 'A') % COLORS.length;
                    System.out.print(COLORS[colorIndex] + c + RESET);
                }
            }
            System.out.println();
        }
    }

    public boolean isFull(){ //cek apakah papan sudah penuh
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean cekPapan(int x, int y, char[][] shape) { //cek apakah blok bisa diletakkan di papan
        int baris = shape.length;
        int kolom = shape[0].length;
        if (x + baris > N || y + kolom > M) {
            return false;
        }
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                if (shape[i][j] != '.'&& grid[x + i][y + j] != '.') {
                    return false;
                }
            }
        }
        return true;
       
    }

    public void taruhBlok(int x, int y, char[][] shape, char idBlok) { //tempatkan blok pada papan
        int baris = shape.length;
        int kolom = shape[0].length;
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                if (shape[i][j] != '.') {
                    grid[x + i][y + j] = shape[i][j];
                }
            }
        }
    }

    public void hapusBlok(int x, int y, char[][] shape) { //hapus blok dari papan dengan mengubahnya jadi . kembali
        int baris = shape.length;
        int kolom = shape[0].length;
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                if (shape[i][j] != '.') {
                    grid[x + i][y + j] = '.';
                }
            }
        }
    }
    /* Mengubah grid menjadi string untuk disimpan di file */
    public String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
