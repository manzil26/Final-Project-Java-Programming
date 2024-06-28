import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;

public class SnL {
    private final JOptionPane frame = new JOptionPane();
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<KotakMisi> kotakMisis;
    private int boardSize;
    private int gameStatus;
    private int nowPlaying;
    private int numDice;

    private final String ladderSoundFile = "src/ladder.wav";
    private final String snakeSoundFile = "src/snake.wav";
    private final String winSoundFile = "src/win.wav";
    private final String rollDiceSoundFile = "src/roll_dice.wav";

    public SnL(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.kotakMisis = new ArrayList<>();
        this.gameStatus = 0;
        this.numDice = 1; // Default number of dice
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public int getNumDice() {
        return numDice;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void play() {
        Player playerInTurn;
        String numPlayersStr = frame.showInputDialog(null, "Enter the number of players:");
        int numPlayers = Integer.parseInt(numPlayersStr);

        for (int i = 1; i <= numPlayers; i++) {
            String playerName = frame.showInputDialog(null, "Please enter Player " + i + ":");
            Player p = new Player(playerName);
            p.setScore(getScoreByName(p.getName()));
            addPlayer(p);
        }

      // Customize number of dice based on board size
       if (boardSize >= 100 && boardSize <= 200) {
        numDice = 4; // Maximum 4 dice rolls
       }

        initiateGame();

        do {
            playerInTurn = getWhoseTurn();
            frame.showMessageDialog(null, "Now Playing: " + playerInTurn.getName());
            frame.showMessageDialog(null, playerInTurn.getName() + ", please press OK to roll the dice");

            Sound rollDiceSound = new Sound(rollDiceSoundFile);
            rollDiceSound.play();

            int x = playerInTurn.rollDice(getNumDice());
            frame.showMessageDialog(null, "Dice Number: " + x);

            movePlayerAround(playerInTurn, x);
            frame.showMessageDialog(null, "New Position: " + playerInTurn.getPosition());

            if (x % 6 == 0) {
                frame.showMessageDialog(null, playerInTurn.getName() + " rolled a 6 and gets another turn!");
            } else {
                nowPlaying = (nowPlaying + 1) % players.size();
            }

        } while (getGameStatus() != 2);

        Sound winSound = new Sound(winSoundFile);
        winSound.play();

        frame.showMessageDialog(null, "The winner is: " + playerInTurn.getName());
        playerInTurn.setScore(playerInTurn.getScore() + 1);

        // Save scores to file
        for (Player pl : this.players) {
            updateScoreInFile(pl.getName(), pl.getScore());
        }
    }

    // Update player score in a text file
    public static void updateScoreInFile(String targetName, int newScore) {
        List<String> lines = new ArrayList<>();
        boolean nameFound = false;
        String filename = "gameSave.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String namePart = parts[0];
                    String scorePart = parts[1];

                    String[] nameParts = namePart.split(": ");
                    String[] scoreParts = scorePart.split(": ");

                    if (nameParts.length == 2 && scoreParts.length == 2) {
                        String name = nameParts[1];
                        if (name.equals(targetName)) {
                            line = "name: " + name + ", score: " + newScore;
                            nameFound = true;
                        }
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            if (!nameFound) {
                bufferedWriter.write("name: " + targetName + ", score: " + newScore);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Score updated successfully.");
    }

    // Get player score by name from a text file
    public int getScoreByName(String targetName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("gameSave.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String namePart = parts[0];
                    String scorePart = parts[1];

                    String[] nameParts = namePart.split(": ");
                    String[] scoreParts = scorePart.split(": ");

                    if (nameParts.length == 2 && scoreParts.length == 2) {
                        String name = nameParts[1];
                        int score = Integer.parseInt(scoreParts[1]);

                        if (name.equals(targetName)) {
                            return score;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 0;
    }

    public void addPlayer(Player s) {
        this.players.add(s);
    }

    public ArrayList<Player> getPlayers(Player s) {
        return this.players;
    }

    public void addSnake(Snake s) {
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s) {
        for (int r = 0; r < s.length; r++) {
            Snake snake = new Snake(s[r][0], s[r][1]);
            this.snakes.add(snake);
        }
    }

    public void addLadder(Ladder l) {
        this.ladders.add(l);
    }

    public void addLadders(int[][] l) {
        for (int r = 0; r < l.length; r++) {
            Ladder ladder = new Ladder(l[r][1], l[r][0]);
            this.ladders.add(ladder);
        }
    }

    // Add mission box function
    public void addKotakMisi(int position, int top, int button){
        Snake snake = new Snake(button, position);
        Ladder ladder = new Ladder(position, top);
        KotakMisi kotakMisi = new KotakMisi(ladder, snake, position);
        this.kotakMisis.add(kotakMisi);
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public ArrayList<Snake> getSnakes() {
        return this.snakes;
    }

    public ArrayList<Ladder> getLadders() {
        return this.ladders;
    }

    public void initiateGame() {
        // Ensure min is at least 3 (or any minimum value you deem appropriate)
        int min = 3;
        int max = boardSize -3;

        int variable1;
        int variable2;

        // Set ladders
        int[][] l = new int[8][2];

        Random random = new Random();
        for (int baris = 0; baris < 8; baris++) {
            variable1 = random.nextInt(max - min) + min;
            variable2 = random.nextInt(max - min) + min;

            if (variable1 > variable2) {
                l[baris][0] = variable1;
                l[baris][1] = variable2;
            } else if (variable1 < variable2) {
                l[baris][0] = variable2;
                l[baris][1] = variable1;
            } else {
                l[baris][0] = variable1;
                l[baris][1] = variable1 + 3;
            }
        }

        addLadders(l);

        int[][] s = new int[8][2];

      //  Random random = new Random();
        for (int baris = 0; baris < 8; baris++) {
            variable1 = random.nextInt(max - min) + min;
            variable2 = random.nextInt(max - min) + min;

            if (variable1 < variable2) {
                s[baris][0] = variable1;
                s[baris][1] = variable2;
            } else if (variable1 > variable2) {
                s[baris][0] = variable2;
                s[baris][1] = variable1;
            } else {
                s[baris][0] = variable1;
                s[baris][1] = variable1 + 3;
            }
        }
//        System.out.println(Arrays.deepToString(s));

        addSnakes(s);


        // Add mission box by hardcode
        addKotakMisi(6, 17, 3);
        addKotakMisi(8, 21, 4);
        addKotakMisi(10, 26, 7);
        addKotakMisi(26, 39, 19);
        addKotakMisi(33, 40, 21);
        addKotakMisi(42, 52, 32);
        addKotakMisi(56, 67, 43);
        addKotakMisi(63, 94, 43);
        addKotakMisi(78, 89, 67);
        addKotakMisi(96, 99, 73);
    }

    // Read question or answer list txt files
    public String readKotakMisiFile(String filePath, int targetLine){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 1;

            while (currentLine < targetLine && (line = reader.readLine()) != null) {
                currentLine++;
            }

            while ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void movePlayerAround(Player p, int x) {
        p.moveAround(x, this.boardSize);
        Sound ladderSound = new Sound(ladderSoundFile);
        Sound snakeSound = new Sound(snakeSoundFile);

        for (Ladder l : this.ladders) {
            if (p.getPosition() == l.getBottomPosition()) {
                ladderSound.play();
                JOptionPane.showMessageDialog(null, p.getName() + " got a ladder from: " + l.getBottomPosition() + " to: " + l.getTopPosition());
                p.setPosition(l.getTopPosition());
            }
        }

        for (Snake s : this.snakes) {
            if (p.getPosition() == s.getHeadPosition()) {
                snakeSound.play();
                p.setPosition(s.getTailPosition());
                JOptionPane.showMessageDialog(null, p.getName() + " got snake head from " + s.getHeadPosition() + " slide down to " + s.getTailPosition());
            }
        }

        // Mission box logic
        for(KotakMisi km : this.kotakMisis){
            if(p.getPosition() == km.getPosition()){
                String question = this.readKotakMisiFile("Question.txt", km.getQuestionNumber());
                String answer = this.readKotakMisiFile("Answer.txt", km.getQuestionNumber());

                String userAnswer = frame.showInputDialog(null, question);

                if (userAnswer.equals(answer)){ // Correct answer
                    p.setPosition(km.getLadder().getTopPosition());
                    ladderSound.play();
                    JOptionPane.showMessageDialog(null, p.getName()+ " Correct answer climbing to + "+p.getPosition());
                }else{ // Wrong answer
                    p.setPosition(km.getSnake().getTailPosition());
                    snakeSound.play();
                    JOptionPane.showMessageDialog(null, p.getName()+ " Wrong answer falling to + "+p.getPosition());
                }
            }
        }

        if (p.getPosition() == this.boardSize) {
            this.gameStatus = 2;
        }
    }

    public Player getWhoseTurn() {
        if (this.gameStatus == 0) {
            this.gameStatus = 1;
            this.nowPlaying = (Math.random() <= 0.5) ? 0 : 1;
            return this.players.get(this.nowPlaying);
        } else {
            return this.players.get(this.nowPlaying);
        }
    }
}
