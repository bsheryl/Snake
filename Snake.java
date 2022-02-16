import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    /**
     * Метод перемещает змею на один ход
     */
    public void move() {
        if (!isAlive)
            return;
        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * Метод перемещает змею в соседнюю клетку
     */
    public void move(int dx, int dy) {
        // Создаем новую голову
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);
        // Проверяем, на вышла ли голова за пределы поля
        checkBorders(head);
        if (!isAlive)
            return;
        //Проверяем, не съела ли змею саму себя
        checkBody(head);
        if (!isAlive)
            return;
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY())
        {
            sections.add(0, head);
            Room.game.eatMouse();
        } else {
            sections.add(0, head);
            sections.remove(sections.size() - 1);
        }
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    /**
     * Метод проверяет, находится ли змею в пределах поля
     */
    void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth())
            || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет, не совпала ли голова с участком тела змеи
     */
    void checkBody(SnakeSection head) {
        if (sections.contains(head))
            isAlive = false;
    }
}
