import java.util.Random;


public class KotakMisi {
    private Ladder ladder;
    private Snake snake;

    private int position;
    private int questionNumber;
    final Random random = new Random();

    public KotakMisi(Ladder ladder, Snake snake, int position) {
        this.ladder = ladder;
        this.snake = snake;
        this.position = position;
        this.questionNumber = this.random.nextInt(10) + 1;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
