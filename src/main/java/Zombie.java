import java.util.ArrayList;
import com.googlecode.lanterna.Symbols;

public class Zombie {
  private final char symbol = 'Z';
  private int x;
  private int y;

  public Zombie( int x, int y) {
    this.x = x;
    this.y = y;

  }


  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

//calculates where to move zombie
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
  public boolean hasCaughtPlayer(Zombie zombie, int playerX, int playerY){
    if (x ==playerX && y == playerY){
      return true;
    }
    return false;
  }

  //TODO move to main

  /*ArrayList<Zombie> zombieArray = new ArrayList<>();
  public static void addZombie(){
    zombieArray.add(new Zombie(10,10));
  }*/
}