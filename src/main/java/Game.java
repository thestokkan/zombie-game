import java.util.ArrayList;

public class Game {
  Player player = new Player(2,30,10);
  ArrayList<Zombie> zombies = new ArrayList<>();

  public void addZombie(){
    zombies.add(new Zombie(10,10));
  }
}