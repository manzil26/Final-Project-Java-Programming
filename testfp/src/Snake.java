// Kelas ini merepresentasikan sebuah ular dalam permainan.
public class Snake {
    private int tailPosition;   // Posisi ekor ular
    private int headPosition;   // Posisi kepala ular

    // Konstruktor untuk membuat objek Snake
    public Snake(int t, int h) {
        this.tailPosition = t;
        this.headPosition = h;
    }

    // Metode untuk mengatur posisi ekor ular
    public void setTailPosition(int t) {
        this.tailPosition = t;
    }

    // Metode untuk mengatur posisi kepala ular
    public void setHeadPosition(int h) {
        this.headPosition = h;
    }

    // Metode untuk mendapatkan posisi ekor ular
    public int getTailPosition() {
        return this.tailPosition;
    }

    // Metode untuk mendapatkan posisi kepala ular
    public int getHeadPosition() {
        return this.headPosition;
    }
}
