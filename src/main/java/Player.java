public class Player {
  final char marker = 'X';
  private int lives = 1;
  private int x;
  private int y;

  public Player(int lives, int x, int y) {
    this.lives = lives;
    this.x = x;
    this.y = y;
  }



  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
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