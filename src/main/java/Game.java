
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Game {
    Terminal terminal;
    Player player = new Player(2, 30, 10);
    ArrayList<Zombie> zombies = new ArrayList<>();

    public Game(Terminal terminal) throws IOException {
        this.terminal = terminal;
    }

    public void setUpGame() {
        // TODO Set terminal size
        addZombie();
    }

    public void addZombie() {
        zombies.add(new Zombie(10, 10));
    }

    public void startPlaying() throws InterruptedException, IOException {
        int counter = 0;
        addZombie();
        while (player.isAlive()) {
            player.movePlayer(terminal);
            for (Zombie z : zombies) {
                z.moveZombie(player.getX(), player.getY());
                terminal.setCursorPosition(z.getX(),z.getY());
                terminal.putCharacter(z.getSymbol());
                if (z.hasCaughtPlayer(z, player.getX(), player.getY())) {
                    player.loseLife();
                    zombies.remove(z);
                    terminal.setCursorPosition(player.getX(), player.getY());
                    terminal.putString(player.getMarker());
                }
                if (!player.isAlive()) {
                    terminal.close();
                }
            }
            counter++;
        }
    } // end startPlaying


  public void finishGame(Terminal t) throws IOException, InterruptedException {
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