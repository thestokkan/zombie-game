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



  // - Get player position -> send player position through moveZombie method
  // - Calculate where(in what direction) to move
  // - Move Zombie

  public void moveZombie(int playerX, int playerY) {
    int distanceX = x - playerX;
    int distanceY = y - playerY;
    //check distance regardless of position
    if ( Math.abs(distanceX) > Math.abs(distanceY)) {
      //check is x/y is pos or neg and move accordingly
      x = distanceX>0 ? x- 1 : x + 1;
    } else{
      y = distanceY>0?y-1:y+1;
    }
  }



}