public class Blok {
    private int id; // maksimal 26 blok
    private char[][] shape;

    public Blok(int id, char[][] shape) {
        this.id = id;
        this.shape = shape;
    }

    public int getId() {
        return id;
    }

    public char[][] getShape() {
        return shape;
    }

    /* Fungsi rotasi blok */
    public char[][] rotasi(char[][] shape) { // rotasi per 90 derajat searah jarum jam
        int baris = shape.length;
        int kolom = shape[0].length;
        char[][] result = new char[kolom][baris];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                result[j][baris - 1 - i] = shape[i][j];
            }
        }
        return result;
    }

    /* Fungsi pencerminan blok */
    public char[][] pencerminanSumbuY(char[][] shape) { // pencerminan terhadap sumbu vertikal/Y
        int baris = shape.length;
        int kolom = shape[0].length;
        char[][] result = new char[baris][kolom];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                result[i][kolom - 1 - j] = shape[i][j];
            }
        }
        return result;
    }

    public char[][] pencerminanSumbuX(char[][] shape) { // pencerminan terhadap sumbu horizontal/X
        int baris = shape.length;
        char[][] result = new char[baris][];
        for (int i = 0; i < baris; i++) {
            result[i] = shape[baris - 1 - i];
        }
        return result;
    }

    // Perubahan bentuk blok
    public char[][][] perubahanBentuk() {
        char[][][] bentuk = new char[11][][];
        bentuk[0] = shape;
        bentuk[1] = rotasi(shape);
        bentuk[2] = rotasi(bentuk[1]);
        bentuk[3] = rotasi(bentuk[2]);
        bentuk[4] = pencerminanSumbuY(shape);
        bentuk[5] = rotasi(bentuk[4]);
        bentuk[6] = rotasi(bentuk[5]);
        bentuk[7] = rotasi(bentuk[6]);
        bentuk[8] = pencerminanSumbuX(shape);
        bentuk[9] = rotasi(bentuk[8]);
        bentuk[10] = rotasi(bentuk[9]);
        return bentuk;
    }
}