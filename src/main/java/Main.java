import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main {
  public static void main(String[] args)
          throws IOException, InterruptedException {
    DefaultTerminalFactory d = new DefaultTerminalFactory();
    Terminal t = d.createTerminal();
    TerminalPosition currentPosition;

    TerminalSize terminalSize = t.getTerminalSize();
    int terminalRows = terminalSize.getRows();
    int terminalCols = terminalSize.getColumns();

    Player player = new Player(1, 10, 10);

    // Reading input
    t.clearScreen();

    boolean continueReadingInput = true;

    while (continueReadingInput) {
      KeyStroke keyStroke = null;

      //TODO move to player class

      t.putCharacter(' ');
      t.setCursorPosition(player.getX(), player.getY());
      t.putCharacter(player.marker);
      t.flush();

      do {
        Thread.sleep(5); // might throw InterruptedException
        keyStroke = t.pollInput();
      } while (keyStroke == null);

      KeyType type = keyStroke.getKeyType();
      Character c =
              keyStroke.getCharacter(); // use Character, not char because it might be null

      switch (type) {
        case ArrowUp -> y--;
        case ArrowDown -> y++;
        case ArrowLeft -> x--;
        case ArrowRight -> x++;
      }

      if (c == Character.valueOf('q')) {
        continueReadingInput = false;
        t.clearScreen();
        String farewell = "Thanks for playing!";
        x = (terminalCols / 2) - (farewell.length() / 2);
        y = terminalRows / 2;

        t.setCursorPosition(x, y);
        t.putString(farewell);
        t.flush();
        Thread.sleep(2000);
      }
    }
    t.close();

  }
}