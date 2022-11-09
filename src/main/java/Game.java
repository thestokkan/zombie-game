import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
  Player player = new Player(2, 30, 10);
  ArrayList<Zombie> zombies = new ArrayList<>();
  ArrayList<SpawnFields> spawnFields = new ArrayList<>();
  int moves = 0;

  DefaultTerminalFactory d = new DefaultTerminalFactory();
  Terminal t = d.createTerminal();
  TerminalPosition currentPosition;

  public Game() throws IOException {

  }

  public static void main(String[] args)
          throws IOException, InterruptedException {
    Game game = new Game();
    game.setUpGame();
    game.startPlaying();
    game.finishGame();
  }

  public void setUpGame() throws IOException {
    // Create spawn fields
    spawnFields = createSpawnFields();

    t.setCursorVisible(false);

    // Activate first spawn field
    SpawnFields startingField = spawnFields.get(0);
    startingField.setActive();

    addZombie();
  }

  private void newSpawnField() throws IOException {
    boolean activateNewField = false;
    while (!activateNewField) {
      int randIndex = (int) (Math.random() * 3);
      if (!spawnFields.get(randIndex).isActive()) {
        spawnFields.get(randIndex).setActive();
        colorSpawnField(randIndex);
        activateNewField = true;
      }
    }
  }

  public void colorSpawnField(int index) throws IOException {
    SpawnFields field = spawnFields.get(index);
    t.setCursorPosition(field.getX(), field.getY());
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.flush();
  }

  private ArrayList<SpawnFields> createSpawnFields() throws IOException {
    int xMax = t.getTerminalSize().getColumns();
    int yMax = t.getTerminalSize().getRows();

    SpawnFields topLeft = new SpawnFields(2, 2);
    SpawnFields topRight = new SpawnFields(xMax - 2, 2);
    SpawnFields bottomLeft = new SpawnFields(2, yMax - 2);
    SpawnFields bottomRight = new SpawnFields(xMax - 2, yMax - 2);
    SpawnFields bottomMiddle = new SpawnFields(xMax / 2, yMax - 2);
    SpawnFields topMiddle = new SpawnFields(xMax / 2, 2);
    SpawnFields rightMiddle = new SpawnFields(xMax - 2, yMax / 2);
    SpawnFields leftMiddle = new SpawnFields( 2, yMax / 2);

    spawnFields.add(topLeft);
    spawnFields.add(topRight);
    spawnFields.add(bottomLeft);
    spawnFields.add(bottomRight);
    spawnFields.add(bottomMiddle);
    spawnFields.add(topMiddle);
    spawnFields.add(rightMiddle);
    spawnFields.add(leftMiddle);

    return spawnFields;
  }

  public void addZombie() {
    // Get random active spawn field
    SpawnFields field = null;
    while (field == null) {
      int random = (int) (Math.random() * 3);
      if (spawnFields.get(random).isActive()) {
        field = spawnFields.get(random);
      }
    }
    zombies.add(new Zombie(field));
  }

  public void startPlaying() throws InterruptedException, IOException {
    while (player.isAlive()) {
      player.movePlayer(t);
      for (Zombie z : zombies) {
        t.setCursorPosition(z.getX(), z.getY());
        t.putCharacter(' ');
        z.moveZombie(player.getX(), player.getY());
        t.setCursorPosition(z.getX(), z.getY());
        t.putCharacter(z.getSymbol());
        if (z.hasCaughtPlayer(z, player.getX(), player.getY())) {
          player.loseLife();
          zombies.remove(z);
          if (zombies.isEmpty()) addZombie();
          t.setCursorPosition(player.getX(), player.getY());
          t.putCharacter(player.getMarker());
        }
        if (!player.isAlive()) {
          break;
        }
        //TODO stop program if window is closed...should probably not be here
        // ->eventlistener?
                /*if (t == null) {
                    System.out.println("Exiting....");
                    break;
                }*/
      }
      moves++;
      // Activate another spawn field
      if (moves % 10 == 0) {
        newSpawnField();
      }

      if (moves % 10 == 0) {
        addZombie();
//        moves -= moves;
      }

    }
  } // end startPlaying


  public void finishGame() throws IOException, InterruptedException {
    t.clearScreen();
    t.setCursorPosition(t.getTerminalSize().getRows() / 2,
                        t.getTerminalSize().getColumns() / 2);
    t.enableSGR(SGR.BLINK);
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.putString("GAME OVER!");
    t.flush();
    Thread.sleep(2000);
    t.clearScreen();
    t.disableSGR(SGR.BLINK);
    t.setCursorPosition(t.getTerminalSize().getRows() / 2,
                        t.getTerminalSize().getColumns() / 2);
    if (moves > 20) {
      t.putString("WELL DONE, YOU MANAGED " + moves + " MOVES!");
    } else {
      t.putString("You only got " + moves +
                  " steps...\nYOU'RE REALLY BAD AT THIS GAME!");
    }
  } // end finishGame

  public void showStats(int moves) throws IOException, InterruptedException {
    t.setCursorPosition(1, 1);
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.putString("Lives: " + player.getLives());
    t.setCursorPosition(1, 10);
    t.putString(("No. Of Moves: " + moves));
    t.flush();

  }
} // end class