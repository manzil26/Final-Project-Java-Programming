import java.util.HashMap;

public class Player {
    private String name;
    private int position;
    private int score;
    private static HashMap<String, Integer> profiles = new HashMap<>();

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.score = 0;
        if (!profiles.containsKey(name)) {
            profiles.put(name, 0);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setScore(int score) {
        this.score = score;
        profiles.put(this.name, score);
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public int getScore() {
        return this.score;
    }

    public int rollDice(int numDice) {
        int total = 0;
        for (int i = 0; i < numDice; i++) {
            total += (int) (Math.random() * 6 + 1);
        }
        return total;
    }

    public void moveAround(int y, int boardSize) {
        if (this.position + y > boardSize) {
            this.position = boardSize - (this.position + y) % boardSize;
        } else {
            this.position = this.position + y;
        }
    }

    public static void displayProfiles() {

        // notifikasi permainan selesai

        System.out.println("User Profiles:");
        for (String name : profiles.keySet()) {
            System.out.println("Name: " + name + ", Score: " + profiles.get(name));
        }
    }
}
