import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
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





  }
}