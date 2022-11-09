
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

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

    public void setUpGame() {
        // TODO Set terminal size
        addZombie();
    }

    public void addZombie() {
        zombies.add(new Zombie(10, 10));
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