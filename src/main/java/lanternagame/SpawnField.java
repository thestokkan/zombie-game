package lanternagame;

public class SpawnField {
    private final String marker = "🚪";
    private final int x;
    private final int y;

    public SpawnField(int[] field) {
        this.x = field[0];
        this.y = field[1];
    }

    public String getMarker() {
        return marker;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}