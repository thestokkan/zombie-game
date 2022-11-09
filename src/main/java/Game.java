
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    Player player = new Player(3, 30, 10);
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
        game.startScreen();
        game.setUpGame();
        game.startPlaying();
        game.finishGame();

    }

    public void setUpGame() throws IOException, InterruptedException {
        // Create spawn fields
        // spawnFields = createSpawnFields();

        t.setCursorVisible(false);
        addZombie();
        showStats(moves);
    }


/*    private ArrayList<SpawnFields> createSpawnFields() throws IOException {

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
            t.disableSGR(SGR.BLINK);
            player.movePlayer(t);
            player.movePlayer(t);
            for (Zombie z : zombies) {
                t.setCursorPosition(z.getX(), z.getY());
                t.putCharacter(' ');
                z.moveZombie(player.getX(), player.getY());
                t.setCursorPosition(z.getX(), z.getY());
                t.putCharacter(z.getSymbol());
                if (z.hasCaughtPlayer(z, player.getX(), player.getY())) {
                    t.enableSGR(SGR.BLINK);
                    player.loseLife();
                    zombies.remove(z);
                    t.flush();
                    if (zombies.isEmpty()) addZombie();
                    t.setCursorPosition(player.getX(), player.getY());
                    t.putString(player.getMarker());
                    break;
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

            showStats(moves);
            if (moves % 50 == 0){
                addZombie();

            }
        }
            t.disableSGR(SGR.BLINK);
    } // end startPlaying

    public void startScreen() throws IOException, InterruptedException {
        t.setCursorPosition(35, 10);
        t.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        char[] teamNameString = new char[]{'V', ' ', 'O', ' ', 'I', ' ', 'D' };
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
        char[] gameOverArr = new char[]{'G', ' ', 'A', ' ', 'M', ' ', 'E', ' ', ' ', 'O', ' ', 'V', ' ', 'E', ' ', 'R', ' ', '!'};
        for (char c : gameOverArr) {
            t.putCharacter(c);
            Thread.sleep(200);
            t.flush();
        }
        t.enableSGR(SGR.BLINK);
        t.clearScreen();
        t.setCursorPosition(31,10);
        t.putString("G A M E  O V E R !");
        t.flush();
        Thread.sleep(3000);
        t.disableSGR(SGR.BLINK);
        t.setCursorPosition(29, 13);
        t.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        if (moves > 50){
            t.putString("WELL DONE, YOU MANAGED " + moves + " MOVES!");
        } else {
            t.putString("YOU ONLY MANAGED " + moves + " MOVES");
            t.setCursorPosition(25, 15);
            t.putString("YOU'RE REALLY BAD AT THIS GAME!");
        }
        t.flush();
    }

    public void showStats(int moves) throws IOException, InterruptedException {
        String hearts = player.getLives()+ "";

        t.setCursorPosition(2, 1);
        t.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        t.putString("Lives: ");
        t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        t.putString(hearts);
        t.setCursorPosition(20,1);
        t.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        t.putString("Moves: ");
        t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        t.putString(moves + "");
        t.setForegroundColor(TextColor.ANSI.WHITE);
    }
} // end class