public class Player {
  final char player = 'X';
  private int lives = 1;
  private int x;
  private int y;

  public Player(int lives, int x, int y) {
    this.lives = lives;
    this.x = x;
    this.y = y;
  }



  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }


}