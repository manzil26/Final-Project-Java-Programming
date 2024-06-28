// Kelas ini merepresentasikan sebuah tangga dalam permainan.
public class Ladder {
    private int topPosition;    // Posisi atas tangga
    private int bottomPosition; // Posisi bawah tangga

    // Konstruktor untuk membuat objek Ladder
    public Ladder(int t, int b) {
        this.topPosition = t;
        this.bottomPosition = b;
    }

    // Metode untuk mengatur posisi atas tangga
    public void setTopPosition(int t) {
        this.topPosition = t;
    }

    // Metode untuk mengatur posisi bawah tangga
    public void setBottomPosition(int b) {

        this.bottomPosition = b;
    }

    // Metode untuk mendapatkan posisi atas tangga
    public int getTopPosition() {
        return this.topPosition;
    }

    // Metode untuk mendapatkan posisi bawah tangga
    public int getBottomPosition() {
        return this.bottomPosition;
    }
}
