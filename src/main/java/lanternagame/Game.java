package lanternagame;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
  Media openingMusic;
  MediaPlayer playOpeningMusic;
  Media ambianceMusic;
  MediaPlayer playBackgroundMusic;
  Media gameOverSound;
  MediaPlayer playGameOverSound;
  Media dyingSound;
  MediaPlayer playDyingSound;
  Media zombieSound;
  MediaPlayer playZombieSound;
  Media doorSound;
  MediaPlayer playDoorSound;

  Player player = new Player(2, 30, 10);
  ArrayList<Zombie> zombies = new ArrayList<>();
  ArrayList<int[]> fieldsList = new ArrayList<>();
  ArrayList<SpawnField> spawnFields = new ArrayList<>();
  int moves = 0;

  private DefaultTerminalFactory d = new DefaultTerminalFactory();
  private Terminal t = d.createTerminal();
  private TerminalPosition currentPosition;
  private int xMax = t.getTerminalSize().getColumns();
  private int yMax = t.getTerminalSize().getRows();

  public Game() throws IOException {
  }

  public static void main(String[] args)
          throws IOException, InterruptedException {

    Game game = new Game();
    game.playMusic();
//    game.startScreen();
    game.setUpGame();
    game.startPlaying();
    game.finishGame();
  }

  public void playMusic() {
    Platform.startup(() -> {});

    openingMusic = new Media(getClass().getResource(
            "/futuristic-heartbeat-60-bpm-7074.mp3").toExternalForm());
    playOpeningMusic = new MediaPlayer(openingMusic);
    playOpeningMusic.setCycleCount(2);

    ambianceMusic = new Media(getClass().getResource("/dead" +
                                                     "-walking-mp3-14594.mp3").toExternalForm());
    playBackgroundMusic = new MediaPlayer(ambianceMusic);
    playBackgroundMusic.setCycleCount(20);

    gameOverSound = new Media(getClass().getResource("/verloren-89595.mp3").toExternalForm());
    playGameOverSound = new MediaPlayer(gameOverSound);

    dyingSound =
            new Media(getClass().getResource("/man-dying-89565.mp3").toExternalForm());
    playDyingSound = new MediaPlayer(dyingSound);

    zombieSound =
            new Media(getClass().getResource("/Zombie-Biting-A1-www.fesliyanstudios.com.mp3").toExternalForm());
    playZombieSound = new MediaPlayer(zombieSound);

    doorSound =
            new Media(getClass().getResource("/door-2-6841.mp3").toExternalForm());
    playDoorSound = new MediaPlayer(doorSound);
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
    playBackgroundMusic.play();
    playDoorSound.play();

    while (player.isAlive()) {
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

        t.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        t.putString(z.getSymbol());
        t.setForegroundColor(TextColor.ANSI.WHITE);

        if (z.hasCaughtPlayer(player.getX(), player.getY())) {
          t.enableSGR(SGR.BOLD);
          t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
          t.setCursorPosition(player.getX(), player.getY());
          player.loseLife();
          playZombieSound.play();
          zombies.remove(z);
          if (player.isAlive()) {
            t.putString(player.getMarkerLostLife());
          } else {
            t.putString(player.getMarkerDead());
            playDyingSound.play();
          }
          t.flush();
          Thread.sleep(1500);
          if (zombies.isEmpty()) addZombie();
          break;
        }
      }
      if (moves % 30 == 0) addZombie();
      if (moves % 50 == 0) {
        newSpawnField();
        playDoorSound.stop();
        playDoorSound.play();
      }
    }
    Thread.sleep(500);

    playBackgroundMusic.stop();
  } // end startPlaying

  public void startScreen() throws IOException, InterruptedException {
    playOpeningMusic.play();

    t.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
    char[] teamNameString = new char[]{'V', ' ', 'O', ' ', 'I', ' ', 'D'};
    t.setCursorPosition((xMax/2) - (teamNameString.length/2), yMax/2);
    for (char c : teamNameString) {
      t.putCharacter(c);
      Thread.sleep(200);
      t.flush();
    }
    t.setCursorVisible(false);
    Thread.sleep(2000);
    t.setForegroundColor(TextColor.ANSI.WHITE);
    String presents = "presents";
    t.setCursorPosition((xMax/2) - (presents.length()/2), yMax/2);
    t.putString(presents);
    t.flush();
    Thread.sleep(2500);
    t.clearScreen();
    t.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
    String title = "\uD83E\uDDDF\u200D Z O M B I E  L A N D \uD83E\uDDDF\u200D";
    t.setCursorPosition((xMax/2) - (title.length()/2), yMax/2);
    t.putString(title);
    t.flush();
    Thread.sleep(3000);
    t.clearScreen();
  } // end startScreen

  public void finishGame() throws IOException, InterruptedException {
    playGameOverSound.play();
    t.clearScreen();
    t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    char[] gameOverArr =
            new char[]{'G', ' ', 'A', ' ', 'M', ' ', 'E', ' ', ' ', 'O', ' ',
                       'V', ' ', 'E', ' ', 'R', ' ', '!'};
    t.setCursorPosition((xMax/2) - (gameOverArr.length/2), yMax/2);
    for (char c : gameOverArr) {
      t.putCharacter(c);
      Thread.sleep(200);
      t.flush();
    }
    t.enableSGR(SGR.BLINK);
    t.clearScreen();
    t.setCursorPosition((xMax/2) - (gameOverArr.length/2), yMax/2);
    t.putString("G A M E  O V E R !");
    t.flush();
    Thread.sleep(3000);
    t.disableSGR(SGR.BLINK);
    t.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
    if (moves > 50) {
      String wellDone = "WELL DONE, YOU MANAGED " + moves + " MOVES!";
      t.setCursorPosition((xMax/2) - (wellDone.length()/2), (yMax/2) + 2);
      t.putString(wellDone);
    } else {
      String youOnly = "YOU ONLY MANAGED " + moves + " MOVES";
      String youBad = "THE ZOMBIES ARE STILL HUNGRY. HAVE ANOTHER GO";
      t.setCursorPosition((xMax/2) - (youOnly.length()/2), (yMax/2) + 2);
      t.putString(youOnly);
      t.setCursorPosition((xMax/2) - (youBad.length()/2), (yMax/2) + 3);
      t.putString(youBad);
    }
    t.flush();
    Platform.exit();
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