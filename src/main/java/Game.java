
  import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
  Player player = new Player(2, 30, 10);
  ArrayList<Zombie> zombies = new ArrayList<>();

  DefaultTerminalFactory d = new DefaultTerminalFactory();
  Terminal t = d.createTerminal();
  TerminalPosition currentPosition;
  TerminalSize terminalSize = t.getTerminalSize();

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
        addZombie();
    }

    private ArrayList<SpawnFields> createSpawnFields() throws IOException {
        int xMax = t.getTerminalSize().getColumns();
        int yMax = t.getTerminalSize().getRows();

        SpawnFields topLeft = new SpawnFields(2, 12);
        SpawnFields topRight = new SpawnFields(xMax - 2, 12);
        SpawnFields bottomLeft = new SpawnFields(2, yMax - 2);
        SpawnFields bottomRight = new SpawnFields(xMax -2, yMax - 2);

        spawnFields.add(topLeft);
        spawnFields.add(topRight);
        spawnFields.add(bottomLeft);
        spawnFields.add(bottomRight);

        return spawnFields;
    }

    public void addZombie() {
        zombies.add(new Zombie(5, 5));
    }

    public void startPlaying() throws InterruptedException, IOException {
        while (player.isAlive()) {
            player.movePlayer(t);
          for (Zombie z : zombies) {
            t.setCursorPosition(z.getX(),z.getY());
            t.putCharacter(' ');
            z.moveZombie(player.getX(), player.getY());
            t.setCursorPosition(z.getX(),z.getY());
            t.putCharacter(z.getSymbol());
            if (z.hasCaughtPlayer(z, player.getX(), player.getY())) {
              player.loseLife();
              zombies.remove(z);
              if (zombies == null) addZombie();
              t.setCursorPosition(player.getX(), player.getY());
                    t.putCharacter(player.getMarker());
                }
                if (!player.isAlive()) {
                    break;
                }
            counter++;
          }
        }
    } // end startPlaying


  public void finishGame() throws IOException, InterruptedException {
    t.clearScreen();;
    t.setCursorPosition(t.getTerminalSize().getRows() / 2, t.getTerminalSize().getColumns() / 2);
    t.enableSGR(SGR.BLINK);
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    t.putString("GAME OVER!");
    t.flush();
    Thread.sleep(2000);
    t.clearScreen();
    t.disableSGR(SGR.BLINK);
    t.setCursorPosition(t.getTerminalSize().getRows() / 2, t.getTerminalSize().getColumns() / 2);
    int numMoves = 1;
    if (numMoves > 10){
      t.putString("WELL DONE, YOU MANAGED " + numMoves + " MOVES!");
    } else {
      t.putString("YOU'RE REALLY BAD AT THIS GAME!");
    }
  } // end finishGame

} // end class