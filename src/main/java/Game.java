import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    Terminal terminal;
    Player player = new Player(2, 30, 10);
    ArrayList<Zombie> zombies = new ArrayList<>();

    public Game(Terminal terminal) throws IOException {
        this.terminal = terminal;
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
                    terminal.putCharacter(player.getMarker());
                }
                if (!player.isAlive()) {
                    //TODO:  do something
                }
            }
            counter++;
        }
    }
}

