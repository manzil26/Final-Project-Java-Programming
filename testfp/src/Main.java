import java.util.Scanner;
import javax.swing.*;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) {
        // Membuat GUI untuk memasukkan ukuran papan
        JFrame frame = new JFrame("Snake and Ladder Game");
        int boardSize;
        String boardSizeStr = JOptionPane.showInputDialog(frame, "Enter board size:");
        try {
            boardSize = Integer.parseInt(boardSizeStr);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "Amount of boar must be number",
                    "Warning!! ",
                    JOptionPane.WARNING_MESSAGE);

            return;

        }
        if (boardSize < 100) {
            JOptionPane.showMessageDialog(null,
                    "The total board size must be less than 100 ",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }
        else if(boardSize > 200) {
            JOptionPane.showMessageDialog(null,
                    "The total board maximum is 200",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        SnL g1 = new SnL(boardSize);
        g1.play();
        Player.displayProfiles();
    }
}



