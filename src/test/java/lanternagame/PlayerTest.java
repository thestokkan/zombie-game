package lanternagame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p = new Player(2, 10,23);


    @Test
    void getLivesTest() {
        int result = p.getLives();
        assertEquals(2, result);
    }

    @Test
    void getX() {
        int result = p.getX();
        assertEquals(10, result);
    }

    @Test
    void getY() {
        int result = p.getY();
        assertEquals(23, result);
    }

    @Test
    void isAlive() {
        Player p = new Player(2, 10,23);
        boolean result = p.isAlive();
        assertEquals(true, result);
    }
    @Test
    void isNotAlive() {
        Player p = new Player(0, 10,23);
        boolean result = p.isAlive();
        assertEquals(false, result);
    }
}