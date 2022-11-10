public class SpawnField {
    private boolean isActive;
    private final int x;
    private final int y;

    public SpawnField(int[] field) {
        this.x = field[0];
        this.y = field[1];
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        isActive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}