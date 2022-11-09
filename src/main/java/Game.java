import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Game {
  Player player = new Player(2,30,10);
  ArrayList<Zombie> zombies = new ArrayList<>();

  public void addZombie(){
    zombies.add(new Zombie(10,10));
  }

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
    if (numMoves > 10){
      t.putString("WELL DONE, YOU MANAGED " + numMoves + " MOVES!");
    } else {
      t.putString("YOU'RE REALLY BAD AT THIS GAME!");
    }


  }
}