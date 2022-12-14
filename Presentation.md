# Presentation

# Team VOID
 - Therese, Maren, Alastair

## Project Description

- Use the *[Laterna](https://github.com/mabe02/lanterna)* java library for GUI's in text-only environment.
- Create a simple single player game.
- Utilize project management/cooperation/coding skills learnt so far.
---
## lanternagame.Game Description

- A player uses the arrow keys to move around the board. 
- Avoid the zombies for as long as possible.
- A player loses a life if they are caught by a zombie.
- The game is over when all lives are lost.

## Structure
![](/Users/therese/Documents/AW Academy/Sprint1/module2-4/lanterna-intro/src/main/resources/Screen Shot 2022-11-10 at 15.40.24.png)

## Code
```java
  public static void main(String[] args)
          throws IOException, InterruptedException {

    Game game = new Game();
    game.playMusic();
    game.startScreen();

    boolean playAgain = true;
    while (playAgain) {
      game.setUpGame();
      game.startPlaying();
      game.finishGame();
      playAgain = game.playAgain();
      game.resetGame();
    }
    Platform.exit();
  }
```

---

## Project organisation

- Project planned and logic organised using pseudocode.
- Code maintained through Git/GitHub.
- GitHub project used as a kanban board to organise tasks.
- Having a highly organised Project Manager is gold!

---

## Lessons Learnt

- It is very hard to keep project members in  their 'own lane'.
- The better the encapsulation the easier it is to cooperate.
- Good planning is essential (and hard).
- Good Git skills and practices are really useful.
    (And don't forget to switch branches!)
- Start small with a Minimum Viable Product and build from there.
    It's really easy to get carried away.