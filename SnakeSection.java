public class SnakeSection {
    private int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SnakeSection that = (SnakeSection) obj;
        if (x != that.x)
            return false;
        return y == that.y;
    }

    public SnakeSection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
