import java.util.ArrayList;
import java.util.List;

public class Blok {
    private List<char[][]> shape;

    public List<char[][]> getShape() {
        return shape;
    }
    
    public Blok(char[][] shapeOri) {
        shape = new ArrayList<>();
        perubahanBentuk(shapeOri); 
    }
    // Perubahan bentuk blok
    private void perubahanBentuk(char[][] shapeOri) {
        char[][] temp = shapeOri;
        for (int i=0;i<4;i++){
            shape.add(temp);
            shape.add(pencerminan(temp));
            temp = rotasi(temp);
        }
    }
    /* Fungsi rotasi blok */
    public char[][] rotasi(char[][] shape) { // rotasi per 90 derajat searah jarum jam
        int baris = shape.length;
        int kolom = shape[0].length;
        char[][] hasil_rotasi = new char[kolom][baris];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                hasil_rotasi[j][baris - 1 - i] = shape[i][j];
            }
        }
        return hasil_rotasi;
    }

    /* Fungsi pencerminan blok */
    public char[][] pencerminan(char[][] shape) { 
        int baris = shape.length;
        int kolom = shape[0].length;
        char[][] hasil_refleksi = new char[baris][kolom];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                hasil_refleksi[i][kolom-1-j] = shape[i][j];
            }
        }
        return hasil_refleksi;
    }
}