import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Основной класс программы
 */
public class Room {
    private int width, height;
    private Snake snake;
    private Mouse mouse;

    public static Room game;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Основной цикл программы
     */
    void run() {
        // Создаем объект "наблюдатель за клавиатурой" и стартуем его
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (snake.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                // если равно 'q' - выйти из игры
                if (event.getKeyChar() == 'q')
                    return;
                else if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }
            snake.move();
            print();
            sleep();
        }
        System.out.println("Game Over!");
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    void print() {
        int[][] matrix = new int[height][width];

        // Рисуем кусочки змеи
        ArrayList<SnakeSection> sections = new ArrayList<>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        // Рисуем голову змеи (4 - если змея мертвая)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        // Рисуем мышь
        matrix[mouse.getY()][mouse.getX()] = 3;

        // Выводим все на экран
        String[] symbols = {" ", "x", "X", "^", "*"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * Создаем новую мышь
     */
    void createMouse() {
        int x, y;
        x = (int) (Math.random() * width);
        y = (int) (Math.random() * height);
        mouse = new Mouse(x, y);
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
    }

    private int initialDelay = 520;
    private int delayStep = 20;
    /**
     * Программа делает паузу, длина которой зависит от длины змеи
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }
}
