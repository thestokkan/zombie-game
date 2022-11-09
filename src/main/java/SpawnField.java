public class SpawnField {
    private boolean isActive;
    private final int x;
    private final int y;

    public SpawnField(int x, int y) {
        this.x = x;
        this.y = y;
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