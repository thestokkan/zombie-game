
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
    ArrayList<int[]> spawnFields = new ArrayList<>();
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
        /*spawnFields = createSpawnFields();*/

        t.setCursorVisible(false);
        addZombie();
    }

   /* private ArrayList<SpawnFields> createSpawnFields() throws IOException {
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
    }*/

    public void addZombie() {
        zombies.add(new Zombie(5, 5));
    }

    public void startPlaying() throws InterruptedException, IOException {
            int countTo50 =0;
        while (player.isAlive()) {
            //TODO hacky way to let player move twice
            player.movePlayer(t);
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
                //TODO stop program if window is closed...should probably not be here ->eventlistener?
                if (t == null) {
                    System.out.println("Exiting....");
                }
            }
            moves++;
            countTo50++;
            if (countTo50>=50){
                addZombie();
                countTo50 =10;
            }
        }
    } // end startPlaying


    public void finishGame() throws IOException, InterruptedException {
        t.clearScreen();
        t.setCursorPosition(t.getTerminalSize().getRows() / 2, t.getTerminalSize().getColumns() / 2);
        t.enableSGR(SGR.BLINK);
        t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        t.putString("GAME OVER!");
        t.flush();
        Thread.sleep(2000);
        t.clearScreen();
        t.disableSGR(SGR.BLINK);
        t.setCursorPosition(t.getTerminalSize().getRows() / 2, t.getTerminalSize().getColumns() / 2);
        if (moves > 20) {
            t.putString("WELL DONE, YOU MANAGED " + moves + " MOVES!");
        } else {
            t.putString("You only got " + moves + " steps...\nYOU'RE REALLY BAD AT THIS GAME!");
        }
    } // end finishGame

    public void showStats(int moves) throws IOException, InterruptedException {
        t.setCursorPosition(1,1);
        t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        t.putString("Lives: " + player.getLives());
        t.setCursorPosition(1,10);
        t.putString(("No. Of Moves: " + moves));
        t.flush();

    }
} // end class