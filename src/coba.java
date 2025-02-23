import java.util.ArrayList;
import java.util.List;

class Papan {
    private char[][] grid;
    private static final int SIZE = 5;

    public Papan() {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public boolean cekPapan(int row, int col, char[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        if (row + rows > SIZE || col + cols > SIZE) return false;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (shape[i][j] != '.' && grid[row + i][col + j] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public void taruhBlok(int row, int col, char[][] shape, char blokId) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != '.') {
                    grid[row + i][col + j] = blokId;
                }
            }
        }
    }

    public void hapusBlok(int row, int col, char[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != '.') {
                    grid[row + i][col + j] = '.';
                }
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public void printPapan() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}

class Blok {
    private List<char[][]> bentuk;
    
    public Blok(char[][] bentukAwal) {
        bentuk = new ArrayList<>();
        generateRotationsAndReflections(bentukAwal);
    }

    private void generateRotationsAndReflections(char[][] bentukAwal) {
        char[][] current = bentukAwal;
        for (int i = 0; i < 4; i++) {
            bentuk.add(current);
            bentuk.add(reflect(current));
            current = rotate(current);
        }
    }
    
    private char[][] rotate(char[][] shape) {
        int rows = shape.length, cols = shape[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    private char[][] reflect(char[][] shape) {
        int rows = shape.length, cols = shape[0].length;
        char[][] reflected = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                reflected[i][cols - 1 - j] = shape[i][j];
            }
        }
        return reflected;
    }

    public List<char[][]> getOrientations() {
        return bentuk;
    }
}

public class coba {
    public static void main(String[] args) {
        Papan board = new Papan();
        List<Blok> pieces = new ArrayList<>();

        char[][] A = {
            {'A', 'A'},
            {'A', '.'}
        };

        char[][] B = {
            {'.', 'B','.'},
            {'B', 'B','.'},
            {'.', 'B','B'}
        };

        char[][] C = {
            {'C', '.'},
            {'C', 'C'}
        };
        char[][] D = {
            {'D', 'D','D'},
            {'D', '.','D'}
        };
        char[][] E = {
            {'.', 'E'},
            {'E', 'E'},
            {'.','E'}
        };
        char[][] F = {
            {'.','.', 'F'},
            {'.','.', 'F'},
            {'F','F','F'}
        };
        
        pieces.add(new Blok(A));
        pieces.add(new Blok(B));
        pieces.add(new Blok(C));
        pieces.add(new Blok(D));
        pieces.add(new Blok(E));
        pieces.add(new Blok(F));
        
        
        if (solve(board, pieces, 0)) {
            board.printPapan();
        } else {
            System.out.println("Tidak ada solusi!");
        }
    }

    private static boolean solve(Papan board, List<Blok> pieces, int index) {
        if (index == pieces.size()) {
            return board.isFull();
        }

        Blok currentPiece = pieces.get(index);
        List<char[][]> orientations = currentPiece.getOrientations();

        for (char[][] orientation : orientations) {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
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
