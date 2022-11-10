package lanternagame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZombieTest {

    Zombie z = new Zombie(10,20);

    @Test
    void getX() {
        int result = z.getX();
        assertEquals(10, result);
    }

    @Test
    void getY() {
        int result = z.getY();
        assertEquals(20, result);
    }

    @Test
    void hasCaughtPlayer() {
        assertTrue(z.hasCaughtPlayer( z,10,20));
    }
    @Test
    void hasNotCaughtPlayer() {
        assertFalse(z.hasCaughtPlayer( z,9,20));
    }
}