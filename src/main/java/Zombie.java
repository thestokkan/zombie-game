public class Zombie {
  private int speed;
  private int x;
  private int y;


  public Zombie(int speed, int x, int y) {
    this.x = x;
    this.y = y;
    this.speed = speed;
  }
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

}