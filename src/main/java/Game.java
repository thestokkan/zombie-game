import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
  Player player = new Player(2, 30, 10);
  ArrayList<Zombie> zombies = new ArrayList<>();
  ArrayList<int[]> fieldsList = new ArrayList<>();
  ArrayList<SpawnField> spawnFields = new ArrayList<>();
  int moves = 0;

  DefaultTerminalFactory d = new DefaultTerminalFactory();
  Terminal t = d.createTerminal();
  TerminalPosition currentPosition;

  public Game() throws IOException {
  }

  public static void main(String[] args)
          throws IOException, InterruptedException {
    Game game = new Game();
//    game.startScreen();
    game.setUpGame();
    game.startPlaying();
    game.finishGame();
  }

  public void setUpGame() throws IOException, InterruptedException {
    t.setCursorVisible(false);

    generateFields();

    newSpawnField();
    addZombie();
    showStats(moves);
  }

  private void newSpawnField() throws IOException {
    if (!fieldsList.isEmpty()) {
      spawnFields.add(new SpawnField(fieldsList.remove(fieldsList.size() - 1)));
    }
  }

  public void showSpawnField() throws IOException {
    for (SpawnField field : spawnFields) {
      t.setCursorPosition(field.getX(), field.getY());
      t.putString(field.getMarker());
    }
  }

  private void generateFields() throws IOException {
    int xMax = t.getTerminalSize().getColumns();
    int yMax = t.getTerminalSize().getRows();

    fieldsList.addAll(Arrays.asList(new int[][]{
            {2, 2},
            {xMax - 2, 2},
            {2, yMax - 2},
            {xMax - 2, yMax - 2},
            {xMax / 2, yMax - 2},
            {xMax / 2, 2},
            {xMax - 2, yMax / 2},
            {2, yMax / 2}
    }));
  }

  public void addZombie() {
    Collections.shuffle(spawnFields);
    zombies.add(new Zombie(spawnFields.get(0)));
  }

  public void startPlaying() throws InterruptedException, IOException {
    int countTo50 = 0;
    while (player.isAlive()) {
      //TODO hacky way to let player move twice
      player.movePlayer(t);
      moves++;
      showStats(moves);
      player.movePlayer(t);
      moves++;
      showStats(moves);

      showSpawnField();

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
          if (player.isAlive()) {
            t.putString(player.getMarkerLostLife());
          } else {
            t.putString(player.getMarkerDead());
          }
          t.flush();
          Thread.sleep(2000);
          break;
        }

        if (!player.isAlive()) {
          break;
        }

        //TODO stop program if window is closed...should probably not be here
        // ->eventlistener?
        if (t == null) {
          System.out.println("Exiting....");
        }
      }
      if (moves % 30 == 0) addZombie();
      if (moves % 50 == 0) newSpawnField();
    }
  } // end startPlaying

  public void startScreen() throws IOException, InterruptedException {
    t.setCursorPosition(35, 10);
    t.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
    char[] teamNameString = new char[]{'V', ' ', 'O', ' ', 'I', ' ', 'D'};
    for (char c : teamNameString) {
      t.putCharacter(c);
      Thread.sleep(200);
      t.flush();
    }
    Thread.sleep(2000);
    t.setCursorPosition(34, 12);
    t.setForegroundColor(TextColor.ANSI.WHITE);
    t.putString("presents");
    t.flush();
    Thread.sleep(2500);
    t.clearScreen();
    t.setCursorPosition(31, 10);
    t.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
    t.putString("Z O M B I E   L A N D ");
    t.flush();
    Thread.sleep(3000);
    t.clearScreen();
  }

  public void finishGame() throws IOException, InterruptedException {
    t.clearScreen();
    t.setCursorPosition(30, 10);
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    char[] gameOverArr =
            new char[]{'G', ' ', 'A', ' ', 'M', ' ', 'E', ' ', ' ', 'O', ' ',
                       'V', ' ', 'E', ' ', 'R', ' ', '!'};
    for (char c : gameOverArr) {
      t.putCharacter(c);
      Thread.sleep(200);
      t.flush();
    }
    t.enableSGR(SGR.BLINK);
    t.clearScreen();
    t.setCursorPosition(31, 10);
    t.putString("G A M E  O V E R !");
    t.flush();
    Thread.sleep(3000);
    t.disableSGR(SGR.BLINK);
    t.setCursorPosition(29, 13);
    t.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
    if (moves > 50) {
      t.putString("WELL DONE, YOU MANAGED " + moves + " MOVES!");
    } else {
      t.putString("YOU ONLY MANAGED " + moves + " MOVES");
      t.setCursorPosition(25, 15);
      t.putString("YOU'RE REALLY BAD AT THIS GAME!");
    }
    t.flush();
  }

  public void showStats(int moves) throws IOException, InterruptedException {
    String hearts = player.getLives() + "";

    t.setCursorPosition(2, 1);
    t.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
    t.putString("Lives: ");
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.putString(hearts);
    t.setCursorPosition(20, 1);
    t.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
    t.putString("Moves: ");
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.putString(moves + "");
    t.setForegroundColor(TextColor.ANSI.WHITE);
  }

} // end class